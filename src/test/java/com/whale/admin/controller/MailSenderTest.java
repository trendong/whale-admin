//package com.whale.admin.controller;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class MailSenderTest {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Test
//    public void sendSimpleMail() throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("1561826175@qq.com");
//        message.setTo("chenchen@jingdata.com");
//        message.setSubject("xxx：xxx");
//        message.setText("xxx");
//        mailSender.send(message);
//    }
//
//}
