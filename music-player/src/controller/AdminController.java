package controller;


import model.Database;
import model.Report;
import model.useraccount.Admin;

import java.util.ArrayList;

public class AdminController {
    private static AdminController adminController;

    private AdminController() {}

    public static AdminController getAdminController() {
        if (adminController == null) {
            adminController = new AdminController();
        }
        return adminController;
    }

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public ArrayList<Report> getReports() {
        return Database.getDatabase().getReports();
    }
}
