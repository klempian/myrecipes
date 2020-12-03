package pl.klemp.ian.myrecipes.model;

import lombok.Data;
import pl.klemp.ian.myrecipes.converter.StringListConverter;

import javax.persistence.Column;
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

    @NotNull
    private String name;

    @Column(name = "images")
    @Convert(converter = StringListConverter.class)
    private List<String> image;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
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

    @Column(name = "recipe_ingredients")
    @Convert(converter = StringListConverter.class)
    private List<String> recipeIngredient;

    @Convert(converter = StringListConverter.class)
    private List<String> recipeInstructions;

}
