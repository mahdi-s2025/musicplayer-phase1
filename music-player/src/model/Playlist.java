package model;

import java.util.ArrayList;

public class Playlist {
    private final long ID;
    private static long counter = 0;
    private String name;
    private String creatorName;
    private final ArrayList<Audio> audios;

    public Playlist(String name, String creatorName) {
        this.ID = ++counter;
        this.name = name;
        this.creatorName = creatorName;
        audios = new ArrayList<>();
    }
    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public ArrayList<Audio> getAudios() {
        return audios;
    }
}
