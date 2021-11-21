package pl.klemp.ian.myrecipes.utils.scraper.reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonLdRecipeParser {

     /**
     * If found, returns a {@link JsonObject} of searched type from the provided {@link JsonElement}.
     * @param jsonElement - {@link JsonElement} to be checked if contains a {@link JsonObject} of searched type
     * @param typeName - name of searched type
     * @return - {@link JsonObject} of searched type
     */
    public static JsonObject getJsonObjectOfType(JsonElement jsonElement, String typeName) {

        JsonObject jsonObject = null;

        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            for (JsonElement e : array) {
                jsonObject = e.getAsJsonObject();
                if (Objects.equals(jsonObject.get("@type").getAsString(), typeName)) {
                    return jsonObject;
                }
            }
        } else {
            jsonObject = jsonElement.getAsJsonObject();
            if (Objects.equals(jsonObject.get("@type").getAsString(), typeName)) {
                return jsonObject;
            }
        }

        return jsonObject;
    }

    /**
     * If found, returns the first {@link JsonObject} of searched types (respecting the order in list)
     * @param jsonElement - {@link JsonElement} to be checked if contains a {@link JsonObject} of searched type
     * @param typeNames - list of names of searched type
     * @return - {@link JsonObject} of searched type
     */
    public static JsonObject getJsonObjectOfOneOfTypes(JsonElement jsonElement, List<String> typeNames) {
        JsonObject jsonObject = null;

        for (String name : typeNames) {
            jsonObject = getJsonObjectOfType(jsonElement, name);
            if (jsonObject != null) {
                break;
            }
        }

        return jsonObject;
    }

    /**
     * Returns a list of values from a recipe's parameter, where the latter could have been implemented as a string,
     * a json object with its own parameters, or a list of strings or json objects.
     * @param jsonElement - value of a recipe's parameter as a {@link JsonElement}
     * @param name - name of the element's parameter
     * @return - list of values
     */
    public static List<String> getListOfValues(JsonElement jsonElement, String name) {
        List<String> listOfValues = new ArrayList<>();

        if(jsonElement.isJsonArray()) {
            for (JsonElement o : jsonElement.getAsJsonArray()) {
                listOfValues.add(getValue(o, name));
            }
        } else {
            listOfValues.add(getValue(jsonElement, name));
        }

        return listOfValues;
    }

    /**
     * Returns the value from an element where the element might be a string or an object with its own parameters
     * @param jsonElement - value of a recipe's parameter as a {@link JsonElement}
     * @param name - name of the object's parameter
     * @return - value as a string
     */
    public static String getValue(JsonElement jsonElement, String name) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            return object.has(name) ? object.get(name).getAsString() : object.getAsString();
        } catch (UnsupportedOperationException | IllegalStateException e) {
            return null;
        }
    }

    /**
     * Returns the amount of minutes parsed from ISO8601 Duration format
     * @param periodDuration - String in ISO8601 format
     * @return - amount of minutes
     */
    public static Integer convertToMinutes(String periodDuration){
        if (periodDuration == null) {return null;}

        String periodPart = periodDuration.substring(periodDuration.indexOf("P"), periodDuration.indexOf("T"));
        String durationPart = periodDuration.substring(periodDuration.indexOf("T"));

        int minutes = periodPart.length() > 1 ? Period.parse(periodPart).getDays() * 24*60 : 0;

        minutes += Duration.parse("P"+ durationPart).getSeconds() / 60;

        return minutes;
    }
}
