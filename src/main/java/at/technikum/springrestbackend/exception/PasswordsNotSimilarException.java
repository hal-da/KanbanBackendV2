package at.technikum.springrestbackend.exception;

public class PasswordsNotSimilarException extends RuntimeException {
    public PasswordsNotSimilarException(String message) {
        super(message);
    }
}
