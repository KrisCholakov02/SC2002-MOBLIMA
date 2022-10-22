import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataManager {
    private static final Path users_path = Paths.get("./data/users.txt");
    private static final Path admins_path = Paths.get("./data/admins.txt");
    private static final Path reviews_path = Paths.get("./data/users.txt");
    private static final Path movies_path = Paths.get("./data/users.txt");

    private static final String user_entry_regex = "^Username=(?<username>.+), Email=(?<email>.+), Password=(?<password>.+), F_Name=(?<fname>.+), L_Name=(?<lname>.+)$";

    public static boolean addUser (User user) {
        try {
            Files.writeString(users_path, user.toString(), StandardOpenOption.APPEND);
            MOBLIMA.printConsoleMessage("User successfully registered!");
            return true;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! User is not saved to the database.");
            return false;
        }
    }

    public static boolean addAdmin (User user) {
        try {
            Files.writeString(admins_path, user.toString(), StandardOpenOption.APPEND);
            MOBLIMA.printConsoleMessage("Admin successfully registered!");
            return true;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! User is not saved to the database.");
            return false;
        }
    }

    public static int ifUserExists(User user) {
        try {
            List<String> lines = Files.readAllLines(users_path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("")) continue;

                Pattern r = Pattern.compile(user_entry_regex);
                Matcher m = r.matcher(line);
                if (m.find()) {
                    String line_username = m.group("username");
                    String line_email = m.group("email");
                    if (line_username.equals(user.getUsername()) || line_email.equals(user.getEmail())) {
                        return 1;
                    }
                } else {
                    MOBLIMA.printConsoleMessage("Wrong entry format or Regex!");
                    return -1;
                }
            }
            return 0;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! Can't be checked if the user exists.");
            return -1;
        }
    }

    public static int ifAdminExists(Admin admin) {
        try {
            List<String> lines = Files.readAllLines(admins_path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("")) continue;

                Pattern r = Pattern.compile(user_entry_regex);
                Matcher m = r.matcher(line);
                if (m.find()) {
                    String line_username = m.group("username");
                    String line_email = m.group("email");
                    if (line_username.equals(admin.getUsername()) || line_email.equals(admin.getEmail())) {
                        return 1;
                    }
                } else {
                    MOBLIMA.printConsoleMessage("Wrong entry format or Regex!");
                    return -1;
                }
            }
            return 0;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! Can't be checked if the user exists.");
            return -1;
        }
    }

    public static void showAdmins() {
        try {
            List<String> lines = Files.readAllLines(admins_path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("")) continue;

                System.out.print("*");
                System.out.println(line);
            }
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! Can't be checked if the user exists.");
        }
    }

    public static User checkCredentials(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(users_path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("")) continue;

                Pattern r = Pattern.compile(user_entry_regex);
                Matcher m = r.matcher(line);

                if (m.find()) {
                    String line_username = m.group("username");
                    String line_email = m.group("email");
                    String line_password = m.group("password");

                    if ((line_username.equals(username) || line_email.equals(username)) && line_password.equals(password)) {
                        String f_name = m.group("fname");
                        String l_name = m.group("lname");
                        User loggedUser = new User(f_name, l_name, line_username, line_email, line_password);
                        return loggedUser;
                    }
                } else {
                    MOBLIMA.printConsoleMessage("Wrong entry format or Regex!");
                    return null;
                }
            }
            MOBLIMA.printConsoleMessage("Wrong credentials!");
            return null;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! Can't be checked if the user exists.");
            return null;
        }
    }

    public static Admin checkAdminCredentials(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(admins_path);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.equals("")) continue;

                Pattern r = Pattern.compile(user_entry_regex);
                Matcher m = r.matcher(line);

                if (m.find()) {
                    String line_username = m.group("username");
                    String line_email = m.group("email");
                    String line_password = m.group("password");

                    if ((line_username.equals(username) || line_email.equals(username)) && line_password.equals(password)) {
                        String f_name = m.group("fname");
                        String l_name = m.group("lname");
                        Admin loggedUser = new Admin(f_name, l_name, line_username, line_email, line_password);
                        return loggedUser;
                    }
                } else {
                    MOBLIMA.printConsoleMessage("Wrong entry format or Regex!");
                    return null;
                }
            }
            MOBLIMA.printConsoleMessage("Wrong credentials!");
            return null;
        } catch (IOException exception) {
            MOBLIMA.printConsoleMessage("Error: Invalid Path! Can't be checked if the user exists.");
            return null;
        }
    }
}