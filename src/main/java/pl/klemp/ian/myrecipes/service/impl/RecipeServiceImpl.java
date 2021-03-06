package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.exception.EntityNotFoundException;
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
        return recipeRepository.save(recipe).getId();
    }

    @Override
    public Recipe findById(UUID id) {
        return recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Recipe.class, id));
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
    public List<Recipe> findAllByRecipeCategoryId(UUID id) {
        return recipeRepository.findAllByRecipeCategoryId(id);
    }

    @Override
    public void delete(UUID id) {
        recipeRepository.delete(findById(id));
    }
}
