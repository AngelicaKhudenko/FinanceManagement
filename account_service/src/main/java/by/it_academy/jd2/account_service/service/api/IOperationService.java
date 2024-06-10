package by.it_academy.jd2.account_service.service.api;

import by.it_academy.jd2.account_service.core.dto.OperationCUDTO;
import by.it_academy.jd2.account_service.model.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IOperationService {

    OperationEntity create(UUID uuid, OperationCUDTO operation);

    Page<OperationEntity> get(Pageable pageable);

    OperationEntity update(UUID uuid, UUID operation, Long updateDate, OperationCUDTO operationCUDTO);

    void delete(UUID uuid, UUID operation, Long updateDate);
}
