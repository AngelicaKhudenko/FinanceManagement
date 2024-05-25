package by.it_academy.jd2.finance_management.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageUserDTO {
    private Integer number;
    private Integer size;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_elements")
    private Long totalElements;
    private boolean first;
    @JsonProperty("number_of_elements")
    private Integer numberOfElements;
    private boolean last;
    private List<UserDTO> content;
}
