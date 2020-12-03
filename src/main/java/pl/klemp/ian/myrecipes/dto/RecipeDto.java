package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.Author;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecipeDto {

//    public RecipeDto() {
//        this.nutrition = new NutritionDto();
//    }

    @NotNull(message = "property.required")
    private String name;

    private List<String> image;

    private Author author;

    private LocalDate datePublished;

    private String description;

    private String recipeCuisine;

    private int prepTime;

    private int cookTime;

    private int totalTime;

    private List<String> keywords;

    private String recipeYield;

    private String recipeCategory;

    private NutritionDto nutrition;

    private List<String> recipeIngredient;

    private List<Map<Integer, String>> recipeInstructions;
}
