package pl.klemp.ian.myrecipes.exception;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "url.unable_to_retrieve")
public class UnableToRetrieveUrlException extends RuntimeException{
    @ExposeAsArg(value = 0, name = "url")
    private final String url;

    public UnableToRetrieveUrlException(String url) {
        this.url = url;
    }
}