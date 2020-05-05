package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Contact implements Serializable {

    protected String id = UUID.randomUUID().toString();

    protected String phoneNumber;

    protected LocalDateTime creationDate;

    protected LocalDateTime updateDate;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean hasNumber() {
        return !(phoneNumber == null || phoneNumber.equals(""));
    }

    public boolean isEditableField(String field) {
        return getEditableFields().contains(field);
    }

    public abstract List<String> getEditableFields();

    public abstract void updateField(String field, String value);

    public abstract String getInfo();

    public abstract String getStringRepresentation();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return id.equals(contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
