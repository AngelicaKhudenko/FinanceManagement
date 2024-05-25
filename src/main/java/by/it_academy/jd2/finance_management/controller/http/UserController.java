package by.it_academy.jd2.finance_management.controller.http;

import by.it_academy.jd2.finance_management.core.dto.PageUserDTO;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.service.api.IUserService;
import by.it_academy.jd2.finance_management.core.dto.UserCUDTO;
import by.it_academy.jd2.finance_management.core.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final Converter<UserEntity, UserDTO> entityUserDTOConverter;
    private final Converter<Page<UserDTO>,PageUserDTO> pageUserDTOConverter;

    public UserController(IUserService userService,
                          Converter<UserEntity, UserDTO> entityUserDTOConverter,
                          Converter<Page<UserDTO>, PageUserDTO> pageUserDTOConverter) {

        this.userService = userService;
        this.entityUserDTOConverter = entityUserDTOConverter;
        this.pageUserDTOConverter = pageUserDTOConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCUDTO user) {

        this.userService.create(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageUserDTO get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> entities = this.userService.get(pageable);

        Page<UserDTO> userDTOS = entities.map(this.entityUserDTOConverter::convert);

        return this.pageUserDTOConverter.convert(userDTOS);
    }

    @GetMapping(value = "/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        UserEntity entity = this.userService.get(uuid);

        return this.entityUserDTOConverter.convert(entity);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody UserCUDTO user) {

        this.userService.update(uuid, updateDate, user);
    }
}
