package by.it_academy.jd2.user_service.controller.http;

import by.it_academy.jd2.user_service.core.dto.PageDTO;
import by.it_academy.jd2.user_service.core.dto.audit.AuditCUDTO;
import by.it_academy.jd2.user_service.core.enums.ETypeEssence;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.core.dto.UserCUDTO;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConversionService conversionService;
    private final String urlAudit = "/audit";
    private final String urlUserService = "/cabinet/me";

    private final String create = "Создание пользователя по url: /users";

    public UserController(IUserService userService,
                          ConversionService conversionService) {

        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCUDTO user) {

        UserEntity entity = this.userService.create(user);

        UserDTO userDTO = getUser();

        audit(this.create,userDTO,entity.getUuid().toString());
    }

    @GetMapping
    public PageDTO<UserDTO> get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> entities = this.userService.get(pageable);

        Page<UserDTO> userDTOS = entities.map(entity -> conversionService.convert(entity, UserDTO.class));

        return new PageDTO<>(userDTOS);
    }

    @GetMapping(value = "/{uuid}")
    public UserDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        UserEntity entity = this.userService.get(uuid);

        return this.conversionService.convert(entity,UserDTO.class);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody UserCUDTO user) {

        this.userService.update(uuid, updateDate, user);
    }

    @GetMapping(value = "/details")
    public UserDetails details(){

        return this.userService.getDetails();
    }

    private void audit(String text, UserDTO user, String id) {

        AuditCUDTO auditCUDTO = AuditCUDTO
                .builder()
                .user(user)
                .text(text)
                .type(ETypeEssence.USER)
                .id(id)
                .build();

        new RestTemplate().postForObject(this.urlAudit, auditCUDTO, Void.class);
    }

    private UserDTO getUser () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<UserDTO> response = new RestTemplate().exchange(
                this.urlUserService,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDTO.class
        );

        UserDTO userDTO = response.getBody();

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        return userDTO;
    }
}
