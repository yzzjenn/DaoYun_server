package com.yzz.security.controller;

import cn.hutool.core.util.IdUtil;
import com.yzz.annotation.AnonymousAccess;
import com.yzz.config.RsaProperties;
import com.yzz.security.config.SecurityProperties;
import com.yzz.security.dao.AuthUserDto;
import com.yzz.security.dao.JwtUserDto;
import com.yzz.security.security.TokenProvider;
import com.yzz.security.service.OnlineUserService;
import com.wf.captcha.ArithmeticCaptcha;
import com.yzz.system.dao.UserDto;
import com.yzz.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.yzz.util.RedisUtils;
import com.yzz.util.RsaUtils;
import com.yzz.util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "系统授权接口")
public class AuthorizationController {
    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${single.login}")
    private Boolean singleLogin;
    @Value("${rsa.private_key}")
    private String privateKey;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation("手机号登录")
    @AnonymousAccess
    @PostMapping(value = "/loginByPhone")
    public  ResponseEntity<Object> loginbyphone(@RequestBody String phoneNumber,HttpServletRequest request){
        UserDto authUser=userService.findByPhoneOrEmail(phoneNumber);

        //这里的authentication是待认证，其principal为账号，credentials为密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), "123456");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        onlineUserService.save(jwtUserDto, token, request);

        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        return ResponseEntity.ok(authInfo);
    }


    @ApiOperation("密码登录")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        System.out.println(password);
        //这里的authentication是待认证，其principal为账号，credentials为密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};

        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    @AnonymousAccess
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
//        JwtUserDto jwtUserDto = (JwtUserDto)userDetailsService.loadUserByUsername(SecurityUtils.getCurrentUsername());
//        return ResponseEntity.ok(jwtUserDto);
    }



    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
