package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe save(Recipe recipe);

    Recipe findById(Long id);

    List<Recipe> findAll();

    List<Recipe> findAllByKeyword(Keyword keyword);

    List<Recipe> findAllByRecipeCategoryId(Long id);

    void delete(Long id);
}
