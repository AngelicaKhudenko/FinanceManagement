package by.it_academy.jd2.finance_management.core.dto;

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
