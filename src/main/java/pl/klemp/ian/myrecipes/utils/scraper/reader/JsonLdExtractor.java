package pl.klemp.ian.myrecipes.utils.scraper.reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static pl.klemp.ian.myrecipes.utils.scraper.reader.JsonLdRecipeParser.getJsonObjectOfType;

/**
 * Extracts json-ld and returns first object of searched type
 */
public class JsonLdExtractor {

    public static JsonObject getJsonLdObject(Document document, String typeName) {
        Elements elements = getJsonLdElements(document);

        for (Element element : elements) {
            JsonElement jsonElement = JsonParser.parseString(element.html());
            JsonObject jsonObject = getJsonObjectOfType(jsonElement, typeName);

            if (jsonObject != null) {
                return getJsonObjectOfType(jsonElement, typeName);
            }
        }

        return null;
    }

    private static Elements getJsonLdElements(Document document) {
        return document.select("script[type$=application/ld+json]");
    }
}
