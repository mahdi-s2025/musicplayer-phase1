package model;

import java.util.ArrayList;
import java.util.Date;

public class Artist extends UserAccount {
    private double income;
    private String biography;
    private final ArrayList<UserAccount> followers;

    public Artist(String username, String password, String fullName, String email,
                  String phoneNumber, Date dateOfBirth, String biography) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
        followers = new ArrayList<>();
        this.biography = biography;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public ArrayList<UserAccount> getFollowers() {
        return followers;
    }
}
