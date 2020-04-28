package contacts;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactRepository contactRepo = new ContactRepository();

        System.out.println("Enter the name of the person:");
        String firstName = scanner.nextLine();

        System.out.println("Enter the surname of the person:");
        String lastName = scanner.nextLine();

        System.out.println("Enter the number:");
        String phoneNumber = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, phoneNumber);
        contactRepo.saveContact(contact);
    }
}
