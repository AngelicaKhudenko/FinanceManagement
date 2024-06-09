package by.it_academy.jd2.user_service.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

    @NotEmpty
    @NotNull
    private String from;
    @NotEmpty
    @NotNull
    private String topic;
    @NotEmpty
    @NotNull
    private String to;
    @NotEmpty
    @NotNull
    private String text;
}
