package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutritionDto {

    private String calories;

    public NutritionDto(String calories) {
        this.calories = calories;
    }
}
