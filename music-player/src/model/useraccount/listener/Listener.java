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
    private final ArrayList<UserAccount> following;
    private final Map<Genre, Integer> genrePlayNum;
    private final Map<Genre, Integer> genreLikeNum;
    private Date subExpirationDate;
    private final ArrayList<Genre> favoriteGenres;

    public Listener(String username, String password, String fullName, String email, String phoneNumber, Date dateOfBirth) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
        credit = 0.0;
        playlists = new ArrayList<>();
        audioPlayNum = new HashMap<>();
        following = new ArrayList<>();
        favoriteGenres = new ArrayList<>();
        subExpirationDate = null;
        genrePlayNum = new HashMap<>();
        genreLikeNum = new HashMap<>();
        for (Genre genre : Genre.values()) {
            genrePlayNum.put(genre, 0);
            genreLikeNum.put(genre, 0);
        }
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

    public ArrayList<UserAccount> getFollowing() {
        return following;
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

    public Map<Genre, Integer> getGenrePlayNum() {
        return genrePlayNum;
    }

    public Map<Genre, Integer> getGenreLikeNum() {
        return genreLikeNum;
    }
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(getFullName() + "\tuser name: " + getUsername() + "\tcredit: " + credit + "\n"
                + "phone number: " + getPhoneNumber() + "\n"
                + "email: " + getEmail() + "\n"
                + "date of birth: " + getDateOfBirth() + "\n"
                + "favorite genres: " + getFavoriteGenres() + "\n"
                + "playlists: {");

        if (!playlists.isEmpty()) {
            for (int i = 0; i < playlists.size() - 1; i++) {
                text.append(playlists.get(i).getName());
                text.append(" ");
                text.append(playlists.get(i).getID());
                text.append(", ");
            }
            text.append(playlists.getLast().getName());
            text.append(" ");
            text.append(playlists.getLast().getID());
        }
        text.append("}");

        return text.toString();
    }
}
