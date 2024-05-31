package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.repository.IUserRepository;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final Converter<UserCUDTO, UserEntity> creationConverter;


    public UserServiceImpl(IUserRepository userRepository,
                           Converter<UserCUDTO, UserEntity> creationConverter) {

        this.userRepository = userRepository;
        this.creationConverter = creationConverter;
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

        Optional<UserEntity> optional = getByMail(user.getMail());

        if (optional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким адресом электронной почты уже существует");
        }

        UserEntity entity = this.creationConverter.convert(user);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);
        entity.setUpdate(creation);

        this.userRepository.saveAndFlush(entity);
    }

    @Override
    public Page<UserEntity> get(Pageable pageable) {

        return this.userRepository.findAll(pageable);
    }

    @Override
    public UserEntity get(UUID uuid) {

        Optional<UserEntity> optional = this.userRepository.findById(uuid);

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

        if (!user.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о пользователе");
        }

        if (!user.correctConstants(user.getRole(),user.getStatus())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        Optional<UserEntity> optional = this.userRepository.findById(uuid);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с таким id не зарегистрирвоан");
        }

        UserEntity entity = optional.get();

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!entity.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        entity.setMail(user.getMail());
        entity.setFio(user.getFio());
        entity.setRole(user.getRole());
        entity.setStatus(user.getStatus());
        entity.setPassword(user.getPassword());

        entity.setUpdate(LocalDateTime.now());

        this.userRepository.saveAndFlush(entity);
    }

    @Override
    public Optional<UserEntity> getByMail (String mail) {

        if (mail == null || mail.isBlank()) {
            throw new IllegalStateException("Переданный адрес электронной почты пуст");
        }

        UserEntity entity = this.userRepository.findByMail(mail);

        return Optional.ofNullable(entity);
    }
}
