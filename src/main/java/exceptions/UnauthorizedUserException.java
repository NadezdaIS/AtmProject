package exceptions;

public class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException() {
        super("Please validate your card!");
        System.out.println("an error occured!!!!");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
