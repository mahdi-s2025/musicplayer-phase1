package controller;

import model.Database;
import model.audio.Audio;
import model.useraccount.Admin;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;
import model.useraccount.listener.Listener;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class CommonController {

    public static UserAccount findUserAccountByUsername(String username) {
        for (UserAccount user : Database.getDatabase().getUserAccounts()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static UserAccount findUserAccountByName(String name) {
        for (UserAccount user : Database.getDatabase().getUserAccounts()) {
            if (user.getFullName().equals(name)) return user;
        }
        return null;
    }

    public static int passwordStrength(String password) {
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

    public static boolean validEmail(String email) {
        Pattern emailValidPattern = Pattern.compile("^[a-z0-9.]{6,30}@[a-z]{3,8}\\.com$");
        Matcher emailMatcher = emailValidPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static boolean validPhoneNumber(String phoneNumber) {
        Pattern phoneNumberValidPattern = Pattern.compile("^09[\\d]{9}");
        Matcher phoneNumberMatcher = phoneNumberValidPattern.matcher(phoneNumber);
        return phoneNumberMatcher.matches();
    }

    public static UserAccount login(String username, String password) {
        UserAccount targetUser = findUserAccountByUsername(username);
        if (targetUser != null && targetUser.getPassword().equals(password)) {
            switch (targetUser) {
                case Listener listener -> ListenerController.getListenerController().setListener(listener);
                case Artist artist -> ArtistController.getArtistController().setArtist(artist);
                case Admin admin -> AdminController.getAdminController().setAdmin(admin);
                default -> {}
            }
            return targetUser;
        }
        return null;
    }

    public static Audio findAudio(int ID) {
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            if (audio.getID() == ID) return audio;
        }
        return null;
    }

    public static ArrayList<Audio> searchAudio(String key) {
        ArrayList<Audio> results = new ArrayList<>();
        for (Audio audio : Database.getDatabase().getAudioFiles()) {
            if (audio.getTitle().equals(key) || audio.getArtistName().equals(key)) {
                results.add(audio);
            }
        }
        return results;
    }

    public static ArrayList<Audio> sortAudios(String flag) {
        ArrayList<Audio> results = Database.getDatabase().getAudioFiles();
        Comparator<Audio> audioComparator;
        if (flag.equals("L")) {
            audioComparator = (Audio o1, Audio o2) -> o2.getLikeNumber() - o1.getLikeNumber();
        } else if (flag.equals("P")) {
            audioComparator = (Audio o1, Audio o2) -> o2.getPlayNumber() - o1.getPlayNumber();
        } else return null;
        results.sort(audioComparator);
        return results;
    }

    public static List<Audio> filterAudios(String flag, String key) {
        List<Audio> results = null;
        switch (flag) {
            case "A" -> results = Database.getDatabase().getAudioFiles().stream().filter(
                    audio -> audio.getArtistName().equals(key)).toList();
            case "G" -> results = Database.getDatabase().getAudioFiles().stream().filter(
                    audio -> audio.getGenre().toString().equals(key)).toList();
            case "D" -> {
                String[] tmpStartEndDate = key.split("-");

                Date startDate = CommonController.getDate(Integer.parseInt(tmpStartEndDate[0]));
                Date endDate = CommonController.getDate((Integer.parseInt(tmpStartEndDate[1])));

                results = Database.getDatabase().getAudioFiles().stream().filter(
                        audio -> (audio.getPublishDate().after(startDate) &&
                                audio.getPublishDate().before(endDate))).toList();
            }
        }

        // maybe should use break.
        // maybe is better to use if statement blow



//        if (flag.equals("-A")) {
//            results = Database.getDatabase().getAudioFiles().stream().filter(
//                    audio -> audio.getArtistName().equals(key)).toList();
//        } else if (flag.equals("-G")) {
//            results = Database.getDatabase().getAudioFiles().stream().filter(
//                    audio -> audio.getGenre().toString().equals(key)).toList();
//        }  else if (flag.equals("-D")) {
//            String[] tmpStartEndDate = key.split("-");
//            int tmpStartDate = Integer.parseInt(tmpStartEndDate[0]);
//            int tmpEndDate = Integer.parseInt(tmpStartEndDate[1]);
//
//            int dayOfMonth = tmpStartDate % 100;
//            tmpStartDate /= 100;
//
//            int month = (tmpStartDate % 100) - 1;
//            tmpStartDate /= 100;
//
//            int year = tmpStartDate;
//            Date startDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
//
//            dayOfMonth = tmpEndDate % 100;
//            tmpEndDate /= 100;
//
//            month = (tmpEndDate % 100) - 1;
//            tmpEndDate /= 100;
//
//            year = tmpEndDate;
//            Date endDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
//
//            results = Database.getDatabase().getAudioFiles().stream().filter(
//                    audio -> (audio.getPublishDate().after(startDate) &&
//                            audio.getPublishDate().before(endDate))).toList();
//        }
        return results;
    }

    public static ArrayList<Artist> getArtistList() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (UserAccount userAccount : Database.getDatabase().getUserAccounts()) {
            if (userAccount instanceof Artist) {
                artists.add((Artist) userAccount);
            }
        }
        return artists;
    }

    public static Artist getArtist(String username) {
        UserAccount targetUserAccount = findUserAccountByUsername(username);
        Artist targetArtist = null;
        if (targetUserAccount instanceof Artist) {
            targetArtist = (Artist) targetUserAccount;
        }
        return targetArtist;
    }

    public static ArrayList<Audio> getAudioList() {
        return Database.getDatabase().getAudioFiles();
    }

    public static Date getDate(int date) {
        int dayOfMonth = date % 100;
        date /= 100;

        int month = (date % 100) - 1;
        date /= 100;

        int year = date;

        return new GregorianCalendar(year, month, dayOfMonth).getTime();
    }
}
