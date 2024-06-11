package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.model.MailEntity;
import org.springframework.stereotype.Component;

public interface IMailSendService {
    void send(MailEntity entity);
}
