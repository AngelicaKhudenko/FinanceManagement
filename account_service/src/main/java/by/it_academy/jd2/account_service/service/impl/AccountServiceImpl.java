package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.repository.IAccountRepository;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import by.it_academy.jd2.account_service.token.dto.UserDTO;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    private final String urlUserService = "/cabinet/me";

    public AccountServiceImpl(ConversionService conversionService,
                              IAccountRepository accountRepository) {
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void create(AccountCUDTO account) {

        if (!account.correctConstants(account.getType())) {
            throw new IllegalArgumentException("Переданы некорректные значения констант");
        }

        AccountEntity entity = this.conversionService.convert(account, AccountEntity.class);

        entity.setUuid(UUID.randomUUID());

        entity.setCreation(LocalDateTime.now());

        UserDTO userDTO = getUserByToken();

        entity.setUser(userDTO.getUuid());

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

    @Override
    public Page<AccountEntity> get(Pageable pageable) {

        UserDTO userDTO = getUserByToken();

        return this.accountRepository.findAllByUser(pageable, userDTO.getUuid());
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

        this.accountRepository.saveAndFlush(entity);
    }

    private UserDTO getUserByToken () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<UserDTO> response = new RestTemplate().exchange(
                this.urlUserService,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDTO.class
        );

        UserDTO userDTO = response.getBody();

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        return userDTO;
    }
}
