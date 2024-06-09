package by.it_academy.jd2.user_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {

    @NotEmpty
    @NotNull
    private String code;
    @NotEmpty
    @NotNull
    private String mail;

}
