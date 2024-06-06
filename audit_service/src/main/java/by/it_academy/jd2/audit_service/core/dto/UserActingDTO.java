package by.it_academy.jd2.audit_service.core.dto;

import by.it_academy.jd2.audit_service.token.enums.EUserRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActingDTO {

    private UUID uuid;
    private String mail;
    private String fio;
    private EUserRole role;
}
