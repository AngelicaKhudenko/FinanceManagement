package by.it_academy.jd2.finance_management.service.impl;

import by.it_academy.jd2.finance_management.core.enums.PageSizeDTO;
import by.it_academy.jd2.finance_management.dao.api.IUserDao;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.service.api.IUserService;
import by.it_academy.jd2.finance_management.service.api.dto.UserCUDTO;
import jakarta.transaction.Transactional;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements IUserService {
    private final IUserDao userDao;
    private final Converter<UserCUDTO, UserEntity> converter;

    public UserServiceImpl(IUserDao userDao,
                           Converter<UserCUDTO,
                                   UserEntity> converter) {

        this.userDao = userDao;
        this.converter = converter;
    }

    @Transactional
    @Override
    public void create(UserCUDTO user) {

        if (!user.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о пользователе");
        }

        if (!user.correctConstants(user.getRole(),user.getStatus())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        UserEntity entity = converter.convert(user);

        LocalDateTime creation = LocalDateTime.now();

        entity.setUuid(UUID.randomUUID());
        entity.setCreation(creation);
        entity.setUpdate(creation);
        entity.setVersion(creation);

        this.userDao.create(entity);
    }

    @Transactional
    @Override
    public List<UserEntity> get(PageSizeDTO pageSize) {

        return this.userDao.get(pageSize);
    }

    @Transactional
    @Override
    public UserEntity get(UUID uuid) {

        Optional<UserEntity> optional = this.userDao.get(uuid);

        if (optional.isEmpty()){
            throw new IllegalArgumentException("Пользователь с таким id отсутствует");
        }

        return optional.get();
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long updateDate, UserCUDTO user) {

        if (uuid == null) {
            throw new IllegalArgumentException("Не передан id");
        }

        if (updateDate == null) {
            throw new IllegalArgumentException("Не передана дата прошлого обновления");
        }

        Optional<UserEntity> optional = this.userDao.get(uuid);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с таким id не зарегистрирвоан");
        }

        if (!user.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о пользователе");
        }

        if (!user.correctConstants(user.getRole(),user.getStatus())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        UserEntity entity = converter.convert(user);

        entity.setUuid(uuid);
        entity.setVersion(updateDate);
        entity.setUpdate(LocalDateTime.now());

        this.userDao.update(uuid,entity);
    }
}
