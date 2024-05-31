package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.core.dto.UserLoginDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;

public interface ICabinetService {
    void create(UserRegistrationCUDTO user);
    void login(UserLoginDTO user);
}
