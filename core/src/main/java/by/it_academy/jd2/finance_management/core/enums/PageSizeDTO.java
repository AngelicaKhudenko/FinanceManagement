package by.it_academy.jd2.finance_management.core.enums;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PageSizeDTO {

    private Integer page;
    private Integer size;
    public static PageSizeDTO of(Integer page, Integer size){
        return new PageSizeDTO(page, size);
    }

    public static PageSizeDTO defaultParam(){
        return new PageSizeDTO(0, 20);
    }
}
