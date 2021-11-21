package pl.klemp.ian.myrecipes.utils.scraper.reader;

import org.jsoup.nodes.Document;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.exception.UnableToRetrieveUrlException;
import pl.klemp.ian.myrecipes.model.Author;

import java.time.LocalDate;
import java.util.List;

public abstract class PageReader {

    protected Document document;

    public PageReader(Document document) {
        this.document = document;
    }

    public RecipeDto getRecipe() throws UnableToRetrieveUrlException {

        return RecipeDto.builder()
                .name(getName())
                .image(getImages())
                .author(getAuthor())
                .datePublished(getDatePublished())
                .description(getDescription())
                .recipeCuisine(getRecipeCuisine())
                .prepTime(getPrepTime())
                .cookTime(getCookTime())
                .totalTime(getTotalTime())
                .keywords(getKeywords())
                .recipeYield(getRecipeYield())
            .build();
    }

    protected abstract String getName();
    protected abstract List<String> getImages();
    protected abstract Author getAuthor();
    protected abstract LocalDate getDatePublished();
    protected abstract String getDescription();
    protected abstract String getRecipeCuisine();
    protected abstract Integer getPrepTime();
    protected abstract Integer getCookTime();
    protected abstract Integer getTotalTime();
    protected abstract List<String> getKeywords();
    protected abstract String getRecipeYield();
}
