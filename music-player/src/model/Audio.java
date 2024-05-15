package model;

import java.util.Date;

abstract public class Audio {
    private final int ID;
    private static int counter;
    private String title;
    private String artistName;
    private int playNumber;
    private int likeNumber;
    private final Date publishDate;
    private Genre genre;
    private String link;
    private String cover;

    public Audio(String title, String artistName, String genre, String link, String cover) {
        this.ID = ++counter;
        this.title = title;
        this.artistName = artistName;
        this.playNumber = 0;
        this.likeNumber = 0;
        this.publishDate = new Date();
        this.genre = Genre.valueOf(genre);
        this.link = link;
        this.cover = cover;
    }

    public int getID() {
        return ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }
}

