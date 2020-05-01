package contacts;

import java.time.LocalDateTime;

public abstract class Contact {

    protected String phoneNumber;

    protected final boolean isPerson;

    protected LocalDateTime creationDate;

    protected LocalDateTime updateDate;

    protected Contact(boolean isPerson) {
        this.isPerson = isPerson;
    }

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
}
