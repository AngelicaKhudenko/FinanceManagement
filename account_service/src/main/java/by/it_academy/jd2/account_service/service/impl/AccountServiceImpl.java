package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.core.enums.EAccountType;
import by.it_academy.jd2.account_service.feign.ICabinetServiceFeignClient;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.repository.IAccountRepository;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import by.it_academy.jd2.account_service.token.dto.UserDTO;
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

    private final ICabinetServiceFeignClient cabinetServiceFeignClient;

    public AccountServiceImpl(ConversionService conversionService,
                              IAccountRepository accountRepository,
                              ICabinetServiceFeignClient cabinetServiceFeignClient) {
        this.conversionService = conversionService;
        this.accountRepository = accountRepository;
        this.cabinetServiceFeignClient = cabinetServiceFeignClient;
    }

    @Transactional
    @Override
    public void create(AccountCUDTO account) {

        if (EAccountType.getByName(account.getType().name()).isEmpty()) {
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

        if (EAccountType.getByName(account.getType().name()).isEmpty()) {
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

        UserDTO userDTO = this.cabinetServiceFeignClient.getUser("Bearer " + token);

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        return userDTO;
    }
}
