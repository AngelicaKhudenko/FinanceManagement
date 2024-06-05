package by.it_academy.jd2.account_service.service.api;

import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAccountService {

    void create(AccountCUDTO account);

    AccountEntity get(UUID uuid);

    void update(UUID uuid, Long updateDate, AccountCUDTO account);

    Page<AccountEntity> get(Pageable pageable);
}
