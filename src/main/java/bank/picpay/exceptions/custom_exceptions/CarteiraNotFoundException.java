package bank.picpay.exceptions.custom_exceptions;

public class CarteiraNotFoundException extends RuntimeException {
    public CarteiraNotFoundException(String message) {
        super(message);
    }
}
