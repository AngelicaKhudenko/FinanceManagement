package by.it_academy.jd2.audit_service.core.dto;

import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
import by.it_academy.jd2.audit_service.token.dto.UserDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditCUDTO {
    @NotNull
    private UserDTO user;
    @NotEmpty
    @NotNull
    private String text;
    @NotNull
    private ETypeEssence type;
    @NotEmpty
    @NotNull
    private String id;
}
