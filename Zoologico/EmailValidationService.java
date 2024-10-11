public class EmailValidationService {
    private final ValidadorEmailAdapter validadorEmail;

    public EmailValidationService(ValidadorEmailAdapter validadorEmail) {
        this.validadorEmail = validadorEmail;
    }

    public boolean validateEmail(String email) {
        return validadorEmail.isValid(email);
    }
}
