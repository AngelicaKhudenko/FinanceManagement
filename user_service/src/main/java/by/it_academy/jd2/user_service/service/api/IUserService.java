package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.core.dto.UserLoginDTO;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserService {

    void create(UserCUDTO user);
    void create(UserRegistrationDTO user);
    Page<UserEntity> get(Pageable pageable);
    UserEntity get(UUID uuid);
    void update(UUID uuid, Long updateDate, UserCUDTO user);

    void login(UserLoginDTO user);
}
