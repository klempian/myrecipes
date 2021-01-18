package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    UUID save(Recipe recipe);

    Recipe findById(UUID id);

    List<Recipe> findAll();

    List<Recipe> findAllByKeyword(Keyword keyword);

    List<Recipe> findAllByRecipeCategoryId(UUID id);

    void delete(UUID id);
}
