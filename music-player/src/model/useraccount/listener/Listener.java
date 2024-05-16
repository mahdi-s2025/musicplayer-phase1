package model.useraccount.listener;

import model.Genre;
import model.Playlist;
import model.audio.Audio;
import model.useraccount.UserAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract public class Listener extends UserAccount {   // abstracting is by my own decision
    private double credit;
    private final ArrayList<Playlist> playlists;
    private final Map<Audio, Integer> audioPlayNum;
    private Date subExpirationDate;
    private final ArrayList<Genre> favoriteGenres;

    public Listener(String username, String password, String fullName, String email, String phoneNumber, Date dateOfBirth) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
        credit = 0.0;
        playlists = new ArrayList<>();
        audioPlayNum = new HashMap<>();
        favoriteGenres = new ArrayList<>();
        subExpirationDate = null;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public Map<Audio, Integer> getAudioPlayNum() {
        return audioPlayNum;
    }

    public Date getSubscriptionExpirationDate() {
        return subExpirationDate;
    }

    public void setSubscriptionExpirationDate(Date subExpirationDate) {
        this.subExpirationDate = subExpirationDate;
    }

    public ArrayList<Genre> getFavoriteGenres() {
        return favoriteGenres;
    }
}
