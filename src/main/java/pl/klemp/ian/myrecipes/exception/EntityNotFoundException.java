package pl.klemp.ian.myrecipes.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        String params = searchParams.entrySet().stream()
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining("& "));

        return StringUtils.capitalize(entity) + " was not found for " + params;
    }

    private static Map<String, String> toMap(String... entries) {
        if (entries.length % 2 == 1) throw new IllegalArgumentException("Invalid entries");

        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(entries[i], entries[i+1]),
                        Map::putAll
        );
    }
}
