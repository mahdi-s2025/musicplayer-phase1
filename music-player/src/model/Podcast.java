package model;

public class Podcast extends Audio {
    private String caption;

    public Podcast(String title, String artistName, String genre, String caption, String link, String cover) {
        super(title, artistName, genre, link, cover);
        this.caption = caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getCaption() {
        return caption;
    }
}
