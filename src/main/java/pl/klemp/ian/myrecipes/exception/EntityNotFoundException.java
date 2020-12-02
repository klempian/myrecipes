package pl.klemp.ian.myrecipes.exception;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "entity.not_found")
public class EntityNotFoundException extends RuntimeException{
    @ExposeAsArg(value = 0, name = "entity name")
    private final String entityName;
    @ExposeAsArg(value = 1, name = "entity id")
    private final Long entityId;

    public EntityNotFoundException(Class clazz, Long id) {
        this.entityName = clazz.getSimpleName();
        this.entityId = id;
    }
}