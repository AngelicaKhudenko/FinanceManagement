package by.it_academy.jd2.audit_service.core.dto;

import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditCUDTO {

    private UserActingDTO user;
    @NotEmpty
    @NotNull
    private String text;
    @NotNull
    private ETypeEssence type;
    @NotEmpty
    @NotNull
    private String id;
}
