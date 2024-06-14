package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.model.MailEntity;

public interface IMailSendService {
    void send(MailEntity entity);
}
