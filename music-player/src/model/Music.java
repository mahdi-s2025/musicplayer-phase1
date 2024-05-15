package model;

public class Music extends Audio {
    private String lyric;
    private int albumID;

    public Music(String title, String artistName, String genre, String lyric, String link, String cover, int albumID) {
        super(title, artistName, genre, link, cover);
        this.lyric = lyric;
        this.albumID = albumID;
    }
    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
