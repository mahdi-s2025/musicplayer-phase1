package controller;


import model.Database;
import model.Report;
import model.useraccount.Admin;

import java.util.ArrayList;
import java.util.Date;

public class AdminController {
    private static AdminController adminController;

    private AdminController() {}

    public static AdminController getAdminController() {
        if (adminController == null) {
            adminController = new AdminController();
        }
        return adminController;
    }

    private Admin admin = null;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public ArrayList<Report> getReports() {
        return Database.getDatabase().getReports();
    }

    public Admin signUp(String username, String password, String fullName, String email,
                        String phoneNumber, int dateOfBirthTmp) {
        Date dateOfBirth = CommonController.getDate(dateOfBirthTmp);

        Admin newAdmin = Admin.getAdmin(username, password, fullName, email, phoneNumber, dateOfBirth);
        setAdmin(newAdmin);
        Database.getDatabase().getUserAccounts().add(newAdmin);
        return newAdmin;
    }
}
