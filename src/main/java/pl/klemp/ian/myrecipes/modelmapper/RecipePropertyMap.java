package pl.klemp.ian.myrecipes.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import pl.klemp.ian.myrecipes.dto.NutritionDto;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RecipePropertyMap extends PropertyMap<Recipe, RecipeDto> {
    @Override
    protected void configure() {
        map().setRecipeCategory(source.getRecipeCategory().getName());
        using(convertCalories).map(source.getCalories()).setNutrition(null);
        using(convertRecipeInstruction).map(source.getRecipeInstructions()).setRecipeInstructions(null);
    }

    Converter<Integer, NutritionDto> convertCalories = mappingContext ->
            new NutritionDto(mappingContext.getSource());

    Converter<List<String>, List<Map<Integer, String>>> convertRecipeInstruction = mappingContext -> {
        AtomicInteger atomicInteger = new AtomicInteger();
        return mappingContext.getSource().stream()
                .map(s -> Collections.singletonMap(atomicInteger.incrementAndGet(), s))
                .collect(Collectors.toList());
    };
}
