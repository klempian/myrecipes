package pl.klemp.ian.myrecipes.error;

import me.alidg.errors.Argument;
import me.alidg.errors.HttpError;
import me.alidg.errors.adapter.HttpErrorAttributesAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomHttpErrorAttributesAdapter implements HttpErrorAttributesAdapter {

    @Value("${recipes.api.version}")
    private String currentApiVersion;

    private final MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    public CustomHttpErrorAttributesAdapter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Map<String, Object> adapt(HttpError httpError) {
        List<Map<String, Object>> collect = httpError.getErrors().stream()
                .map(this::toMap)
                .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);

        Map<String, Object> errorBlock = new LinkedHashMap<>();
        errorBlock.put("code", httpError.getHttpStatus().value());
        errorBlock.put("errors", collect);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("version", currentApiVersion);
        result.put("timestamp", formatDateTime);
        result.put("error", errorBlock);

        return result;
    }

    private Map<String, Object> toMap(HttpError.CodedMessage codedMessage) {

        Map<String, Object> result = new HashMap<>();
        result.put("code",  codedMessage.getCode());
        result.put("message", codedMessage.getMessage());

//        one general message for each type mismatch instead of separate messages for each single property
        if (codedMessage.getCode().startsWith("binding.type_mismatch")) {
            String message = messageSource.getMessage("binding.type_mismatch", null, locale);
            result.put("message", message);
        }

        List<Argument> arguments = codedMessage.getArguments();
        for (Argument argument : arguments) {
            result.put(argument.getName(), argument.getValue());
        }

        return result;
    }
}
