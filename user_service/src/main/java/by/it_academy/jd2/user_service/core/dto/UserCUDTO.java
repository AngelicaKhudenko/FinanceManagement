package by.it_academy.jd2.user_service.core.dto;

import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserCUDTO {
    @NotEmpty
    @NotNull
    private String mail;
    @NotEmpty
    @NotNull
    private String fio;
    @NotNull
    private EUserRole role;
    @NotNull
    private EUserStatus status;
    @NotEmpty
    @NotNull
    private String password;

    public boolean correctConstants(EUserRole role, EUserStatus status) {

        boolean roleIsCorrect = EUserRole.getByName(role.name()).isPresent();
        boolean statusIsCorrect = EUserStatus.getByName(status.name()).isPresent();

        return roleIsCorrect && statusIsCorrect;
    }
}
