package DemoRest.WebApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Email Address already exists")
public class ForbiddenException extends RuntimeException {
}
