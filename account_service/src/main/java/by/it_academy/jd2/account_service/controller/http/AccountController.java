package by.it_academy.jd2.account_service.controller.http;

import by.it_academy.jd2.account_service.core.dto.AccountDTO;
import by.it_academy.jd2.account_service.core.dto.PageDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    private final IAccountService accountService;
    private final Converter<AccountEntity, AccountDTO> entityAccountDTOConverter;


    public AccountController(IAccountService accountService,
                             Converter<AccountEntity, AccountDTO> entityAccountDTOConverter) {

        this.accountService = accountService;
        this.entityAccountDTOConverter = entityAccountDTOConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AccountCUDTO account) {

        this.accountService.create(account);
    }

    @GetMapping
    public PageDTO<AccountDTO> get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AccountEntity> entities = this.accountService.get(pageable);

        Page<AccountDTO> accountDTOS = entities.map(this.entityAccountDTOConverter::convert);

        return new PageDTO<>(accountDTOS);
    }
    @GetMapping(value = "/{uuid}")
    public AccountDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        AccountEntity entity = this.accountService.get(uuid);

        return this.entityAccountDTOConverter.convert(entity);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody AccountCUDTO account) {

        this.accountService.update(uuid, updateDate, account);
    }
}
