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
        super(true);
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
