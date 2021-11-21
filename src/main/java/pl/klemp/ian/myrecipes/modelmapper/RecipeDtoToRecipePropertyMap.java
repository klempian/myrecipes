package pl.klemp.ian.myrecipes.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
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

public class RecipeDtoToRecipePropertyMap extends PropertyMap<RecipeDto, Recipe> {

    private CategoryService categoryService;
    private AuthorService authorService;
    private KeywordService keywordService;

    public RecipeDtoToRecipePropertyMap(CategoryService categoryService, AuthorService authorService, KeywordService keywordService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.keywordService = keywordService;
    }

    @Override
    protected void configure() {
        map().setCalories(source.getNutrition().getCalories());
        using(convertRecipeInstruction).map(source.getRecipeInstructions()).setRecipeInstructions(null);
        using(convertRecipeCategory).map(source.getRecipeCategory()).setRecipeCategory(null);
        using(convertAuthor).map(source.getAuthor()).setAuthor(null);
        using(convertKeywords).map(source.getKeywords()).setKeywords(null);
    }

    Converter<List<Map<Integer, String>>, List<String>> convertRecipeInstruction = mappingContext ->
            mappingContext.getSource().stream()
            .flatMap(map -> map.values().stream())
            .collect(Collectors.toList());

    Converter<String, Category> convertRecipeCategory = mappingContext -> {
        String categoryName = mappingContext.getSource();

        return categoryService.findByNameOrCreate(categoryName);
    };

    Converter<Author, Author> convertAuthor = mappingContext -> {
        Author author = mappingContext.getSource();
        String authorName = author.getName();
        String authorUrl = author.getUrl();

        return authorService.findByUrlAndNameOrCreate(authorUrl, authorName);
    };

    Converter<List<String>, List<Keyword>> convertKeywords = mappingContext -> {
        List<Keyword> keywordList = new ArrayList<>();
        mappingContext.getSource()
                .forEach(keyword -> keywordList.add(
                        keywordService.findByNameOrCreate(keyword)
                ));

      return keywordList;
    };
}
