package by.it_academy.jd2.user_service.controller.http;

import by.it_academy.jd2.user_service.core.dto.LoginDTO;
import by.it_academy.jd2.user_service.core.dto.VerificationDTO;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.core.dto.UserRegistrationCUDTO;
import by.it_academy.jd2.user_service.service.api.ICabinetService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    private final ICabinetService cabinetService;
    private final ConversionService conversionService;

    public CabinetController(ICabinetService cabinetService,
                             ConversionService conversionService) {

        this.cabinetService = cabinetService;
        this.conversionService = conversionService;
    }

    @PostMapping(value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRegistrationCUDTO user) {

        this.cabinetService.create(user);
    }

    @GetMapping(value = "/verification")
    public void verify(@RequestParam(value = "code") String code,
                       @RequestParam(value = "mail") String mail) {

        VerificationDTO verificationDTO = VerificationDTO
                .builder()
                .code(code)
                .mail(mail)
                .build();

        this.cabinetService.verify(verificationDTO);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginDTO user) {

        return this.cabinetService.login(user);
    }

    @GetMapping(value = "/me")
    public UserDTO getInfoAboutMe() {

        UserEntity entity = this.cabinetService.getInfoAboutMe();

        return this.conversionService.convert(entity,UserDTO.class);
    }
}
