package by.it_academy.jd2.finance_management.service.api.dto;

import by.it_academy.jd2.finance_management.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.core.enums.EUserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID uuid;
    private LocalDateTime creation;
    private LocalDateTime update;
    private String mail;
    private String fio;
    private EUserRole role;
    private EUserStatus status;
}
