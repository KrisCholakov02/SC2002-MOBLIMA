package pages;

import constants.Regexes;
import data_managers.AdminManager;
import models.accounts.Admin;

import java.util.ArrayList;
import java.util.Scanner;

public class MasterPage {
    private static String masterCode = "master111";

    public static void masterLoginPage() {
        Scanner scanner = new Scanner(System.in);
        PageElements.printHeader();
        System.out.println("MASTER LOGIN\n");
        System.out.print("Enter the master's code: ");
        String code = scanner.nextLine();
        if (!code.equals(masterCode)) PageElements.printConsoleMessage("Wrong code!");
        else masterPage();
    }

    public static void masterPage() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            PageElements.printHeader();
            System.out.println("Select the action you want:\n" +
                    "(Type the number of the choice)\n" +
                    "       1 - Create an Admin Account\n" +
                    "       2 - Show All Admins\n" +
                    "       3 - Back to Start Page");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createAdminPage();
                    break;
                case 2:
                    showAdminsPage();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    PageElements.printConsoleMessage("Invalid Choice!");
                    break;
            }
        }
    }

    public static void createAdminPage() {
        Scanner scanner = new Scanner(System.in);
        PageElements.printHeader();
        System.out.println("CREATE ADMIN\n");
        System.out.println("*** Username must contain only letters, digits or '_'");
        System.out.println("*** Password Must:\n" +
                "       contain 1 number (0-9)\n" +
                "       contain 1 uppercase letters\n" +
                "       contain 1 lowercase letters\n" +
                "       contain 1 non-alpha numeric number\n" +
                "       be 8-16 characters long\n");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (!username.matches(Regexes.username_regex)) {
            PageElements.printConsoleMessage("Error: The username is not in wanted format.");
            return;
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (!email.matches(Regexes.email_regex)) {
            PageElements.printConsoleMessage("Error: The email is not in wanted format.");
            return;
        }
        System.out.print("Password: ");
        String password1 = scanner.nextLine();
        if (!password1.matches(Regexes.password_regex)) {
            PageElements.printConsoleMessage("Error: The password is not in wanted format.");
            return;
        }
        System.out.print("Confirm Password: ");
        String password2 = scanner.nextLine();
        if (!password1.equals(password2)) {
            PageElements.printConsoleMessage("Error: The passwords do not match.");
            return;
        }
        System.out.print("First Name: ");
        String f_name = scanner.nextLine();
        System.out.print("Last Name: ");
        String l_name = scanner.nextLine();

        Admin admin = new Admin(f_name, l_name, username, email, password1);
        if (!AdminManager.ifAdminExists(admin)) {
            AdminManager.writeAdmin(admin);
        } else {
            PageElements.printConsoleMessage("Error: The admin already exists.");
        }
    }

    public static void showAdminsPage() {
        PageElements.printHeader();
        System.out.println("SHOW ADMINS\n");
        ArrayList<Admin> admins = AdminManager.readAdmins();
        for (int i = 0; i < admins.size(); i++) {
            System.out.print("*");
            System.out.println(admins.get(i).toString());
            System.out.println();
        }
    }
}
