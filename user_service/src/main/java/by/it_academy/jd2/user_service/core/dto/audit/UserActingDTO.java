package by.it_academy.jd2.user_service.core.dto.audit;

import by.it_academy.jd2.user_service.core.enums.EUserRole;
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
