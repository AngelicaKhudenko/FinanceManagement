package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.core.exceptions.FieldsIncorrectException;
import by.it_academy.jd2.user_service.repository.IUserRepository;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;
    private final ConversionService conversionService;

    public UserServiceImpl(IUserRepository userRepository,
                           PasswordEncoder encoder,
                           ConversionService conversionService) {

        this.userRepository = userRepository;
        this.encoder = encoder;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public UserEntity create(UserCUDTO user) {

        if (EUserRole.getByName(user.getRole().name()).isEmpty()) {
            throw new FieldsIncorrectException("role","Переданы некорректные значения констант");
        }

        if (EUserStatus.getByName(user.getStatus().name()).isEmpty()) {
            throw new FieldsIncorrectException("status","Переданы некорректные значения констант");
        }

        Optional<UserEntity> optional = getByMail(user.getMail());

        if (optional.isPresent()) {
            throw new FieldsIncorrectException("mail","Пользователь с таким адресом электронной почты уже существует");
        }

        UserEntity entity = this.conversionService.convert(user,UserEntity.class);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        entity.setPassword(this.encoder.encode(user.getPassword()));

        return this.userRepository.saveAndFlush(entity);
    }

    @Override
    public Page<UserEntity> get(Pageable pageable) {

        return this.userRepository.findAll(pageable);
    }

    @Override
    public UserEntity get(UUID uuid) {

        Optional<UserEntity> optional = this.userRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new FieldsIncorrectException("uuid","Пользователь с таким id отсутствует");
        }

        return optional.get();
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long updateDate, UserCUDTO user) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан id");
        }

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        if (EUserRole.getByName(user.getRole().name()).isEmpty()) {
            throw new FieldsIncorrectException("role","Переданы некорректные значения констант");
        }

        if (EUserStatus.getByName(user.getStatus().name()).isEmpty()) {
            throw new FieldsIncorrectException("status","Переданы некорректные значения констант");
        }

        Optional<UserEntity> optional = this.userRepository.findById(uuid);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирвоан");
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

        this.userRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity entity) {

        Optional<UserEntity> optional = this.userRepository.findById(entity.getUuid());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирвоан");
        }

        UserEntity entityDB = optional.get();

        if (!entityDB.getUpdate().equals(entity.getUpdate())) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.userRepository.saveAndFlush(entity);
        return entityDB;
    }

    @Override
    public Optional<UserEntity> getByMail (String mail) {

        if (mail == null || mail.isBlank()) {
            throw new FieldsIncorrectException("mail","Переданный адрес электронной почты пуст");
        }

        UserEntity entity = this.userRepository.findByMail(mail);

        return Optional.ofNullable(entity);
    }
}
