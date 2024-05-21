package by.it_academy.jd2.finance_management.dao.api;

import by.it_academy.jd2.finance_management.core.enums.PageSizeDTO;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserDao {
    void create(UserEntity entity);
    Optional<UserEntity> get(UUID uuid);
    List<UserEntity> get(PageSizeDTO pageSize);
    void update(UUID uuid, UserEntity entity);
}
