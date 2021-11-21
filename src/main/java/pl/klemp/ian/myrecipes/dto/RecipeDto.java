package pl.klemp.ian.myrecipes.dto;

import lombok.*;
import pl.klemp.ian.myrecipes.model.Author;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeDto {

    @NotNull(message = "property.required")
    private String name;

    private List<String> image;

    private Author author;

    private LocalDate datePublished;

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
