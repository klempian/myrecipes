package pl.klemp.ian.myrecipes.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.dto.RecipeThumbnailDto;
import pl.klemp.ian.myrecipes.dto.RecipeUpdateDto;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.KeywordService;
import pl.klemp.ian.myrecipes.service.RecipeService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final KeywordService keywordService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<Object> create(@RequestBody @Valid RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        UUID recipeId = recipeService.save(recipe);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{recipeId}").buildAndExpand(recipeId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{recipeId}", consumes = "application/json")
    public ResponseEntity<Object> update(@PathVariable UUID recipeId, @RequestBody @Valid RecipeUpdateDto recipeUpdateDto) {
        Recipe recipe = recipeService.findById(recipeId);
        modelMapper.map(recipeUpdateDto, recipe);
        recipeService.save(recipe);

        return ResponseEntity.accepted().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{recipeId}")
    public RecipeDto getById(@PathVariable UUID recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        return modelMapper.map(recipe, RecipeDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category")
    public List<RecipeThumbnailDto> getByCategory(@RequestParam UUID id) {

        return recipeService.findAllByRecipeCategoryId(id).stream()
                .map(recipe -> modelMapper.map(recipe, RecipeThumbnailDto.class))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/keyword")
    public List<RecipeThumbnailDto> getByKeyword(@RequestParam String key) {
        Optional<Keyword> optionalKeyword = keywordService.findByName(key);

        return optionalKeyword.map(keyword -> recipeService.findAllByKeyword(keyword).stream()
                .map(recipe -> modelMapper.map(recipe, RecipeThumbnailDto.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<RecipeThumbnailDto> getAllRecipes() {

        return recipeService.findAll().stream()
                .map(recipe -> modelMapper.map(recipe, RecipeThumbnailDto.class))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteRecipeById(@PathVariable UUID id) {
        recipeService.delete(id);
    }
}
