package pl.klemp.ian.myrecipes.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/add", consumes = "application/json")
    public Recipe create(@RequestBody @Valid RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        return recipeService.save(recipe);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{recipeId}")
    public RecipeDto getById(@PathVariable Long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        return modelMapper.map(recipe, RecipeDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.findAll();
    }
}
