package pl.klemp.ian.myrecipes.modelmapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.klemp.ian.myrecipes.service.AuthorService;
import pl.klemp.ian.myrecipes.service.CategoryService;
import pl.klemp.ian.myrecipes.service.KeywordService;

@RequiredArgsConstructor
@Configuration
public class ModelMapperConfig {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final KeywordService keywordService;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new RecipeToRecipeDtoPropertyMap());
        modelMapper.addMappings(new RecipeDtoToRecipePropertyMap(categoryService, authorService, keywordService));
        modelMapper.addMappings(new RecipeUpdateDtoToRecipePropertyMap(categoryService, keywordService));
        modelMapper.addMappings(new RecipeToRecipeThumbnailDtoPropertyMap());

        return modelMapper;
    }
}
