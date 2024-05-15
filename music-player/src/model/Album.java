package model;

import java.util.ArrayList;

public class Album {
    private final long ID;
    private static long counter = 0;
    private String name;
    private String singerName;
    private final ArrayList<Music> musicList;

    public Album(String name, String singerName) {
        this.ID = ++counter;
        this.name = name;
        this.singerName = singerName;
        musicList = new ArrayList<>();
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

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }
}
