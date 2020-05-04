package contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

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

    public List<Contact> searchContacts(String query) {
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        List<Contact> results = new ArrayList<>();

        for (Contact contact : contacts) {
            if (pattern.matcher(contact.getStringRepresentation()).find()) {
                results.add(contact);
            }
        }

        return results;
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public int countContacts() {
        return contacts.size();
    }
}
