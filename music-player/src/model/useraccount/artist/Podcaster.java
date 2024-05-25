package model.useraccount.artist;

import model.audio.Podcast;

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
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(getFullName() + "\tuser name: " + getUsername() + "\n"
                + "phone number: " + getPhoneNumber() + "\n"
                + "email: " + getEmail() + "\n"
                + "date of birth: " + getDateOfBirth() + "\n"
                + "biography: " + getBiography() + "\n"
                + "podcasts: {");
        if (!getPodcasts().isEmpty()){
            for (int i = 0; i < getPodcasts().size() - 1; i++) {
                text.append(getPodcasts().get(i).getTitle());
                text.append(" ");
                text.append(getPodcasts().get(i).getID());
                text.append(", ");
            }
            text.append(getPodcasts().getLast().getTitle());
            text.append(" ");
            text.append(getPodcasts().getLast().getID());
        }
        text.append("}");

        return text.toString();
    }
}
