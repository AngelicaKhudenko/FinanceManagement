package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    UserEntity create(UserCUDTO user);
    Page<UserEntity> get(Pageable pageable);
    UserEntity get(UUID uuid);
    void update(UUID uuid, Long updateDate, UserCUDTO user);
    void update(UserEntity entity);
    Optional<UserEntity> getByMail(String mail);
}
