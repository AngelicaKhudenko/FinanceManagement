package by.it_academy.jd2.finance_management.controller.http;

import by.it_academy.jd2.finance_management.controller.factory.AppFactory;
import by.it_academy.jd2.finance_management.core.enums.PageSizeDTO;
import by.it_academy.jd2.finance_management.dao.entity.UserEntity;
import by.it_academy.jd2.finance_management.service.api.IUserService;
import by.it_academy.jd2.finance_management.service.api.dto.UserCUDTO;
import by.it_academy.jd2.finance_management.service.api.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;
    private final ObjectMapper mapper = AppFactory.getObjectMapper();
    private final Converter<UserEntity, UserDTO> converter;

    public UserController(IUserService userService,
                          Converter<UserEntity, UserDTO> converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCUDTO user) {

        this.userService.create(user);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String get(@RequestParam(value = "page", required = false) Integer page,
                      @RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {

        PageSizeDTO pageSize = Objects.nonNull(page) && Objects.nonNull(size)
                ? PageSizeDTO.of(page, size)
                : PageSizeDTO.defaultParam();

        List<UserEntity> entities = this.userService.get(pageSize);

        List<UserDTO> userDTOS = entities
                .stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());

        return mapper.writeValueAsString(userDTOS);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public String getById(@PathVariable(value = "uuid") UUID uuid) throws JsonProcessingException {

        UserEntity entity = this.userService.get(uuid);

        return mapper.writeValueAsString(converter.convert(entity));
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long updateDate,
                       @RequestBody UserCUDTO user) {

        this.userService.update(uuid, updateDate, user);
    }
}
