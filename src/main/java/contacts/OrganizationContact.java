package contacts;

import java.time.LocalDateTime;
import java.util.List;

public class OrganizationContact extends Contact {

    private static final long serialVersionUID = 1L;

    private String name;

    private String address;

    private OrganizationContact() {
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

    @Override
    public List<String> getEditableFields() {
        return List.of("name", "address", "number");
    }

    @Override
    public String getInfo() {
        return "Organization name: " + name + System.lineSeparator() +
                "Address: " + address + System.lineSeparator() +
                "Number: " + (hasNumber() ? phoneNumber : "[no data]") + System.lineSeparator() +
                "Time created: " + creationDate + System.lineSeparator() +
                "Time last edit: " + updateDate;
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name).append(" ");
        }
        if (address != null) {
            sb.append(address).append(" ");
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
                setName(value);
                break;
            case "address":
                setAddress(value);
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
