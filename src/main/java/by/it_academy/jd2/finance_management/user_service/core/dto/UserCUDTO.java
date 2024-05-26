package by.it_academy.jd2.finance_management.user_service.core.dto;

import by.it_academy.jd2.finance_management.user_service.core.enums.EUserRole;
import by.it_academy.jd2.finance_management.user_service.core.enums.EUserStatus;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCUDTO {
    private String mail;
    private String fio;
    private EUserRole role;
    private EUserStatus status;
    private String password;

    public boolean fieldsChanged() {
        return Objects.nonNull(mail) &&
                Objects.nonNull(fio) &&
                Objects.nonNull(role) &&
                Objects.nonNull(status) &&
                Objects.nonNull(password);
    }

    public boolean correctConstants(EUserRole role, EUserStatus status) {

        boolean roleIsCorrect = EUserRole.getByName(role.name()).isPresent();
        boolean statusIsCorrect = EUserStatus.getByName(status.name()).isPresent();

        return roleIsCorrect && statusIsCorrect;
    }
}
