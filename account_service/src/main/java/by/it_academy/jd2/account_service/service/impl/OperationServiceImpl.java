package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.core.dto.CategoryDTO;
import by.it_academy.jd2.account_service.core.dto.CurrencyDTO;
import by.it_academy.jd2.account_service.core.dto.OperationCUDTO;
import by.it_academy.jd2.account_service.core.exceptions.FieldsIncorrectException;
import by.it_academy.jd2.account_service.service.feign.ICategoryServiceFeignClient;
import by.it_academy.jd2.account_service.service.feign.ICurrencyServiceFeignClient;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.model.OperationEntity;
import by.it_academy.jd2.account_service.repository.IOperationRepository;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import by.it_academy.jd2.account_service.service.api.IOperationService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements IOperationService {

    private final ConversionService conversionService;
    private final IOperationRepository operationRepository;
    private final IAccountService accountService;
    private final ICurrencyServiceFeignClient currencyFeign;
    private final ICategoryServiceFeignClient categoryFeign;

    public OperationServiceImpl(ConversionService conversionService,
                                IOperationRepository operationRepository,
                                IAccountService accountService,
                                ICurrencyServiceFeignClient currencyFeign,
                                ICategoryServiceFeignClient categoryFeign) {

        this.conversionService = conversionService;
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.currencyFeign = currencyFeign;
        this.categoryFeign = categoryFeign;
    }

    @Override
    @Transactional
    public OperationEntity create(UUID uuid, OperationCUDTO operation) {

        checkAccount(uuid);

        UUID currency = operation.getCurrency();
        UUID category = operation.getCategory();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (authentication != null && authentication.getDetails() instanceof String) {
            token = (String) authentication.getDetails();
        }

        CurrencyDTO currencyDTO = this.currencyFeign.get("Bearer " + token, currency);
        CategoryDTO categoryDTO = this.categoryFeign.get("Bearer " + token, category);

        if (currencyDTO == null || categoryDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        OperationEntity entity = this.conversionService.convert(operation,OperationEntity.class);

        entity.setUuid(UUID.randomUUID());

        entity.setAccount(this.accountService.get(uuid));

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        AccountEntity account = this.accountService.get(uuid);
        double balance = account.getBalance();
        balance += operation.getValue();
        account.setBalance(balance);

        this.accountService.update(account);

        return this.operationRepository.saveAndFlush(entity);
    }

    @Override
    public Page<OperationEntity> get(Pageable pageable) {

        return this.operationRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public OperationEntity update(UUID uuid, UUID operationUUID, Long updateDate, OperationCUDTO operationCUDTO) {

        checkAccount(uuid);

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        OperationEntity operation = get(operationUUID);

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!operation.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        if (!Objects.equals(operation.getValue(), operationCUDTO.getValue())) {

            double difference = operation.getValue() - operationCUDTO.getValue();

            AccountEntity account = this.accountService.get(uuid);
            double balance = account.getBalance();
            balance += difference;
            account.setBalance(balance);

            this.accountService.update(account);
        }

        operation.setDescription(operationCUDTO.getDescription());
        operation.setCategory(operationCUDTO.getCategory());
        operation.setValue(operationCUDTO.getValue());
        operation.setCurrency(operationCUDTO.getCurrency());

        return this.operationRepository.saveAndFlush(operation);
    }

    @Transactional
    @Override
    public void delete(UUID uuid, UUID operationUUID, Long updateDate) {

        checkAccount(uuid);

        OperationEntity operation = get(operationUUID);

        if (updateDate == null) {
            throw new FieldsIncorrectException("dt_update","Не передана дата прошлого обновления");
        }

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!operation.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        AccountEntity account = this.accountService.get(uuid);
        double balance = account.getBalance();
        balance -= operation.getValue();
        account.setBalance(balance);

        this.accountService.update(account);

        this.operationRepository.delete(operation);
    }

    private void checkAccount (UUID uuid) {

        if (uuid == null) {
            throw new FieldsIncorrectException("uuid","Не передан идентификатор счета");
        }

        AccountEntity account = this.accountService.get(uuid);

        if (account == null) {
            throw new FieldsIncorrectException("uuid","Счета с таким id не существует");
        }
    }

    private OperationEntity get(UUID operationUUID) {

        if (operationUUID == null) {
            throw new FieldsIncorrectException("uuid_operation","Не передан идентификатор операции");
        }

        Optional<OperationEntity> optional = this.operationRepository.findById(operationUUID);

        if (optional.isEmpty()) {
            throw new FieldsIncorrectException("uuid_operation","Операция с таким id не зарегистрирована");
        }

        return optional.get();
    }
}
