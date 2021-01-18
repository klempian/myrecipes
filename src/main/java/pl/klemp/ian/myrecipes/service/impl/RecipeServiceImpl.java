package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.exception.EntityNotFoundException;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.repository.RecipeRepository;
import pl.klemp.ian.myrecipes.service.RecipeService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findById(Long id) {
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
    public List<Recipe> findAllByRecipeCategoryId(Long id) {
        return recipeRepository.findAllByRecipeCategoryId(id);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.delete(findById(id));
    }
}
