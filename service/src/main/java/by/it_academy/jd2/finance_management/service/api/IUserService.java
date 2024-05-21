package by.it_academy.jd2.finance_management.service.api;

import by.it_academy.jd2.finance_management.core.enums.PageSizeDTO;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.service.api.dto.UserCUDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    void create(UserCUDTO user);
    List<UserEntity> get(PageSizeDTO pageSize);
    UserEntity get(UUID uuid);
    void update(UUID uuid, Long updateDate, UserCUDTO user);
}
