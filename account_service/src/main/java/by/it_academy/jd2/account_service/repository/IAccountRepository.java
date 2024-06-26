package by.it_academy.jd2.account_service.repository;

import by.it_academy.jd2.account_service.model.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {

    Page<AccountEntity> findAllByUser(Pageable pageable, UUID userUuid);
}
