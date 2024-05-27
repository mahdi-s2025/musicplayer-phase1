package controller;


import model.Album;
import model.Database;
import model.audio.Audio;
import model.audio.Music;
import model.audio.Podcast;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;
import model.useraccount.artist.Podcaster;
import model.useraccount.artist.Singer;

import java.util.ArrayList;
import java.util.Date;

public class ArtistController {
    private static ArtistController artistController;

    private ArtistController() {}

    public static ArtistController getArtistController() {
        if (artistController == null) {
            artistController = new ArtistController();
        }
        return artistController;
    }

    private Artist artist;

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public Artist signUp(String username, String password, String fullName,
                               String email, String phoneNumber, int dateOfBirthTmp, String bio, String flag) {

        Date dateOfBirth = CommonController.getDate(dateOfBirthTmp);

        Artist newArtist = null;

        if (flag.equals("S")) {
            Singer singer = new Singer(username, password, fullName,
                    email, phoneNumber, dateOfBirth, bio);
            newArtist = singer;
            setArtist(singer);
            Database.getDatabase().getUserAccounts().add(singer);
        } else if (flag.equals("P")) {
            Podcaster podcaster = new Podcaster(username, password, fullName,
                    email, phoneNumber, dateOfBirth, bio);
            newArtist = podcaster;
            setArtist(podcaster);
            Database.getDatabase().getUserAccounts().add(podcaster);
        }

        return newArtist;
    }

    public ArrayList<UserAccount> getFollowers() {
        return artist.getFollowers();
    }

    public String playsStatistics() {
        StringBuilder result = new StringBuilder();
        if (artist instanceof Singer tmp) {

            for (Album album : tmp.getAlbums()) {
                result.append("Album: ");
                result.append(album.getName());
                result.append("\n");

                for (Audio audio : album.getMusicList()) {
                    result.append("(");
                    result.append(audio.getTitle());
                    result.append(", ");
                    result.append(audio.getPlayNumber());
                    result.append(") ");
                }
                result.append("\n");
            }
        } else if (artist instanceof Podcaster tmp) {

            for (Podcast podcast : tmp.getPodcasts()) {
                result.append("(");
                result.append(podcast.getTitle());
                result.append(", ");
                result.append(podcast.getPlayNumber());
                result.append(") ");
            }
        }
        return result.toString();
    }

    public void updateIncome() {
        if (artist instanceof Singer tmp) {
            int totalPlays = 0;
            for (Album album : tmp.getAlbums()) {
                for (Audio audio : album.getMusicList()) {
                    totalPlays += audio.getPlayNumber();
                }
            }
            tmp.setIncome(totalPlays * 0.4);
        } else if (artist instanceof Podcaster tmp) {
            int totalPlays = 0;
            for (Podcast podcast : tmp.getPodcasts()) {
                totalPlays += podcast.getPlayNumber();
            }
            tmp.setIncome(totalPlays * 0.5);
        }
    }

    public Podcast publishPodcast(String title, String artistName, String genre, String caption, String link, String cover) {
        Podcast newPodcast = null;

        if (artist instanceof Podcaster tmp) {
            newPodcast = new Podcast(title, artistName, genre, caption, link, cover);
            tmp.getPodcasts().add(newPodcast);
            Database.getDatabase().getAudioFiles().add(newPodcast);
        }
        return newPodcast;
    }

    public Album createAlbum(String name, String singerName) {
        Album newAlbum = null;

        if (artist instanceof Singer tmp) {
            newAlbum = new Album(name, singerName);
            tmp.getAlbums().add(newAlbum);
        }
        return newAlbum;
    }

    public Album findAlbum(int ID) {
        if (artist instanceof Singer tmp) {
            for (Album album : tmp.getAlbums()) {
                if (album.getID() == ID) return album;
            }
        }
        return null;
    }

    public Music publishMusic(String title, String artistName, String genre, String lyric, String link, String cover, int albumID) {
        Music newMusic = null;

        if (artist instanceof Singer) {
            Album targetAlbum = findAlbum(albumID);
            if (targetAlbum == null) return null;
            newMusic = new Music(title, artistName, genre, lyric, link, cover, albumID);
            targetAlbum.getMusicList().add(newMusic);
            Database.getDatabase().getAudioFiles().add(newMusic);
        }
        return newMusic;
    }
}
