package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.core.dto.LoginDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;
import by.it_academy.jd2.user_service.core.dto.VerificationDTO;

public interface ICabinetService {
    void create(UserRegistrationCUDTO user);
    void verify(VerificationDTO verificationDTO);
    String login(LoginDTO user);
}
