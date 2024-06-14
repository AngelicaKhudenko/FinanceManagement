package by.it_academy.jd2.account_service.core.converters;

import by.it_academy.jd2.account_service.core.dto.AccountCUDTO;
import by.it_academy.jd2.account_service.model.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountCUDTOToEntityConverter implements Converter<AccountCUDTO, AccountEntity> {
    @Override
    public AccountEntity convert(AccountCUDTO item) {

        AccountEntity entity = new AccountEntity();

        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setType(item.getType());
        entity.setCurrency(item.getCurrency());

        return entity;
    }
}