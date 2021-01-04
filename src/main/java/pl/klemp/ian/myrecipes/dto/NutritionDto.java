package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutritionDto {

    private int calories;

    public NutritionDto() {
    }

    public NutritionDto(int calories) {
        this.calories = calories;
    }
}
