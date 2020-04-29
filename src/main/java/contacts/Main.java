package contacts;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ContactRepository contactRepo = new ContactRepository();

        while (true) {
            System.out.print("Enter action (add, remove, edit, count, list, exit): ");
            String action = scanner.nextLine();

            switch (action) {
                case "add":
                    processAddAction(contactRepo);
                    break;
                case "remove":
                    processRemoveAction(contactRepo);
                    break;
                case "edit":
                    processEditAction(contactRepo);
                    break;
                case "count":
                    processCountAction(contactRepo);
                    break;
                case "list":
                    processListAction(contactRepo);
                    break;
                case "exit":
                    processExitAction();
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
        }
    }

    private static void processAddAction(ContactRepository contactRepo) {
        System.out.print("Enter the name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter the surname: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter the number: ");
        String phoneNumber = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, phoneNumber);
        contactRepo.addContact(contact);
    }

    private static void processRemoveAction(ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to remove!");
            return;
        }

        listContacts(contactRepo.getContacts());

        System.out.print("Select a record: ");
        int record = Integer.parseInt(scanner.nextLine());

        contactRepo.removeContact(record - 1);
    }

    private static void processEditAction(ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to edit!");
            return;
        }

        listContacts(contactRepo.getContacts());

        System.out.print("Select a record: ");
        int record = Integer.parseInt(scanner.nextLine());

        System.out.print("Select a field (name, surname, number): ");
        String field = scanner.nextLine();

        Contact contact = contactRepo.getContact(record - 1);
        switch (field) {
            case "name":
                System.out.print("Enter the name: ");
                contact.setFirstName(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "surname":
                System.out.print("Enter the surname: ");
                contact.setLastName(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            case "number":
                System.out.print("Enter the number: ");
                contact.setPhoneNumber(scanner.nextLine());
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("Incorrect option! Try again.");
                break;
        }
    }

    private static void processCountAction(ContactRepository contactRepo) {
        System.out.println("The Phone Book has " + contactRepo.countContacts() + " records.");
    }

    private static void processListAction(ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to list!");
        } else {
            listContacts(contactRepo.getContacts());
        }
    }

    private static void processExitAction() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private static void listContacts(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            String phoneNumber = contact.hasNumber() ? contact.getPhoneNumber() : "[no number]";
            System.out.printf("%d. %s %s, %s%n", i + 1, contact.getFirstName(), contact.getLastName(), phoneNumber);
        }
    }
}
