package ma.emsi.ebankingbackend.Exceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String message ) {
        super(message);
    }
}
