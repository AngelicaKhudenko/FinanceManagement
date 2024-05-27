package by.it_academy.jd2.finance_management.account_service.controller.converter;

import by.it_academy.jd2.finance_management.account_service.core.dto.AccountDTO;
import by.it_academy.jd2.finance_management.account_service.model.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityToDTOConverter implements Converter<AccountEntity, AccountDTO> {
    @Override
    public AccountDTO convert(AccountEntity item) {

        return AccountDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .title(item.getTitle())
                .description(item.getDescription())
                .balance(item.getBalance())
                .type(item.getType())
                .currency(item.getCurrency())
                .build();
    }
}
