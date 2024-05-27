package by.it_academy.jd2.user_service.core.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    private String mail;
    private String password;

    public boolean fieldsChanged() {
        return Objects.nonNull(mail) &&
                Objects.nonNull(password);
    }
}
