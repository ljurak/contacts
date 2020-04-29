package contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactRepository {

    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("The record added.");
    }

    public void removeContact(int index) {
        contacts.remove(index);
        System.out.println("The record removed!");
    }

    public int countContacts() {
        return contacts.size();
    }
}
