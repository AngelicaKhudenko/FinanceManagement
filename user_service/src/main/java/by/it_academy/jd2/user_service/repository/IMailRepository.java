package by.it_academy.jd2.user_service.repository;

import by.it_academy.jd2.user_service.core.enums.EMailStatus;
import by.it_academy.jd2.user_service.model.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface IMailRepository extends JpaRepository<MailEntity, UUID> {
    List<MailEntity> findAllByStatus(EMailStatus status);
}
