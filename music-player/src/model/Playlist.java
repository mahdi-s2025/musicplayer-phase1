package model;

import model.audio.Audio;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
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

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(name + "\t" + creatorName + "\tID: " + ID + "\n"
                + "audio list: {");

        for (int i = 0; i < audios.size()-1; i++) {
            text.append(audios.get(i).getTitle());
            text.append(" ");
            text.append(audios.get(i).getID());
            text.append(", ");
        }
        text.append(audios.getLast().getTitle());
        text.append(" ");
        text.append(audios.getLast().getID());
        text.append("}\n");
        return text.toString();
    }
}
