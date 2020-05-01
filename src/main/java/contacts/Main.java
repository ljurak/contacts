package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ContactValidator contactValidator = new ContactValidator();
        ContactRepository contactRepo = new ContactRepository();

        while (true) {
            String action = getUserInput("Enter action (add, remove, edit, count, info, exit): ");

            switch (action) {
                case "add":
                    processAddAction(contactValidator, contactRepo);
                    break;
                case "remove":
                    processRemoveAction(contactRepo);
                    break;
                case "edit":
                    processEditAction(contactValidator, contactRepo);
                    break;
                case "count":
                    processCountAction(contactRepo);
                    break;
                case "info":
                    processInfoAction(contactRepo);
                    break;
                case "exit":
                    processExitAction();
                    break;
                default:
                    System.out.println("Incorrect option! Try again.\n");
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
                System.out.println("Incorrect option! Try again.\n");
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
            System.out.println("Wrong number format!");
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
            System.out.println("Wrong number format!");
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

    /* REMOVING CONTACT */

    private static void processRemoveAction(ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to remove!\n");
            return;
        }

        listContacts(contactRepo.getContacts());

        int record = Integer.parseInt(getUserInput("Select a record: "));

        contactRepo.removeContact(record - 1);
        System.out.println("The record removed!\n");
    }

    /* UPDATING CONTACT */

    private static void processEditAction(ContactValidator contactValidator, ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to edit!");
            return;
        }

        listContacts(contactRepo.getContacts());

        int record = Integer.parseInt(getUserInput("Select a record: "));
        Contact contact = contactRepo.getContact(record - 1);

        if (contact.isPerson) {
            PersonContact person = (PersonContact) contact;
            editPerson(person, contactValidator);
        } else {
            OrganizationContact organization = (OrganizationContact) contact;
            editOrganization(organization, contactValidator);
        }
    }

    private static void editPerson(PersonContact person, ContactValidator contactValidator) {
        String field = getUserInput("Select a field (name, surname, birth, gender, number): ");

        switch (field) {
            case "name":
                person.setFirstName(getUserInput("Enter the name: "));
                break;
            case "surname":
                person.setLastName(getUserInput("Enter the surname: "));
                break;
            case "number":
                String phoneNumber = getUserInput("Enter the number: ");
                if (contactValidator.validatePhoneNumber(phoneNumber)) {
                    person.setPhoneNumber(phoneNumber);
                } else {
                    person.setPhoneNumber(null);
                    System.out.println("Wrong number format!");
                }
                break;
            case "birth":
                String birthDate = getUserInput("Enter the birth date: ");
                if (contactValidator.validateBirthDate(birthDate)) {
                    person.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("d-M-yyyy")));
                } else {
                    person.setBirthDate(null);
                    System.out.println("Bad birth date!");
                }
                break;
            case "gender":
                String gender = getUserInput("Enter the gender (M, F): ");
                if (contactValidator.validateGender(gender)) {
                    person.setGender(Gender.valueByAbbr(gender));
                } else {
                    person.setGender(null);
                    System.out.println("Bad gender!");
                }
                break;
            default:
                System.out.println("Incorrect option! Try again.\n");
                return;
        }

        person.setUpdateDate(LocalDateTime.now().withNano(0));
        System.out.println("The record updated!\n");
    }

    private static void editOrganization(OrganizationContact organization, ContactValidator contactValidator) {
        String field = getUserInput("Select a field (name, address, number): ");

        switch (field) {
            case "name":
                organization.setName(getUserInput("Enter the name: "));
                break;
            case "address":
                organization.setAddress(getUserInput("Enter the address: "));
                break;
            case "number":
                String phoneNumber = getUserInput("Enter the number: ");
                if (contactValidator.validatePhoneNumber(phoneNumber)) {
                    organization.setPhoneNumber(phoneNumber);
                } else {
                    organization.setPhoneNumber(null);
                    System.out.println("Wrong number format!");
                }
                break;
            default:
                System.out.println("Incorrect option! Try again.\n");
                return;
        }

        organization.setUpdateDate(LocalDateTime.now().withNano(0));
        System.out.println("The record updated!\n");
    }

    /* COUNTING CONTACTS */

    private static void processCountAction(ContactRepository contactRepo) {
        System.out.println("The Phone Book has " + contactRepo.countContacts() + " records.\n");
    }

    /* INFO ABOUT CONTACT */

    private static void processInfoAction(ContactRepository contactRepo) {
        if (contactRepo.countContacts() == 0) {
            System.out.println("No records to list!");
            return;
        }

        listContacts(contactRepo.getContacts());

        int record = Integer.parseInt(getUserInput("Select a record: "));
        Contact contact = contactRepo.getContact(record - 1);

        if (contact.isPerson) {
            PersonContact person = (PersonContact) contact;
            showPersonInfo(person);
        } else {
            OrganizationContact organization = (OrganizationContact) contact;
            showOrganizationInfo(organization);
        }
        System.out.println();
    }

    private static void showPersonInfo(PersonContact person) {
        System.out.println("Name: " + person.getFirstName());
        System.out.println("Surname: " + person.getLastName());

        String birthDate = (person.getBirthDate() != null) ? person.getBirthDate().toString() : "[no data]";
        System.out.println("Birth date: " + birthDate);

        String gender = (person.getGender() != null) ? person.getGender().getAbbr() : "[no data]";
        System.out.println("Gender: " + gender);

        String phoneNumber = person.hasNumber() ? person.getPhoneNumber() : "[no data]";
        System.out.println("Number: " + phoneNumber);

        System.out.println("Time created: " + person.getCreationDate());
        System.out.println("Time last edit: " + person.getUpdateDate());
    }

    private static void showOrganizationInfo(OrganizationContact organization) {
        System.out.println("Organization name: " + organization.getName());
        System.out.println("Address: " + organization.getAddress());

        String phoneNumber = organization.hasNumber() ? organization.getPhoneNumber() : "[no data]";
        System.out.println("Number: " + phoneNumber);

        System.out.println("Time created: " + organization.getCreationDate());
        System.out.println("Time last edit: " + organization.getUpdateDate());
    }

    /* EXIT PROGRAM */

    private static void processExitAction() {
        System.out.println("Bye!");
        System.exit(0);
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
}
