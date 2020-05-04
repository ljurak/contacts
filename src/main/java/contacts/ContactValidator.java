package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ContactValidator {

    private static final Pattern phonePattern1 = Pattern.compile("^(\\+?[0-9a-z]+[ -])?(([0-9a-z]{2,3})|(\\([0-9a-z]{2,3}\\)))([ -][0-9a-z]{2,})*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern phonePattern2 = Pattern.compile("^\\+?(([0-9a-z]+)|(\\([0-9a-z]+\\)))$", Pattern.CASE_INSENSITIVE);

    public boolean validatePhoneNumber(String phoneNumber) {
        return phonePattern1.matcher(phoneNumber).matches() || phonePattern2.matcher(phoneNumber).matches();
    }

    public boolean validateBirthDate(String birthDate) {
        try {
            LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("d-M-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validateGender(String gender) {
        return "M".equalsIgnoreCase(gender) || "F".equalsIgnoreCase(gender);
    }

    public boolean validateField(String field, String value) {
        switch (field) {
            case "birth":
                return validateBirthDate(value);
            case "gender":
                return validateGender(value);
            case "number":
                return validatePhoneNumber(value);
            default:
                return true; // for other fields when no validation required
        }
    }

    public String getValidationMessage(String field) {
        switch (field) {
            case "birth":
                return "Bad birth date!";
            case "gender":
                return "Bad gender!";
            case "number":
                return "Bad phone number!";
            default:
                return null;
        }
    }
}
