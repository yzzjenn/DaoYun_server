package com.yzz.security.service;

import com.yzz.exception.BadRequestException;
import com.yzz.exception.EntityNotFoundException;
import com.yzz.security.dao.JwtUserDto;
import com.yzz.system.dao.UserDto;
import com.yzz.system.service.DataService;
import com.yzz.system.service.RoleService;
import com.yzz.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final RoleService roleService;
    private final DataService dataService;

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        UserDto user;
        try {
            user = userService.findByName(username);
        } catch (EntityNotFoundException e) {
            // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
            throw new UsernameNotFoundException("", e);
        }
        if (user == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return new JwtUserDto(
                    user,
                    dataService.getDeptIds(user),
                    roleService.mapToGrantedAuthorities(user)
            );
        }
    }
}
