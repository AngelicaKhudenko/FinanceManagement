package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.repository.IAccountRepository;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements IAccountService {

    private final Converter<AccountCUDTO, AccountEntity> creationConverter;
    private final IAccountRepository accountRepository;

    public AccountServiceImpl(Converter<AccountCUDTO, AccountEntity> creationConverter,
                              IAccountRepository accountRepository) {

        this.creationConverter = creationConverter;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void create(AccountCUDTO account) {

        if (!account.fieldsChanged()) {
            throw new IllegalArgumentException("Отсутствует достаточно данных о счете");
        }

        if (!account.correctConstants(account.getType())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        AccountEntity entity = this.creationConverter.convert(account);

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);
        entity.setUpdate(creation);

        this.accountRepository.saveAndFlush(entity);
    }

    @Override
    public AccountEntity get(UUID uuid) {

        Optional<AccountEntity> optional = this.accountRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new IllegalArgumentException("Счет с таким id отсутствует");
        }

        return optional.get();
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long updateDate, AccountCUDTO account) {

        if (uuid == null) {
            throw new IllegalArgumentException("Не передан id");
        }

        if (updateDate == null) {
            throw new IllegalArgumentException("Не передана дата прошлого обновления");
        }

        if (!account.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о счете");
        }

        if (!account.correctConstants(account.getType())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        Optional<AccountEntity> optional = this.accountRepository.findById(uuid);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Счет с таким id не зарегистрирвоан");
        }

        AccountEntity entity = optional.get();

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!entity.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        entity.setTitle(account.getTitle());
        entity.setDescription(account.getDescription());
        entity.setType(account.getType());
        entity.setCurrency(account.getCurrency());

        entity.setUpdate(LocalDateTime.now());

        this.accountRepository.saveAndFlush(entity);
    }
}
