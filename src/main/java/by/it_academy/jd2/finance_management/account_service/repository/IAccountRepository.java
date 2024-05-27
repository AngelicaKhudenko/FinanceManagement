package by.it_academy.jd2.finance_management.account_service.repository;

import by.it_academy.jd2.finance_management.account_service.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {

}
