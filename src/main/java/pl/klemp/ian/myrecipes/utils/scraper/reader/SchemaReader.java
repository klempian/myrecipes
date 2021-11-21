package pl.klemp.ian.myrecipes.utils.scraper.reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import pl.klemp.ian.myrecipes.model.Author;

import javax.xml.bind.DatatypeConverter;
import java.time.LocalDate;
import java.util.*;

import static pl.klemp.ian.myrecipes.utils.scraper.reader.JsonLdExtractor.getJsonLdObject;
import static pl.klemp.ian.myrecipes.utils.scraper.reader.JsonLdRecipeParser.*;

/**
 * Reads a <a href="https://schema.org">Schema.org</a> json-ld recipe
 */

public class SchemaReader extends PageReader{

    public SchemaReader(Document document) {
        super(document);
    }

    protected JsonObject jsonLdRecipe = getJsonLdObject(super.document, "Recipe");

    @Override
    protected String getName() {

        return getValue(jsonLdRecipe, "name");
    }

    @Override
    protected List<String> getImages() {
        JsonElement jsonImage = jsonLdRecipe.get("image");

        return getListOfValues(jsonImage, "url");
    }

    @Override
    protected Author getAuthor() {
        Author author = new Author();
        JsonElement jsonAuthor = jsonLdRecipe.get("author");
        JsonObject jsonObject = getJsonObjectOfOneOfTypes(jsonAuthor, Arrays.asList("Person", "Organization"));
        author.setName(getValue(jsonObject, "name"));
        author.setUrl(getValue(jsonObject, "url"));

        return author;
    }

    @Override
    protected LocalDate getDatePublished() {

        Calendar calendar = DatatypeConverter.parseDateTime(getValue(jsonLdRecipe, "datePublished"));
        Date date = calendar.getTime();

        return date.toInstant()
                .atZone(calendar.getTimeZone().toZoneId())
                .toLocalDate();
    }

    @Override
    protected String getDescription() {
        return getValue(jsonLdRecipe, "description");
    }

    @Override
    protected String getRecipeCuisine() {
        return getValue(jsonLdRecipe, "recipeCuisine");
    }

    @Override
    protected Integer getPrepTime() {
        return convertToMinutes(getValue(jsonLdRecipe, "prepTime"));
    }

    @Override
    protected Integer getCookTime() {
        return convertToMinutes(getValue(jsonLdRecipe, "cookTime"));
    }

    @Override
    protected Integer getTotalTime() {
        return convertToMinutes(getValue(jsonLdRecipe, "totalTime"));
    }

    @Override
    protected List<String> getKeywords() {
        try {
            return Arrays.asList(getValue(jsonLdRecipe, "keywords")
                    .split("(, ?)"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    protected String getRecipeYield() {
        return getValue(jsonLdRecipe, "recipeYield");
    }
}
