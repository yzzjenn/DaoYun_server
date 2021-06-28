package com.yzz.utils;

import cn.hutool.core.util.RandomUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@RestController
public class SmsUtil {
      private final static int appid = 1400510715;
    private final static String appkey = "e76da3eaf764c8c9b00fa71cd960eef8";
//    @PostMapping("/test")
//    @ApiOperation("发送手机验证码")
             public  String sendMessage( @RequestParam String phoneNumber) {
                     String status = "";
        int templateId=930343 ;
        String smsSign="奶威奶本联盟";
        String code=RandomUtil.randomNumbers(6);
        System.out.println(code);
                     String[] params = {code,"3"};
//
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
                     return status;  // 此处的status只有发送成功是"OK"
                 }
}
