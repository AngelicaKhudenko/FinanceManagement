package by.it_academy.jd2.finance_management.user_service.core.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String mail;
    private String fio;
    private String password;

    public boolean fieldsChanged() {
        return Objects.nonNull(mail) &&
                Objects.nonNull(fio) &&
                Objects.nonNull(password);
    }
}
