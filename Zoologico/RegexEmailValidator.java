import java.util.regex.Pattern;

public class RegexEmailValidator implements ValidadorEmailAdapter {
    // Expressão regular para validar emails
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
}
