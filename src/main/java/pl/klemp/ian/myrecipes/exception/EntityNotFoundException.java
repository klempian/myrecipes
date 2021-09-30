package pl.klemp.ian.myrecipes.exception;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "entity.not_found")
public class EntityNotFoundException extends RuntimeException{
    @ExposeAsArg(value = 0, name = "entity name")
    private final String entityName;
    @ExposeAsArg(value = 1, name = "parameter name")
    private final String parameterName;
    @ExposeAsArg(value = 2, name = "parameter value")
    private final String parameterValue;

    public EntityNotFoundException(Class<?> clazz, String parameterName, String parameterValue) {
        this.entityName = clazz.getSimpleName();
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }
}