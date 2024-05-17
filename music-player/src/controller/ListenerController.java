package controller;

import model.Database;
import model.Genre;
import model.Playlist;
import model.audio.Audio;
import model.useraccount.UserAccount;
import model.useraccount.listener.FreeListener;
import model.useraccount.listener.Listener;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerController {
    private static ListenerController listenerController;

    private ListenerController() {}

    public static ListenerController getListenerController() {
        if (listenerController == null) {
            listenerController = new ListenerController();
        }
        return listenerController;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public Listener getListener() {
        return listener;
    }

    public FreeListener signUp(String username, String password, String fullName,
                       String email, String phoneNumber, int dateOfBirthTmp) {
        int dayOfMonth = dateOfBirthTmp % 100;
        dateOfBirthTmp /= 100;

        int month = (dateOfBirthTmp % 100) - 1;
        dateOfBirthTmp /= 100;

        int year = dateOfBirthTmp;

        Date dateOfBirth = new GregorianCalendar(year, month, dayOfMonth).getTime();

        FreeListener freeListener = new FreeListener(username, password, fullName,
                email, phoneNumber, dateOfBirth);
        freeListener.setCredit(50);
        setListener(freeListener);
        Database.getDatabase().getUserAccounts().add(freeListener);
        return freeListener;
    }

    public UserAccount findUsername(String username) {
        for (UserAccount user : Database.getDatabase().getUserAccounts()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public int passwordStrength(String password) {
        int strength = 0;
        if (password.length() < 8)
            return 0;

        if (password.length() > 30)
            return 5;

        Pattern smallLetterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[a-z]))[\\w\\d!@#$%^&*()]*$");
        Matcher smallLetterMatcher = smallLetterPattern.matcher(password);
        if (smallLetterMatcher.matches()) strength++;

        Pattern numberPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[0-9]))[\\w\\d!@#$%^&*()]*$");
        Matcher numberMatcher = numberPattern.matcher(password);
        if (numberMatcher.matches()) strength++;

        Pattern capitalLetterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[A-Z]))[\\w\\d!@#$%^&*()]*$");
        Matcher capitalLetterMatcher = capitalLetterPattern.matcher(password);
        if (capitalLetterMatcher.matches()) strength++;

        Pattern characterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[!@#$%^&*()]))[\\w\\d!@#$%^&*()]*$");
        Matcher characterMatcher = characterPattern.matcher(password);
        if (characterMatcher.matches()) strength++;

        if (strength == 0) return 6;
        return strength;
    }

    public boolean validEmail(String email) {
        Pattern emailValidPattern = Pattern.compile("^[a-z0-9.]{6,30}@[a-z]{3,8}\\.com$");
        Matcher emailMatcher = emailValidPattern.matcher(email);
        return emailMatcher.matches();
    }

    public boolean validPhoneNumber(String phoneNumber) {
        Pattern phoneNumberValidPattern = Pattern.compile("^09[\\d]{9}");
        Matcher phoneNumberMatcher = phoneNumberValidPattern.matcher(phoneNumber);
        return phoneNumberMatcher.matches();
    }

    public Listener login(String username, String password) {
        Listener targetListener = (Listener) findUsername(username);
        if (targetListener.getPassword().equals(password)) {
            setListener(targetListener);
            return targetListener;
        }
        return null;
    }

    public boolean setFavoriteGenres(String input) {
        String[] tmpGenres = input.split(",");
        if (tmpGenres.length == 0 || tmpGenres.length > 4) return false;
        for (String genre : tmpGenres) {
            if (!listener.getFavoriteGenres().add(Genre.valueOf(genre))) return false;
        }
        return true;
    }

    public Playlist createPlaylist(String name) {
        if (listener instanceof FreeListener) {
            if (listener.getPlaylists().size() < FreeListener.getMaxPlaylistCreationNumber()) {
                Playlist newPlaylist = new Playlist(name, listener.getFullName());
                listener.getPlaylists().add(newPlaylist);
                return newPlaylist;
            }
            else return null;
        }
        Playlist newPlaylist = new Playlist(name, listener.getFullName());
        listener.getPlaylists().add(newPlaylist);
        return newPlaylist;
    }

    public Playlist findListenerPlaylist(String name) {
        for (Playlist playlist : listener.getPlaylists()) {
            if (playlist.getName().equals(name)) return playlist;
        }
        return null;
    }

    public Audio findAudio(int ID) {
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            if (audio.getID() == ID) return audio;
        }
        return null;
    }

    public boolean addAudioToPlaylist(String playlistName, int ID) {
        Playlist targetPlaylist = findListenerPlaylist(playlistName);
        Audio targetAudio = findAudio(ID);

        if (listener instanceof FreeListener) {
            if (targetPlaylist.getAudios().size() < FreeListener.getMaxMusicsNumberInPlaylist()) {
                targetPlaylist.getAudios().add(targetAudio);
                return true;
            }
            else return false;
        }

        targetPlaylist.getAudios().add(targetAudio);
        return true;
    }
}
