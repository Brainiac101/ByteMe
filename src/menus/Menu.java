package menus;

import database.*;
import users.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    protected static Scanner sc = new Scanner(System.in);

    public void mainMenu() {
        int choice;
        while (true) {
            try {
                System.out.println("Welcome to ByteMe!");
                System.out.println("• 1 for Signing Up");
                System.out.println("• 2 for Logging In");
                System.out.println("• 0 for Exit");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        User temp = login();
                        if (temp == null) continue;
                        else if (temp instanceof VIP) {
                            VIPMenu menu = new VIPMenu((VIP) temp);
                            menu.mainMenu();
                        } else if (temp instanceof Customer) {
                            CustomerMenu menu = new CustomerMenu((Customer) temp);
                            menu.mainMenu();
                        } else if (temp instanceof Admin) {
                            AdminMenu menu = new AdminMenu((Admin) temp);
                            menu.mainMenu();
                        }
                        break;
                    case 0:
                        exit();
                    default:
                        System.out.println("Invalid input\n");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input\n");
            }
        }
    }

    public void signUp() {
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Enter your address: ");
        String address = sc.nextLine();
        if (CustomerList.getCustomer(username) == null) {
            CustomerList.addCustomer(new Customer(username, password, address));
            System.out.println("You have successfully registered!\n");
        }
        else if (CustomerList.getCustomer(username) != null) System.out.println("Username already in use\n");
        else System.out.println("Invalid username or password\n");
    }

    public User login() {
        System.out.println("Login as: ");
        System.out.println("• 1 for Customer");
        System.out.println("• 2 for admin");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        switch (choice) {
            case 1:
                if (CustomerList.getCustomer(username) != null) {
                    if (CustomerList.getCustomer(username).getPassword().equals(password)) return CustomerList.getCustomer(username);
                }
            case 2:
                if (username.equals("admin") && password.equals("admin")) return new Admin("admin", "admin");
            default:
                System.out.println("Invalid Login Credentials\n");
                return null;
        }
    }

    public void exit() {
        System.out.println("Exiting the application...\n");
        System.exit(0);
    }
}
