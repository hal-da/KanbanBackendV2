package at.technikum.springrestbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotEnoughPrivileges extends RuntimeException{
    public UserNotEnoughPrivileges() {
    }

    public UserNotEnoughPrivileges(String message) {
        super(message);
    }
}
