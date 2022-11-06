package models.accounts;

import models.accounts.User;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(String fNmae, String lName, String username, String email, String password) {
        super(fNmae, lName, username, email, password, LocalDate.now(), null);
    }
}
