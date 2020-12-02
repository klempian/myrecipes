package pl.klemp.ian.myrecipes.model;

import lombok.Data;
import pl.klemp.ian.myrecipes.converter.StringListConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "property.required")
    private String name;

    @Convert(converter = StringListConverter.class)
    private List<String> images;

    @ManyToOne
    private Author author;

    private LocalDate datePublished;

    private String description;

    private String recipeCuisine;

    private int prepTime;

    private int cookTime;

    private int totalTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipes_keywords",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords;

    private String recipeYield;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category recipeCategory;

    private int calories;

    @Convert(converter = StringListConverter.class)
    private List<String> recipeIngredients;

    @Convert(converter = StringListConverter.class)
    private List<String> recipeInstructions;

}
