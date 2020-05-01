package contacts;

import java.time.LocalDateTime;

public class OrganizationContact extends Contact {

    private String name;

    private String address;

    private OrganizationContact() {
        super(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ContactValidator validator = new ContactValidator();

        private OrganizationContact contact = new OrganizationContact();

        private Builder() {
        }

        public Builder name(String name) {
            contact.name = name;
            return this;
        }

        public Builder address(String address) {
            contact.address = address;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            if (validator.validatePhoneNumber(phoneNumber)) {
                contact.phoneNumber = phoneNumber;
            }
            return this;
        }

        public OrganizationContact build() {
            contact.creationDate = LocalDateTime.now().withNano(0);
            contact.updateDate = LocalDateTime.now().withNano(0);
            return contact;
        }
    }
}
