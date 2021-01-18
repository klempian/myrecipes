package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findById(UUID id);

    List<Recipe> findAllByKeywordsContaining(Keyword keyword);

    List<Recipe> findAllByRecipeCategoryId(UUID id);
}
