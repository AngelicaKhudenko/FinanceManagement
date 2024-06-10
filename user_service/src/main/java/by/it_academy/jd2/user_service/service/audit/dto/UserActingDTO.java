package by.it_academy.jd2.user_service.service.audit.dto;

import by.it_academy.jd2.user_service.core.enums.EUserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActingDTO {
    @NotNull
    private UUID uuid;
    @NotNull
    @NotEmpty
    private String mail;
    @NotNull
    @NotEmpty
    private String fio;
    @NotNull
    private EUserRole role;
}
