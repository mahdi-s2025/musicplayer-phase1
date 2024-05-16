package model;

import java.util.ArrayList;

public class Database {
    private static Database database;

    private Database() {
        userAccounts = new ArrayList<>();
        audioFiles = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private final ArrayList<UserAccount> userAccounts;
    private final ArrayList<Audio> audioFiles;
    private final ArrayList<Report> reports;

    public ArrayList<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public ArrayList<Audio> getAudioFiles() {
        return audioFiles;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }
}
