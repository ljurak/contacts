package contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public void saveContact(Contact contact) {
        contacts.add(contact);
        System.out.println("A record created");
        System.out.println("A Phone Book with a single record created!");
    }
}
