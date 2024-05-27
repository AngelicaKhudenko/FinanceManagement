package by.it_academy.jd2.user_service.service.converter;

import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDTOToEntityConverter implements Converter<UserRegistrationDTO, UserEntity> {
    @Override
    public UserEntity convert(UserRegistrationDTO item) {

        UserEntity entity = new UserEntity();

        entity.setMail(item.getMail());
        entity.setFio(item.getFio());
        entity.setRole(EUserRole.USER);
        entity.setStatus(EUserStatus.WAITING_ACTIVATION);
        entity.setPassword(item.getPassword());

        return entity;
    }
}
