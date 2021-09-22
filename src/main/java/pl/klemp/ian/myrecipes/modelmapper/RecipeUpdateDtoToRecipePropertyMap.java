package pl.klemp.ian.myrecipes.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.dto.RecipeUpdateDto;
import pl.klemp.ian.myrecipes.model.Author;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.AuthorService;
import pl.klemp.ian.myrecipes.service.CategoryService;
import pl.klemp.ian.myrecipes.service.KeywordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeUpdateDtoToRecipePropertyMap extends PropertyMap<RecipeUpdateDto, Recipe> {

    private CategoryService categoryService;
    private KeywordService keywordService;

    public RecipeUpdateDtoToRecipePropertyMap(CategoryService categoryService, KeywordService keywordService) {
        this.categoryService = categoryService;
        this.keywordService = keywordService;
    }

    @Override
    protected void configure() {
        map().setCalories(source.getNutrition().getCalories());
        using(convertRecipeInstruction).map(source.getRecipeInstructions()).setRecipeInstructions(null);
        using(convertRecipeCategory).map(source.getRecipeCategory()).setRecipeCategory(null);
        using(convertKeywords).map(source.getKeywords()).setKeywords(null);
        using(convertImages).map(source.getImage()).setImage(null);
        using(convertIngredients).map(source.getRecipeIngredient()).setRecipeIngredient(null);
    }

    Converter<List<Map<Integer, String>>, List<String>> convertRecipeInstruction = mappingContext ->
            mappingContext.getSource().stream()
            .flatMap(map -> map.values().stream())
            .collect(Collectors.toList());

    Converter<String, Category> convertRecipeCategory = mappingContext -> {
        String categoryName = mappingContext.getSource();

        return categoryService.findByNameOrCreate(categoryName);
    };

    Converter<List<String>, List<Keyword>> convertKeywords = mappingContext -> {
        List<Keyword> keywordList = new ArrayList<>();
        mappingContext.getSource()
                .forEach(keyword -> keywordList.add(
                        keywordService.findByNameOrCreate(keyword)
                ));

      return keywordList;
    };

    Converter<List<String>, List<String>> convertImages = MappingContext::getSource;

    Converter<List<String>, List<String>> convertIngredients = MappingContext::getSource;
}
