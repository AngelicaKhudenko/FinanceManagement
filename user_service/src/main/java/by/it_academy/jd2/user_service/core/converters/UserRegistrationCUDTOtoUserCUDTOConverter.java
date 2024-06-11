package by.it_academy.jd2.user_service.core.converters;

import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCUDTOtoUserCUDTOConverter implements Converter<UserRegistrationCUDTO, UserCUDTO> {
    @Override
    public UserCUDTO convert(UserRegistrationCUDTO item) {

        UserCUDTO userCUDTO = UserCUDTO
                .builder()
                .mail(item.getMail())
                .fio(item.getFio())
                .password(item.getPassword())
                .build();

        return userCUDTO;
    }
}
