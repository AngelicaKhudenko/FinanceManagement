package by.it_academy.jd2.user_service.controller.http;

import by.it_academy.jd2.user_service.core.dto.UserLoginDTO;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;
import by.it_academy.jd2.user_service.service.api.ICabinetService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cabinet")
public class CabinetController {
    private final ICabinetService cabinetService;
    private final Converter<UserEntity, UserDTO> converter;

    public CabinetController(ICabinetService cabinetService,
                          Converter<UserEntity, UserDTO> converter) {

        this.cabinetService = cabinetService;
        this.converter = converter;
    }

    @PostMapping(value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRegistrationCUDTO user) {

        this.cabinetService.create(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody UserLoginDTO user) {

        this.cabinetService.login(user);
    }
}
