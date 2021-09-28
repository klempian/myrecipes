package pl.klemp.ian.myrecipes.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.klemp.ian.myrecipes.converter.StringListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(this.getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
