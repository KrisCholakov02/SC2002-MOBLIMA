package models.accounts;

import models.Person;

public class User extends Person {
    private String username;
    private String email;
    private String password;

    public User(String f_name, String l_name, String username, String email, String password) {
        super(f_name, l_name);
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User:\n" +
                "Username=" + this.username + "\n" +
                "Email=" + this.email + "\n" +
                "Password=" + this.password + "\n" +
                "F_Name=" + super.getfName() + "\n" +
                "L_Name=" + super.getlName() + "\n";
    }
}