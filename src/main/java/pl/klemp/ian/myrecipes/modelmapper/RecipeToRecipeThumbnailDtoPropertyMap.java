package pl.klemp.ian.myrecipes.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import pl.klemp.ian.myrecipes.dto.RecipeThumbnailDto;
import pl.klemp.ian.myrecipes.model.Recipe;

import java.util.List;

public class RecipeToRecipeThumbnailDtoPropertyMap extends PropertyMap<Recipe, RecipeThumbnailDto> {
    @Override
    protected void configure() {
        using(convertImages).map(source.getImage()).setImage(null);
    }

    Converter<List<String>, String> convertImages = mappingContext -> {
        String image = mappingContext.getSource().get(0);
        return image.isEmpty() ? null : image;
    };
}
