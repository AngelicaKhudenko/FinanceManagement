package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.core.dto.MailDTO;
import by.it_academy.jd2.user_service.core.enums.EMailStatus;
import by.it_academy.jd2.user_service.model.MailEntity;

import java.util.List;

public interface IMailService {
    void create(MailDTO mail);
    void update (MailEntity entity);
    List<MailEntity> getByStatus(EMailStatus status);
}
