package pl.klemp.ian.myrecipes.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@RestController
public class RecipeAPI {

    private final RecipeService recipeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public Recipe create(@RequestBody @Valid Recipe recipe) {
        return recipeService.save(recipe);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{recipeId}")
    public Recipe getById(@PathVariable Long recipeId) {
        return recipeService.findById(recipeId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.findAll();
    }


}
