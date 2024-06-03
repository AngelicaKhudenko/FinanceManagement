package by.it_academy.jd2.user_service.core.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

    private String from;
    private String topic;
    private String to;
    private String text;

    public boolean fieldsChanged() {
        return Objects.nonNull(from) &&
                Objects.nonNull(topic) &&
                Objects.nonNull(to)&&
                Objects.nonNull(text);
    }
}
