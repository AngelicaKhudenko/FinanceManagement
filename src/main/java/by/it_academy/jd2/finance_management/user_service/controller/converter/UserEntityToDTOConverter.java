package by.it_academy.jd2.finance_management.user_service.controller.converter;

import by.it_academy.jd2.finance_management.user_service.model.UserEntity;
import by.it_academy.jd2.finance_management.user_service.core.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDTOConverter implements Converter<UserEntity, UserDTO> {
    @Override
    public UserDTO convert(UserEntity item) {

        return UserDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .update(item.getUpdate())
                .mail(item.getMail())
                .fio(item.getFio())
                .role(item.getRole())
                .status(item.getStatus())
                .build();
    }
}
