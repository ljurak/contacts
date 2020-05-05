package contacts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String filename = null;
        if (args.length > 0) {
            filename = args[0];
        }

        ContactValidator contactValidator = new ContactValidator();
        ContactRepository contactRepo = new ContactRepository();

        loadContacts(contactRepo, filename);

        while (true) {
            String action = getUserInput("[menu] Enter action (add, list, search, count, exit): ");

            switch (action) {
                case "add":
                    processAddAction(contactValidator, contactRepo);
                    break;
                case "list":
                    processListAction(contactValidator, contactRepo);
                    break;
                case "search":
                    processSearchAction(contactValidator, contactRepo);
                    break;
                case "count":
                    processCountAction(contactRepo);
                    break;
                case "exit":
                    processExitAction(contactRepo, filename);
                    break;
                default:
                    System.out.println("Incorrect option! Try again." + System.lineSeparator());
                    break;
            }
        }
    }

    /* ADDING CONTACT */

    private static void processAddAction(ContactValidator contactValidator, ContactRepository contactRepo) {
        String type = getUserInput("Enter the type (person, organization): ");

        switch (type) {
            case "person":
                addPerson(contactValidator, contactRepo);
                break;
            case "organization":
                addOrganization(contactValidator, contactRepo);
                break;
            default:
                System.out.println("Incorrect option! Try again." + System.lineSeparator());
                break;
        }

        String next = getUserInput("[add] Enter action (back, again): ");

        switch (next) {
            case "back":
                System.out.println();
                break;
            case "again":
                processAddAction(contactValidator, contactRepo);
                break;
            default:
                System.out.println("Incorrect option! Try again." + System.lineSeparator());
                break;
        }
    }

    private static void addPerson(ContactValidator contactValidator, ContactRepository contactRepo) {
        String firstName = getUserInput("Enter the name: ");

        String lastName = getUserInput("Enter the surname: ");

        String birthDate = getUserInput("Enter the birth date: ");
        if (!contactValidator.validateBirthDate(birthDate)) {
            System.out.println("Bad birth date!");
        }

        String gender = getUserInput("Enter the gender (M, F): ");
        if (!contactValidator.validateGender(gender)) {
            System.out.println("Bad gender!");
        }

        String phoneNumber = getUserInput("Enter the number: ");
        if (!contactValidator.validatePhoneNumber(phoneNumber)) {
            System.out.println("Bad number format!");
        }

        PersonContact contact = PersonContact.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .build();

        if (contactRepo.addContact(contact)) {
            System.out.println("The record added.");
        }
        System.out.println();
    }

    private static void addOrganization(ContactValidator contactValidator, ContactRepository contactRepo) {
        String name = getUserInput("Enter the organization name: ");

        String address = getUserInput("Enter the address: ");

        String phoneNumber = getUserInput("Enter the number: ");
        if (!contactValidator.validatePhoneNumber(phoneNumber)) {
            System.out.println("Bad number format!");
        }

        OrganizationContact contact = OrganizationContact.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        if (contactRepo.addContact(contact)) {
            System.out.println("The record added.");
        }
        System.out.println();
    }

    /* LISTING CONTACTS */

    private static void processListAction(ContactValidator contactValidator, ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to list!" + System.lineSeparator());
            return;
        }

        listContacts(contactRepo.getContacts());
        System.out.println();

        String next = getUserInput("[list] Enter action ([number], back): ");

        if (next.equals("back")) {
            System.out.println();
        } else {
            int record = Integer.parseInt(next);
            Contact contact = contactRepo.getContact(record - 1);

            System.out.println(contact.getInfo());
            System.out.println();

            processContactAction(contact, contactValidator, contactRepo);
        }
    }

    /* SEARCHING CONTACTS */

    private static void processSearchAction(ContactValidator contactValidator, ContactRepository contactRepo) {
        String query = getUserInput("Enter search query: ");
        List<Contact> found = contactRepo.searchContacts(query);

        printSearchResults(found);

        String next = getUserInput("[search] Enter action ([number], back, again): ");

        switch (next) {
            case "back":
                System.out.println();
                break;
            case "again":
                processSearchAction(contactValidator, contactRepo);
                break;
            default:
                int record = Integer.parseInt(next);
                Contact contact = found.get(record - 1);

                System.out.println(contact.getInfo());
                System.out.println();

                processContactAction(contact, contactValidator, contactRepo);

                break;
        }
    }

    /* PROCESSING CONTACT */

    private static void processContactAction(Contact contact, ContactValidator contactValidator, ContactRepository contactRepo) {
        while (true) {
            String action = getUserInput("[record] Enter action (edit, delete, menu): ");

            switch (action) {
                case "edit":
                    processEditAction(contact, contactValidator);
                    break;
                case "delete":
                    processDeleteAction(contact, contactRepo);
                    return;
                case "menu":
                    System.out.println();
                    return;
                default:
                    System.out.println("Incorrect option! Try again." + System.lineSeparator());
                    break;
            }
        }
    }

    /* EDITING CONTACT */

    private static void processEditAction(Contact contact, ContactValidator contactValidator) {
        String field = getUserInput("Select a field (" + printEditableFields(contact.getEditableFields()) + "): ");
        if (contact.isEditableField(field)) {
            String value = getUserInput("Enter the " + field + ": ");

            if (contactValidator.validateField(field, value)) {
                contact.updateField(field, value);
            } else {
                contact.updateField(field, null);
                System.out.println(contactValidator.getValidationMessage(field));
            }
            System.out.println("The record updated!");
            System.out.println(contact.getInfo() + System.lineSeparator());
        } else {
            System.out.println("Incorrect option! Try again." + System.lineSeparator());
        }
    }

    /* DELETING CONTACT */

    private static void processDeleteAction(Contact contact, ContactRepository contactRepo) {
        contactRepo.removeContact(contact);
        System.out.println("The record removed!" + System.lineSeparator());
    }

    /* COUNTING CONTACTS */

    private static void processCountAction(ContactRepository contactRepo) {
        System.out.println("The Phone Book has " + contactRepo.countContacts() + " records." + System.lineSeparator());
    }

    /* EXIT PROGRAM */

    private static void processExitAction(ContactRepository contactRepo, String filename) {
        saveContacts(contactRepo, filename);
        System.out.println("Bye!");
        System.exit(0);
    }

    /* SAVING CONTACTS */

    private static void saveContacts(ContactRepository contactRepo, String filename) {
        if (filename != null) {
            System.out.println("Saving data to: " + filename);

            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
                try {
                    out.writeInt(contactRepo.countContacts());

                    for (Contact contact : contactRepo.getContacts()) {
                        out.writeObject(contact);
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Error occurred when trying to write file: " + filename);
            }
        }
    }

    /* LOADING CONTACTS */

    private static void loadContacts(ContactRepository contactRepo, String filename) {
        if (filename != null) {
            File file = new File(filename);

            if (file.exists() && file.isFile()) {
                System.out.println("Loading data from: " + filename);

                try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                    try {
                        int n = in.readInt();

                        for (int i = 0; i < n; i++) {
                            Contact contact = (Contact) in.readObject();
                            contactRepo.addContact(contact);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error occurred: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred when trying to read file: " + filename);
                }
            }
        }
    }

    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void listContacts(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, contacts.get(i));
        }
    }

    private static String printEditableFields(List<String> fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            sb.append(fields.get(i));
            if (i < fields.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static void printSearchResults(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("Found no results.");
        } else if (contacts.size() == 1) {
            System.out.println("Found 1 result:");
        } else {
            System.out.println("Found " + contacts.size() + " results:");
        }
        listContacts(contacts);
        System.out.println();
    }
}
