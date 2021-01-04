package pl.klemp.ian.myrecipes.modelmapper;

import lombok.RequiredArgsConstructor;
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
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new RecipePropertyMap());
        modelMapper.addMappings(new RecipeDtoPropertyMap(categoryService, authorService, keywordService));

        return modelMapper;
    }
}
