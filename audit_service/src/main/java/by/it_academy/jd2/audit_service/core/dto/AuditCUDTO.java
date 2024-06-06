package by.it_academy.jd2.audit_service.core.dto;

import by.it_academy.jd2.audit_service.core.enums.ETypeEssence;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditCUDTO {

    private UserActingDTO user;
    private String text;
    private ETypeEssence type;
    private String id;


    public boolean fieldsChanged() {
        return Objects.nonNull(user) &&
                Objects.nonNull(text) &&
                Objects.nonNull(type) &&
                Objects.nonNull(id)&&(!id.isBlank());
    }

    public boolean correctConstants(ETypeEssence type) {

        return ETypeEssence.getByName(type.name()).isPresent();
    }
}
