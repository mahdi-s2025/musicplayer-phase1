package model.useraccount.artist;

import model.Album;

import java.util.ArrayList;
import java.util.Date;

public class Singer extends Artist {
    private final ArrayList<Album> albums;

    public Singer(String username, String password, String fullName, String email,
                  String phoneNumber, Date dateOfBirth, String biography) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth, biography);
        albums = new ArrayList<>();
    }
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(getFullName() + "\tuser name: " + getUsername() + "\n"
                + "phone number: " + getPhoneNumber() + "\n"
                + "email: " + getEmail() + "\n"
                + "date of birth: " + getDateOfBirth() + "\n"
                + "biography: " + getBiography() + "\n"
                + "albums: {");
        for (int i = 0; i < getAlbums().size()-1; i++) {
            text.append(getAlbums().get(i).getName());
            text.append(" ");
            text.append(getAlbums().get(i).getID());
            text.append(", ");
        }
        text.append(getAlbums().getLast().getName());
        text.append(" ");
        text.append(getAlbums().getLast().getID());
        text.append("}");

        return text.toString();
    }
}
