package pl.klemp.ian.myrecipes.error;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping({CustomErrorController.ERROR_PATH})
public class CustomErrorController extends AbstractErrorController {

    static final String ERROR_PATH = "/error";
    private static final ErrorAttributeOptions options = ErrorAttributeOptions.of(
            ErrorAttributeOptions.Include.BINDING_ERRORS,
            ErrorAttributeOptions.Include.MESSAGE
    );

    public CustomErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = this.getErrorAttributes(request, options);
        HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
