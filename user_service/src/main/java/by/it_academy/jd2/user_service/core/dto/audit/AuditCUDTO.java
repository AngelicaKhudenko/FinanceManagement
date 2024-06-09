package by.it_academy.jd2.user_service.core.dto.audit;

import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.core.enums.ETypeEssence;
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

    public boolean correctConstants(ETypeEssence type) {

        return ETypeEssence.getByName(type.name()).isPresent();
    }
}
