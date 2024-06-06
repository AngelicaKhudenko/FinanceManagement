package by.it_academy.jd2.audit_service.controller.converter;

import by.it_academy.jd2.audit_service.core.dto.AuditDTO;

import by.it_academy.jd2.audit_service.core.dto.UserActingDTO;
import by.it_academy.jd2.audit_service.model.AuditEntity;
import by.it_academy.jd2.audit_service.token.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Component
public class AuditEntityToDTOConverter implements Converter<AuditEntity, AuditDTO> {

    private final String urlUserService = "/api/v1/users/";
    @Override
    public AuditDTO convert(AuditEntity item) {

        UserActingDTO user = getUser(item.getUser());

        return AuditDTO.builder()
                .uuid(item.getUuid())
                .creation(item.getCreation())
                .user(user)
                .text(item.getText())
                .type(item.getType())
                .id(item.getId())
                .build();
    }

    private UserActingDTO getUser(UUID uuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<UserDTO> response = new RestTemplate().exchange(
                this.urlUserService + uuid,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDTO.class
        );

        UserDTO userDTO = response.getBody();

        if (userDTO == null) {
            throw new IllegalStateException("Ошибка при обработке токена");
        }

        UserActingDTO user = UserActingDTO.builder()
                .uuid(userDTO.getUuid())
                .mail(userDTO.getMail())
                .fio(userDTO.getFio())
                .role(userDTO.getRole())
                .build();

        return user;
    }
}
