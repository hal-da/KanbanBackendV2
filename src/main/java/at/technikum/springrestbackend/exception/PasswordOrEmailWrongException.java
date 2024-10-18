package at.technikum.springrestbackend.exception;

public class PasswordOrEmailWrongException extends RuntimeException {
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
