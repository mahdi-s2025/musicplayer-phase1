package model;

import java.util.ArrayList;
import java.util.Date;

public class Podcaster extends Artist {
    private final ArrayList<Podcast> podcasts;
    public Podcaster(String username, String password, String fullName, String email,
                     String phoneNumber, Date dateOfBirth, String biography) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth, biography);
        podcasts = new ArrayList<>();
    }
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }
}
