package exceptions;

public class WithdrawalFailedException extends Exception {
    public WithdrawalFailedException(String message) {
        super(message);
    }
}
