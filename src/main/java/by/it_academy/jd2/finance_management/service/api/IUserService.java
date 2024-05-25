package by.it_academy.jd2.finance_management.service.api;

import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.core.dto.UserCUDTO;
import by.it_academy.jd2.finance_management.core.dto.UserRegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserService {

    void create(UserCUDTO user);
    void create(UserRegistrationDTO user);
    Page<UserEntity> get(Pageable pageable);
    UserEntity get(UUID uuid);
    void update(UUID uuid, Long updateDate, UserCUDTO user);
}
