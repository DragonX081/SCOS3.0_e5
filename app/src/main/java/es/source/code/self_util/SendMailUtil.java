package es.source.code.self_util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import es.source.code.model.MailInfo;

public class SendMailUtil {
    final static String receive = "zgx081@qq.com";
    final static String send = "zgx081@qq.com";
    final static String pwd = "uwymaktbscbmbjbg";
    final static String subject ="test";
    final static String message = "a test Mail from android";
    final static String host = "smtp.qq.com";
    final static String port = "587";
    //final static int popPort = 995;
    public static void send(final Handler handler) {
        final MailInfo mailInfo = createMail();
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
                if(handler!=null) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.setData(new Bundle());
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    private static MailInfo createMail(){
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(host);
        mailInfo.setMailServerPort(port);
        mailInfo.setValidate(true);
        mailInfo.setUserName(send); // 你的邮箱地址
        mailInfo.setPassword(pwd);// 您的邮箱密码
        mailInfo.setFromAddress(send); // 发送的邮箱
        mailInfo.setToAddress(receive); // 发到哪个邮件去
        mailInfo.setSubject(subject); // 邮件主题
        mailInfo.setContent(message); // 邮件文本
        return mailInfo;
    }

}
