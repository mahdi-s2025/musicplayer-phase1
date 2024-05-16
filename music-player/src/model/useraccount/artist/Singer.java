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
}
