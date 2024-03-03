package at.technikum.springrestbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthenticatedException extends RuntimeException{

    public UserNotAuthenticatedException() {
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
