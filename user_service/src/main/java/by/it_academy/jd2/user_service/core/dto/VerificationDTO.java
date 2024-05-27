package by.it_academy.jd2.user_service.core.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDTO {

    private String code;
    private String mail;
}
