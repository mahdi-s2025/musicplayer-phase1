package controller;

import model.Database;
import model.Genre;
import model.Playlist;
import model.Report;
import model.audio.Audio;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;
import model.useraccount.listener.FreeListener;
import model.useraccount.listener.Listener;

import java.util.*;
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

    public UserAccount findUserAccountByUsername(String username) {
        for (UserAccount user : Database.getDatabase().getUserAccounts()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public UserAccount findUserAccountByName(String name) {
        for (UserAccount user : Database.getDatabase().getUserAccounts()) {
            if (user.getFullName().equals(name)) return user;
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
        Listener targetListener = (Listener) findUserAccountByUsername(username);
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

    public Audio playAudio(int ID) {
        Audio targetAudio = findAudio(ID);
        if (targetAudio == null) return null;
        targetAudio.setPlayNumber(targetAudio.getPlayNumber() + 1);
        if (listener.getAudioPlayNum().containsKey(targetAudio)) {
            listener.getAudioPlayNum().put(targetAudio, listener.getAudioPlayNum().get(targetAudio) + 1);
        }
        else {
            listener.getAudioPlayNum().put(targetAudio, 1);
        }
        listener.getGenrePlayNum().put(targetAudio.getGenre(), listener.getGenrePlayNum().get(targetAudio.getGenre()) + 1);
        return targetAudio;
    }

    public boolean likeAudio(int ID) {
        Audio targetAudio = findAudio(ID);
        if (targetAudio == null) return false;
        targetAudio.setLikeNumber(targetAudio.getLikeNumber() + 1);
        listener.getGenreLikeNum().put(targetAudio.getGenre(), listener.getGenreLikeNum().get(targetAudio.getGenre()) + 1);
        return true;
    }

    public ArrayList<Audio> searchAudio(String key) {
        ArrayList<Audio> results = new ArrayList<>();
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            if (audio.getTitle().equals(key) || audio.getArtistName().equals(key)) {
                results.add(audio);
            }
        }
        return results;
    }

    public ArrayList<Audio> sortAudios(String flag) {  // maybe should not use comparator interface
        ArrayList<Audio> results = Database.getDatabase().getAudioFiles();
        Comparator<Audio> audioComparator = null;
        if (flag.equals("L")) {
            audioComparator = (Audio o1, Audio o2) -> o2.getLikeNumber() - o1.getLikeNumber();
        } else if (flag.equals("P")) {
            audioComparator = (Audio o1, Audio o2) -> o2.getPlayNumber() - o1.getPlayNumber();
        } else return null;
        results.sort(audioComparator);
        return results;
    }

    public List<Audio> filterAudios(String flag, String key) {
        List<Audio> results = null;
        if (flag.equals("A")) {
            results = Database.getDatabase().getAudioFiles().stream().filter(
                    audio -> audio.getArtistName().equals(key)).toList();
        } else if (flag.equals("G")) {
            results = Database.getDatabase().getAudioFiles().stream().filter(
                    audio -> audio.getGenre().toString().equals(key)).toList();
        }  else if (flag.equals("D")) {
            String[] tmpStartEndDate = key.split("-");
            int tmpStartDate = Integer.parseInt(tmpStartEndDate[0]);
            int tmpEndDate = Integer.parseInt(tmpStartEndDate[1]);

            int dayOfMonth = tmpStartDate % 100;
            tmpStartDate /= 100;

            int month = (tmpStartDate % 100) - 1;
            tmpStartDate /= 100;

            int year = tmpStartDate;
            Date startDate = new GregorianCalendar(year, month, dayOfMonth).getTime();

            dayOfMonth = tmpEndDate % 100;
            tmpEndDate /= 100;

            month = (tmpEndDate % 100) - 1;
            tmpEndDate /= 100;

            year = tmpEndDate;
            Date endDate = new GregorianCalendar(year, month, dayOfMonth).getTime();

            results = Database.getDatabase().getAudioFiles().stream().filter(
                    audio -> (audio.getPublishDate().after(startDate) &&
                            audio.getPublishDate().before(endDate))).toList();
        }
        return results;
    }

    public ArrayList<UserAccount> getFollowings() {
        return listener.getFollowing();
    }

    public Report reportArtist(String username, String explanation) {
        UserAccount targetUserAccount = findUserAccountByUsername(username);
        Artist targetArtist = null;
        if (targetUserAccount instanceof Artist) {
            targetArtist = (Artist) targetUserAccount;
        }
        if (targetArtist == null) return null;

        Report report = new Report(listener, targetArtist, explanation);
        Database.getDatabase().getReports().add(report);
        return report;
    }

    public ArrayList<Artist> getArtistList() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (UserAccount userAccount : Database.getDatabase().getUserAccounts()) {
            if (userAccount instanceof Artist) {
                artists.add((Artist) userAccount);
            }
        }
        return artists;
    }

    public Artist getArtist(String username) {
        UserAccount targetUserAccount = findUserAccountByUsername(username);
        Artist targetArtist = null;
        if (targetUserAccount instanceof Artist) {
            targetArtist = (Artist) targetUserAccount;
        }
        return targetArtist;
    }

    public boolean follow(String username) {
        UserAccount targetUserAccount = findUserAccountByUsername(username);
        Artist targetArtist = null;
        if (targetUserAccount instanceof Artist) {
            targetArtist = (Artist) targetUserAccount;
        }
        if (targetArtist == null) return false;
        listener.getFollowing().add(targetArtist);
        targetArtist.getFollowers().add(listener);
        return true;
    }

    public ArrayList<Playlist> getPlaylists() {
        return listener.getPlaylists();
    }

    public Playlist findPlaylist(String name) {
        Playlist targetPlaylist = null;
        for (Playlist playlist : listener.getPlaylists()) {
            if (playlist.getName().equals(name)) {
                targetPlaylist = playlist;
            }
        }
        return targetPlaylist;
    }

    public ArrayList<Map.Entry<Audio, Integer>> recommendedAudios() {
        Map<Audio, Integer> audioScore = new HashMap<>();
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            int score = listener.getGenrePlayNum().get(audio.getGenre());
            score += listener.getGenreLikeNum().get(audio.getGenre()) * 2;
            UserAccount audioArtist = findUserAccountByName(audio.getArtistName()); // maybe can
            if (listener.getFollowing().contains(audioArtist)) {                // implement better
                score += 30;
            }
            if (listener.getFavoriteGenres().contains(audio.getGenre())) {
                score += 40;
            }
            audioScore.put(audio, score);
        }
        Comparator<Map.Entry<Audio, Integer>> orderBase = (Map.Entry<Audio, Integer> o1, Map.Entry<Audio, Integer> o2)
                -> o2.getValue() - o1.getValue();
        ArrayList<Map.Entry<Audio, Integer>> sortedList = new ArrayList<>(audioScore.entrySet());
        sortedList.sort(orderBase);
        return sortedList;
    }
}
