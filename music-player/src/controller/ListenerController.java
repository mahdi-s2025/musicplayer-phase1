package controller;

import model.*;
import model.audio.Audio;
import model.audio.Music;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;
import model.useraccount.listener.FreeListener;
import model.useraccount.listener.Listener;
import model.useraccount.listener.PremiumListener;
import view.MainView;

import java.util.*;
import java.util.concurrent.*;


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

        Date dateOfBirth = CommonController.getDate(dateOfBirthTmp);

        FreeListener freeListener = new FreeListener(username, password, fullName,
                email, phoneNumber, dateOfBirth);
        freeListener.setCredit(50);
        setListener(freeListener);
        Database.getDatabase().getUserAccounts().add(freeListener);
        return freeListener;
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

//    public Playlist findPlaylist(String name) {
//        Playlist targetPlaylist = null;
//        for (Playlist playlist : listener.getPlaylists()) {
//            if (playlist.getName().equals(name)) {
//                targetPlaylist = playlist;
//            }
//        }
//        return targetPlaylist;
//    }
    // is the same as upper method

    public boolean addAudioToPlaylist(String playlistName, int ID) {
        Playlist targetPlaylist = findListenerPlaylist(playlistName);
        Audio targetAudio = CommonController.findAudio(ID);

        if (targetAudio != null && targetPlaylist != null) {
            if (listener instanceof FreeListener) {
                if (targetPlaylist.getAudios().size() < FreeListener.getMaxMusicsNumberInPlaylist()) {
                    targetPlaylist.getAudios().add(targetAudio);
                    return true;
                } else return false;
            }

            targetPlaylist.getAudios().add(targetAudio);
            return true;
        }
        return false;
    }

    public Audio playAudio(int ID) {
        Audio targetAudio = CommonController.findAudio(ID);
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
        Audio targetAudio = CommonController.findAudio(ID);
        if (targetAudio == null) return false;
        targetAudio.setLikeNumber(targetAudio.getLikeNumber() + 1);
        listener.getGenreLikeNum().put(targetAudio.getGenre(), listener.getGenreLikeNum().get(targetAudio.getGenre()) + 1);
        return true;
    }

    public String getLyric(int ID) {
        Audio targetAudio = CommonController.findAudio(ID);
        if (targetAudio instanceof Music music) return music.getLyric();
        return null;
    }

    public ArrayList<UserAccount> getFollowings() {
        return listener.getFollowing();
    }

    public Report reportArtist(String username, String explanation) {
        UserAccount targetUserAccount = CommonController.findUserAccountByUsername(username);
        Artist targetArtist = null;
        if (targetUserAccount instanceof Artist) {
            targetArtist = (Artist) targetUserAccount;
        }
        if (targetArtist == null) return null;

        Report report = new Report(listener, targetArtist, explanation);
        Database.getDatabase().getReports().add(report);
        return report;
    }

    public boolean follow(String username) {
        UserAccount targetUserAccount = CommonController.findUserAccountByUsername(username);
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

    public ArrayList<Map.Entry<Audio, Integer>> recommendedAudios() {
        Map<Audio, Integer> audioScore = new HashMap<>();
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            int score = listener.getGenrePlayNum().get(audio.getGenre());
            score += listener.getGenreLikeNum().get(audio.getGenre()) * 2;
            UserAccount audioArtist = CommonController.findUserAccountByName(audio.getArtistName()); // maybe can
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

    public void addToCredit(double amount) {
        listener.setCredit(listener.getCredit() + amount);
    }

    public PremiumPlans[] getPremiumPlans() {
        return PremiumPlans.values();
    }

    public boolean premiumBuyOrRenewal(PremiumPlans plan) {
        if (listener.getCredit() >= plan.getCost()) {
            if (listener instanceof FreeListener) {
                PremiumListener premiumListener = new PremiumListener(listener.getUsername(), listener.getPassword(),
                        listener.getFullName(), listener.getEmail(), listener.getPhoneNumber(),
                        listener.getDateOfBirth(), plan.getPeriod());
                premiumListener.setCredit(listener.getCredit() - plan.getCost());
                premiumListener.getPlaylists().addAll(listener.getPlaylists());
                premiumListener.getAudioPlayNum().putAll(listener.getAudioPlayNum());
                premiumListener.getFollowing().addAll(listener.getFollowing());
                premiumListener.getGenrePlayNum().putAll(listener.getGenrePlayNum());
                premiumListener.getGenreLikeNum().putAll(listener.getGenreLikeNum());
                premiumListener.getFavoriteGenres().addAll(listener.getFavoriteGenres());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, plan.getPeriod());
                premiumListener.setSubscriptionExpirationDate(calendar.getTime());
                Database.getDatabase().getUserAccounts().remove(listener);
                setListener(premiumListener);
                Database.getDatabase().getUserAccounts().add(premiumListener);
                MainView.getMainView().setLoggedInUser(premiumListener);
            }
            else {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, plan.getPeriod());
                listener.setSubscriptionExpirationDate(calendar.getTime());
                PremiumListener premiumListener = (PremiumListener) listener;
                premiumListener.setSubRemainingDays(plan.getPeriod());
                premiumListener.setCredit(premiumListener.getCredit() - plan.getCost());
            }
            PremiumListener premiumListener = (PremiumListener) listener;
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            final Runnable task = () -> {
                premiumListener.setSubRemainingDays(premiumListener.getSubRemainingDays() - 1);
            };
            scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS); // for test change time unit to seconds
            return true;
        }
        return false;
    }

    public String getSubscriptionDetails() {
        String result = null;
        if (listener instanceof PremiumListener premiumListener){
            result = "Subscription Expiration Date: " + premiumListener.getSubscriptionExpirationDate() + "\n" +
                    "Subscription Remaining Days: " + premiumListener.getSubRemainingDays();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(premiumListener.getSubscriptionExpirationDate());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            premiumListener.setSubscriptionExpirationDate(calendar.getTime());

            premiumListener.setSubRemainingDays(premiumListener.getSubRemainingDays() - 1);
        }
        return result;
    }
}
