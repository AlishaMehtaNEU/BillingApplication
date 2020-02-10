package DemoRest.WebApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Account Created field cannot be inserted")
public class AccCreateFieldNotRequiredException extends RuntimeException{
}
