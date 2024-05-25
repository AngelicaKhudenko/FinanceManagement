package by.it_academy.jd2.finance_management.controller.http;

import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.service.api.IUserService;
import by.it_academy.jd2.finance_management.core.dto.UserDTO;
import by.it_academy.jd2.finance_management.core.dto.UserRegistrationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cabinet")
public class CabinetController {
    private final IUserService userService;
    private final Converter<UserEntity, UserDTO> converter;

    public CabinetController(IUserService userService,
                          Converter<UserEntity, UserDTO> converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @PostMapping(value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRegistrationDTO user) {

        this.userService.create(user);
    }
}
