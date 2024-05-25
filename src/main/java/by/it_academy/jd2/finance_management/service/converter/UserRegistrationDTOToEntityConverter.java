package by.it_academy.jd2.finance_management.service.converter;

import by.it_academy.jd2.finance_management.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.core.enums.EUserStatus;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.core.dto.UserRegistrationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserRegistrationDTOToEntityConverter implements Converter<UserRegistrationDTO, UserEntity> {
    @Override
    public UserEntity convert(UserRegistrationDTO item) {

        UserEntity entity = new UserEntity();

        entity.setUuid(UUID.randomUUID());

        LocalDateTime creation = LocalDateTime.now();
        entity.setCreation(creation);
        entity.setUpdate(creation);
        entity.setMail(item.getMail());
        entity.setFio(item.getFio());
        entity.setRole(EUserRole.USER);
        entity.setStatus(EUserStatus.WAITING_ACTIVATION);
        entity.setPassword(item.getPassword());

        return entity;
    }
}
