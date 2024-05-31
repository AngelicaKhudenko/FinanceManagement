package by.it_academy.jd2.user_service.service.impl;

import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import by.it_academy.jd2.user_service.core.dto.UserLoginDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.ICabinetService;
import by.it_academy.jd2.user_service.service.api.IUserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements ICabinetService{
    private final Converter<UserRegistrationCUDTO, UserCUDTO> registrationConverter;
    private final IUserService userService;

    public CabinetServiceImpl(Converter<UserRegistrationCUDTO, UserCUDTO> registrationConverter,
                              IUserService userService) {

        this.registrationConverter = registrationConverter;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void create(UserRegistrationCUDTO user) {

        if (!user.fieldsChanged()){
            throw new IllegalArgumentException("Отсутствует достаточно данных о пользователе");
        }

        UserCUDTO userCUDTO = this.registrationConverter.convert(user);

        userCUDTO.setRole(EUserRole.USER);
        userCUDTO.setStatus(EUserStatus.WAITING_ACTIVATION);

        this.userService.create(userCUDTO);

        // ВЕРИФИКАЦИЯ
    }

    @Override
    public void login(UserLoginDTO user) {

        if (!user.fieldsChanged()) {
            throw new IllegalArgumentException("Не переданы параметры для входа");
        }

        String mail = user.getMail();
        String password = user.getPassword();

        Optional<UserEntity> optional = this.userService.getByMail(user.getMail());

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с указанной почтой отсутствует");
        }

        UserEntity entity = optional.get();

        if (!entity.getPassword().equals(password)){
            throw new IllegalArgumentException("Неверный пароль");
        }
    }
}
