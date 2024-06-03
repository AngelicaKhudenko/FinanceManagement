package by.it_academy.jd2.user_service.core.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {

    private String code;
    private String mail;

    public boolean fieldsChanged() {
        return Objects.nonNull(code) &&
                Objects.nonNull(mail);
    }
}
