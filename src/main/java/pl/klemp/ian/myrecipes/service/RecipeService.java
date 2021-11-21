package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    UUID save(Recipe recipe);

    Recipe findByUuid(UUID uuid);

    List<Recipe> findAll();

    List<Recipe> findAllByKeyword(Keyword keyword);

    List<Recipe> findAllByRecipeCategory(Category category);

    void delete(UUID uuid);

    RecipeDto getRecipeFromUrl(String url);
}
