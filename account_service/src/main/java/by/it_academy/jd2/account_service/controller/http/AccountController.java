package by.it_academy.jd2.account_service.controller.http;

import by.it_academy.jd2.account_service.core.dto.AccountDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.service.api.IAccountService;
import org.springframework.core.convert.converter.Converter;
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

        this.accountService.createOperation(account);
    }

    @GetMapping(value = "/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        AccountEntity entity = this.accountService.getOperation(uuid);

        return this.entityAccountDTOConverter.convert(entity);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody AccountCUDTO account) {

        this.accountService.update(uuid, updateDate, account);
    }
}
