package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.model.MailEntity;
import by.it_academy.jd2.user_service.service.api.IMailSendService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSendServiceImpl implements IMailSendService {

    private final JavaMailSender javaMailSender;

    public MailSendServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(MailEntity entity) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(entity.getFrom());
        message.setTo(entity.getTo());
        message.setSubject(entity.getTopic());
        message.setText(entity.getText());

        javaMailSender.send(message);
    }
}
