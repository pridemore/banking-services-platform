package com.example.transactionprocessing.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Override
    public String send(EmailNotification emailNotification) {

        log.info("### Sending Email.............");

        try{
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(emailNotification.getSender());
            mailMessage.setTo(emailNotification.getTo());
            mailMessage.setText(emailNotification.getBody());
            mailMessage.setSubject(emailNotification.getSubject());
            javaMailSender.send(mailMessage);
            log.info("### Message Send Successfully...........");
            return "Message Send Successfully";

        }catch (Exception ex){
            log.info("### Error occured while sending email...........");
            log.info(ex.getMessage());
            return "Message Send Failed!!";
        }

    }
}
