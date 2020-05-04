package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersonContact extends Contact {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    private PersonContact() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public String getEditableFields() {
        return "name, surname, birth, gender, number";
    }

    @Override
    public String getInfo() {
        return "Name: " + firstName + System.lineSeparator() +
                "Surname: " + lastName + System.lineSeparator() +
                "Birth date: " + (birthDate != null ? birthDate : "[no data]") + System.lineSeparator() +
                "Gender: " + (gender != null ? gender.getAbbr() : "[no data]") + System.lineSeparator() +
                "Number: " + (hasNumber() ? phoneNumber : "[no data]") + System.lineSeparator() +
                "Time created: " + creationDate + System.lineSeparator() +
                "Time last edit: " + updateDate;
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        if (firstName != null) {
            sb.append(firstName).append(" ");
        }
        if (lastName != null) {
            sb.append(lastName).append(" ");
        }
        if (birthDate != null) {
            sb.append(birthDate).append(" ");
        }
        if (gender != null) {
            sb.append(gender).append(" ");
        }
        if (phoneNumber != null) {
            sb.append(phoneNumber).append(" ");
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void updateField(String field, String value) {
        switch (field) {
            case "name":
                setFirstName(value);
                break;
            case "surname":
                setLastName(value);
                break;
            case "birth":
                setBirthDate(value != null
                        ? LocalDate.parse(value, DateTimeFormatter.ofPattern("d-M-yyyy"))
                        : null);
                break;
            case "gender":
                setGender(Gender.valueByAbbr(value));
                break;
            case "number":
                setPhoneNumber(value);
                break;
            default:
                return;
        }
        setUpdateDate(LocalDateTime.now().withNano(0));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ContactValidator validator = new ContactValidator();

        private PersonContact contact = new PersonContact();

        private Builder() {
        }

        public Builder firstName(String firstName) {
            contact.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            contact.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            if (validator.validatePhoneNumber(phoneNumber)) {
                contact.phoneNumber = phoneNumber;
            }
            return this;
        }

        public Builder birthDate(String birthDate) {
            if (validator.validateBirthDate(birthDate)) {
                contact.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("d-M-yyyy"));
            }
            return this;
        }

        public Builder gender(String gender) {
            contact.gender = Gender.valueByAbbr(gender);
            return this;
        }

        public PersonContact build() {
            contact.creationDate = LocalDateTime.now().withNano(0);
            contact.updateDate = LocalDateTime.now().withNano(0);
            return contact;
        }
    }
}
