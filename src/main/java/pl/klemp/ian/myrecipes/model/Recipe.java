package pl.klemp.ian.myrecipes.model;

import lombok.*;
import pl.klemp.ian.myrecipes.converter.StringListConverter;
import pl.klemp.ian.myrecipes.model.persistent.AbstractPersistentObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe extends AbstractPersistentObject {

    @NotNull
    private String name;

    @NotNull
    @Column(name = "images")
    @Convert(converter = StringListConverter.class)
    private List<String> image;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    private LocalDate datePublished;

    private String description;

    private String recipeCuisine;

    private Integer prepTime;

    private Integer cookTime;

    private Integer totalTime;

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
