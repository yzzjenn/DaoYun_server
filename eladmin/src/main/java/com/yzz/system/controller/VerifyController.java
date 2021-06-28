package com.yzz.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.yzz.annotation.AnonymousAccess;
import com.yzz.consts.CodeBiEnum;
import com.yzz.consts.CodeEnum;
import com.yzz.system.pojo.vo.EmailVo;
import com.yzz.system.service.EmailService;
import com.yzz.system.service.VerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
@Api(tags = "验证码管理")
public class VerifyController {
    private final static int appid = 1400510715;
    private final static String appkey = "e76da3eaf764c8c9b00fa71cd960eef8";
    private final VerifyService verificationCodeService;
    private final EmailService emailService;

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    @AnonymousAccess
    public ResponseEntity<Object> resetEmail(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey());
        //emailService.send(emailVo, emailService.find());
        emailService.send(emailVo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    @AnonymousAccess
    public ResponseEntity<Object> resetPass(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_PWD_CODE.getKey());
        emailService.send(emailVo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public ResponseEntity<Object> validated(@RequestParam String email, @RequestParam String code, @RequestParam Integer codeBi) {
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            case ONE:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            case TWO:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/phoneCode")
    @ApiOperation("发送手机验证码")
    @AnonymousAccess
    public  String phoneCode( @RequestParam String phoneNumber) {
        String status = "";
        int templateId=930343 ;
        String smsSign="奶威奶本联盟";
        String code= RandomUtil.randomNumbers(6);
        System.out.println(code);
        String[] params = {code,"3"};
        //7.发送短信对象
        SmsSingleSender ssender = new SmsSingleSender(appid,appkey);
        //地区，电话，模板ID，验证码，签名
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");
            status=result.errMsg;
            log.info("sms send status,template id [{}],phone is [{}],status is [{}] ",templateId,phoneNumber,status);
        } catch (Exception e){
            log.info("sms send status,template id [{}],phone is [{}],status is [{}] ",templateId,phoneNumber,status,e);
        }
//        return new ResponseEntity<>(HttpStatus.OK);  // 此处的status只有发送成功是"OK"
        return  code;
    }
}
