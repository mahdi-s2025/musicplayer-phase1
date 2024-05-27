package view;

import controller.AdminController;
import controller.CommonController;
import controller.ListenerController;
import model.Report;
import model.audio.Audio;
import model.useraccount.artist.Artist;

import java.util.ArrayList;

public class AdminView {
    private static AdminView adminView;
    private AdminView() {}

    public static AdminView getAdminView() {
        if (adminView == null) {
            adminView = new AdminView();
        }
        return adminView;
    }

    public void start(String[] commands) {
        switch (commands[0]) {
            case "Statistics" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    ArrayList<Audio> likeStatistics = CommonController.sortAudios("L");
                    if (likeStatistics != null) {
                        for (Audio audio : likeStatistics) {
                            System.out.println(audio.getTitle() + " ID:" + audio.getID() + " Likes:" +
                                    audio.getLikeNumber());
                        }
                    }
                }
            }
            case "Audios" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    System.out.println("Audios: {");
                    for (Audio audio : CommonController.getAudioList()) {
                        System.out.println("(" + audio.getTitle() + ", " + audio.getID() + "), ");
                    }
                    System.out.println("}");
                }
            }
            case "Audio" -> {
                if (commands.length != 2) {
                    System.out.println("Invalid number of arguments");
                } else {
                    Audio targetAudio = ListenerController.getListenerController().playAudio(Integer.parseInt(commands[1]));
                    System.out.println(targetAudio != null ? targetAudio : "Audio not found");
                }
            }
            case "Artists" -> {
                if (commands.length != 1) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println("Artists: {");
                    for (Artist artist : CommonController.getArtistList()) {
                        System.out.println("(" + artist.getFullName() + ", " + artist.getUsername() + "), ");
                    }
                    System.out.println("}");
                }
            }
            case "Artist" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    Artist targetArtist = CommonController.getArtist(commands[1]);
                    System.out.println(targetArtist == null ? "Artist not found" : targetArtist);
                }
            }
            case "Reports" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    System.out.println("Reports: {");
                    for (Report report : AdminController.getAdminController().getReports()) {
                        System.out.println(report);
                    }
                    System.out.println("}");
                }
            }
            default -> {
                if (!commands[0].equals("exit")) {
                    System.out.println("Invalid command");
                }
            }
        }
    }
}
