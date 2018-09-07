package ru.alta.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDTO {
    @NotNull
    private String name;
    @Min(0)
    private Long salaryLevel;
    private String experience;
    private String city;
}
