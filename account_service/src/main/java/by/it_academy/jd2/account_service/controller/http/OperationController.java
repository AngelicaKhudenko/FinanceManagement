package by.it_academy.jd2.account_service.controller.http;

import by.it_academy.jd2.account_service.core.dto.OperationCUDTO;
import by.it_academy.jd2.account_service.core.dto.OperationDTO;
import by.it_academy.jd2.account_service.core.dto.PageOperationDTO;
import by.it_academy.jd2.account_service.model.OperationEntity;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/account/{uuid}/operation")
public class OperationController {
    private final IAccountService accountService;

    private final Converter<OperationEntity, OperationDTO> entityOperationDTOConverter;
    private final Converter<Page<OperationDTO>, PageOperationDTO> pageOperationDTOConverter;

    public OperationController(IAccountService accountService,
                               Converter<OperationEntity, OperationDTO> entityOperationDTOConverter,
                               Converter<Page<OperationDTO>, PageOperationDTO> pageOperationDTOConverter) {

        this.accountService = accountService;
        this.entityOperationDTOConverter = entityOperationDTOConverter;
        this.pageOperationDTOConverter = pageOperationDTOConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable(value = "uuid") UUID uuid,
                       @RequestBody OperationCUDTO operation) {

        this.accountService.createOperation(uuid, operation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageOperationDTO get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<OperationEntity> entities = this.accountService.getOperation(pageable);

        Page<OperationDTO> operationDTOS = entities.map(this.entityOperationDTOConverter::convert);

        return this.pageOperationDTOConverter.convert(operationDTOS);
    }
}
