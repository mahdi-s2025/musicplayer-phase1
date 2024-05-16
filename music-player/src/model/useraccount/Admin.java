package model.useraccount;

import java.util.Date;

public class Admin extends UserAccount {
    private static Admin admin;

    private Admin(String username, String password, String fullName, String email, String phoneNumber, Date dateOfBirth) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
    }
    public static Admin getAdmin(String username, String password, String fullName, String email, String phoneNumber, Date dateOfBirth) {
        if (admin == null) {
            admin = new Admin(username, password, fullName, email, phoneNumber, dateOfBirth);
        }
        return admin;
    }

    public static Admin getAdmin() {
        return admin;
    }
}
