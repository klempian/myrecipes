package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecipeUpdateDto {

    @NotNull(message = "property.required")
    private String name;

    private List<String> image;

    private String description;

    private String recipeCuisine;

    private Integer prepTime;

    private Integer cookTime;

    private Integer totalTime;

    private List<String> keywords;

    private String recipeYield;

    private String recipeCategory;

    private NutritionDto nutrition;

    private List<String> recipeIngredient;

    private List<Map<Integer, String>> recipeInstructions;
}
