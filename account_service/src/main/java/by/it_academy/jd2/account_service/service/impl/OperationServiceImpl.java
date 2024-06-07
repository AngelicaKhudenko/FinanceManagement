package by.it_academy.jd2.account_service.service.impl;

import by.it_academy.jd2.account_service.core.dto.OperationCUDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.model.OperationEntity;
import by.it_academy.jd2.account_service.repository.IOperationRepository;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import by.it_academy.jd2.account_service.service.api.IOperationService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

public class OperationServiceImpl implements IOperationService {

    private final ConversionService conversionService;
    private final IOperationRepository operationRepository;
    private final IAccountService accountService;

    public OperationServiceImpl(ConversionService conversionService,
                                IOperationRepository operationRepository,
                                IAccountService accountService) {
        this.conversionService = conversionService;
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }

    @Override
    public void create(UUID uuid, OperationCUDTO operation) {

        checkAccount(uuid);

        OperationEntity entity = this.conversionService.convert(operation,OperationEntity.class);

        entity.setUuid(UUID.randomUUID());

        entity.setAccount(this.accountService.get(uuid));

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);

        this.operationRepository.saveAndFlush(entity);
    }

    @Override
    public Page<OperationEntity> get(Pageable pageable) {

        return this.operationRepository.findAll(pageable);
    }

    @Override
    public void update(UUID uuid, UUID operationUUID, Long updateDate, OperationCUDTO operationCUDTO) {

        checkAccount(uuid);

        if (updateDate == null) {
            throw new IllegalArgumentException("Не передана дата прошлого обновления");
        }

        if (!operationCUDTO.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных об операции");
        }

        OperationEntity operation = get(operationUUID);

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!operation.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        operation.setDescription(operationCUDTO.getDescription());
        operation.setCategory(operationCUDTO.getCategory());
        operation.setValue(operationCUDTO.getValue());
        operation.setCurrency(operationCUDTO.getCurrency());

        this.operationRepository.saveAndFlush(operation);
    }

    @Override
    public void delete(UUID uuid, UUID operationUUID, Long updateDate) {

        checkAccount(uuid);

        OperationEntity operation = get(operationUUID);

        if (updateDate == null) {
            throw new IllegalArgumentException("Не передана дата прошлого обновления");
        }

        Instant instant = Instant.ofEpochMilli(updateDate);
        LocalDateTime updateDateLocal = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        if (!operation.getUpdate().equals(updateDateLocal)) {
            throw new OptimisticLockException("Несоответствие версий. Данные были обновлены другим пользователем");
        }

        this.operationRepository.delete(operation);
    }

    private void checkAccount (UUID uuid) {

        if (uuid == null) {
            throw new IllegalArgumentException("Не передан идентификатор счета");
        }

        AccountEntity account = this.accountService.get(uuid);

        if (account == null) {
            throw new IllegalArgumentException("Счета с таким id не существует");
        }
    }

    private OperationEntity get(UUID operationUUID) {

        if (operationUUID == null) {
            throw new IllegalArgumentException("Не передан идентификатор операции");
        }

        Optional<OperationEntity> optional = this.operationRepository.findById(operationUUID);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Операция с таким id не зарегистрирована");
        }

        return optional.get();
    }
}
