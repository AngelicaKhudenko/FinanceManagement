package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.account_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.account_service.core.dto.CurrencyDTO;
import by.it_academy.jd2.account_service.core.enums.EAccountType;
import by.it_academy.jd2.account_service.core.exceptions.FieldsIncorrectException;
import by.it_academy.jd2.account_service.service.feign.ICurrencyServiceFeignClient;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.repository.IAccountRepository;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import by.it_academy.jd2.account_service.controller.token.dto.UserDTO;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ConversionService conversionService;
    private final IAccountRepository accountRepository;
    private final JwtTokenHandler tokenHandler;
    private final ICurrencyServiceFeignClient currencyServiceFeignClient;

    public AccountServiceImpl(ConversionService conversionService,
                              IAccountRepository accountRepository, JwtTokenHandler tokenHandler,
                              ICurrencyServiceFeignClient currencyServiceFeignClient) {

        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
        this.tokenHandler = tokenHandler;
        this.currencyServiceFeignClient = currencyServiceFeignClient;
    }

    @Transactional
    @Override
    public AccountEntity create(AccountCUDTO account) {

        if (EAccountType.getByName(account.getType().name()).isEmpty()) {
            throw new FieldsIncorrectException("type", "Переданы некорректные значения констант");
        }

        UUID currency = account.getCurrency();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (authentication != null && authentication.getDetails() instanceof String) {
            token = (String) authentication.getDetails();
        }

        CurrencyDTO currencyDTO = this.currencyServiceFeignClient.get("Bearer " + token, currency);

        if (currencyDTO == null) {
            throw new FieldsIncorrectException("currency","Ошибка при указании валюты");
        }

        AccountEntity entity = this.conversionService.convert(account, AccountEntity.class);

        entity.setUuid(UUID.randomUUID());

        entity.setCreation(LocalDateTime.now());

        UserDTO userDTO = getUserByToken();

        entity.setUser(userDTO.getUuid());

        return this.accountRepository.saveAndFlush(entity);
    }

    @Override
    public AccountEntity get(UUID uuid) {

        Optional<AccountEntity> optional = this.accountRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new FieldsIncorrectException("uuid","Счет с таким id отсутствует");
        }

        return optional.get();
    }

    @Override
    public Page<AccountEntity> get(Pageable pageable) {

        UserDTO userDTO = getUserByToken();

        return this.accountRepository.findAllByUser(pageable, userDTO.getUuid());
    }

    @Transactional
    @Override
    public AccountEntity update(UUID uuid, Long updateDate, AccountCUDTO account) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан id");
        }

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        if (EAccountType.getByName(account.getType().name()).isEmpty()) {
            throw new FieldsIncorrectException("type","Переданы некорректные значения констант");
        }

        Optional<AccountEntity> optional = this.accountRepository.findById(uuid);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Счет с таким id не зарегистрирвоан");
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

        this.accountRepository.saveAndFlush(entity);
        return entity;
    }

    @Transactional
    @Override
    public AccountEntity update(AccountEntity entity) {

        Optional<AccountEntity> optional = this.accountRepository.findById(entity.getUuid());

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid","Пользователь с таким id не зарегистрирвоан");
        }

        AccountEntity entityDB = optional.get();

        if (!entityDB.getUpdate().equals(entity.getUpdate())) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.accountRepository.saveAndFlush(entity);
        return entityDB;
    }

    private UserDTO getUserByToken () {

        UserDetailsExpanded userDetailsExpanded = (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = userDetailsExpanded.getUser();

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        return userDTO;
    }
}
