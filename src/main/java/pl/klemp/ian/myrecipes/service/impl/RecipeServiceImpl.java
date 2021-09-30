package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.exception.EntityNotFoundException;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.repository.RecipeRepository;
import pl.klemp.ian.myrecipes.service.RecipeService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public UUID save(Recipe recipe) {
        return recipeRepository.save(recipe).getUuid();
    }

    @Override
    public Recipe findByUuid(UUID uuid) {
        return recipeRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(Recipe.class, "id", uuid.toString()));
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findAllByKeyword(Keyword keyword) {
        return recipeRepository.findAllByKeywordsContaining(keyword);
    }

    @Override
    public List<Recipe> findAllByRecipeCategory(Category category) {
        return recipeRepository.findAllByRecipeCategory(category);
    }

    @Override
    public void delete(UUID uuid) {
        recipeRepository.delete(findByUuid(uuid));
    }
}
