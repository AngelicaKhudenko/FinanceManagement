package by.it_academy.jd2.user_service;

import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.service.api.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSetUp implements CommandLineRunner {
    private final IUserService userService;

    public AdminSetUp(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        UserCUDTO admin = UserCUDTO
                .builder()
                .mail(System.getenv("MAIL_LOGIN"))
                .fio(System.getenv("ADMIN_FIO"))
                .role(EUserRole.ADMIN)
                .status(EUserStatus.ACTIVATED)
                .password(System.getenv("ADMIN_PASSWORD"))
                .build();

        this.userService.create(admin);
    }
}
