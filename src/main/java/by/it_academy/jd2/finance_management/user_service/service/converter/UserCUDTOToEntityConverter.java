package by.it_academy.jd2.finance_management.user_service.service.converter;

import by.it_academy.jd2.finance_management.user_service.model.UserEntity;
import by.it_academy.jd2.finance_management.user_service.core.dto.UserCUDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCUDTOToEntityConverter implements Converter<UserCUDTO, UserEntity> {
    @Override
    public UserEntity convert(UserCUDTO item) {

        UserEntity entity = new UserEntity();

        entity.setMail(item.getMail());
        entity.setFio(item.getFio());
        entity.setRole(item.getRole());
        entity.setStatus(item.getStatus());
        entity.setPassword(item.getPassword());

        return entity;
    }
}
