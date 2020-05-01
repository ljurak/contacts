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

    public boolean addContact(Contact contact) {
        return contacts.add(contact);
    }

    public void removeContact(int index) {
        contacts.remove(index);
    }

    public int countContacts() {
        return contacts.size();
    }
}
