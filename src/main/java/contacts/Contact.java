package contacts;

import java.util.regex.Pattern;

public class Contact {

    private static final Pattern phonePattern1 = Pattern.compile("^(\\+?[0-9a-z]+[ -])?(([0-9a-z]{2,3})|(\\([0-9a-z]{2,3}\\)))([ -][0-9a-z]{2,})*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern phonePattern2 = Pattern.compile("^\\+?(([0-9a-z]+)|(\\([0-9a-z]+\\)))$", Pattern.CASE_INSENSITIVE);

    private String firstName;

    private String lastName;

    private String phoneNumber;

    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (validatePhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
        }
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (validatePhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = null;
            System.out.println("Wrong number format!");
        }
    }

    public boolean hasNumber() {
        return !(phoneNumber == null || phoneNumber.equals(""));
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return phonePattern1.matcher(phoneNumber).matches() || phonePattern2.matcher(phoneNumber).matches();
    }
}
