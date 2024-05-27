package view;


import controller.ArtistController;
import model.Album;
import model.audio.Music;
import model.audio.Podcast;
import model.useraccount.UserAccount;

public class ArtistView {
    private static ArtistView artistView;
    private ArtistView() {}

    public static ArtistView getArtistView() {
        if (artistView == null) {
            artistView = new ArtistView();
        }
        return artistView;
    }

    public void start(String[] commands) {
        switch (commands[0]) {
            case "Followers" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    System.out.print("Followers: { ");
                    for (UserAccount user : ArtistController.getArtistController().getFollowers()) {
                        System.out.printf("%s, ", user.getUsername());
                    }
                    System.out.println("}");
                }
            }
            case "ViewsStatistics" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    System.out.println(ArtistController.getArtistController().playsStatistics());
                }
            }
            case "CalculateEarnings" -> {
                if (commands.length != 1) {
                    System.out.println("Invalid number of arguments");
                } else {
                    ArtistController.getArtistController().updateIncome();
                    System.out.println("income: " + ArtistController.getArtistController().getArtist().getIncome());
                }
            }
            case "NewAlbum" -> {
                if (commands.length != 2) {
                    System.out.println("Invalid number of arguments");
                } else {
                    Album newAlbum = ArtistController.getArtistController().createAlbum(commands[1],
                            ArtistController.getArtistController().getArtist().getFullName());
                   if (newAlbum == null) {
                       System.out.println("Creating new album failed");
                   } else {
                       System.out.println("New album: " + newAlbum);
                   }
                }
            }
            case "Publish" -> {
                if (commands.length < 7) {
                    System.out.println("Invalid number of arguments");
                } else {
                    if (commands[1].equals("M")) {
                        if (commands.length != 8) {
                            System.out.println("Invalid number of arguments");
                        } else {
                            Music newMusic = ArtistController.getArtistController().publishMusic(
                                    commands[2], ArtistController.getArtistController().getArtist().getFullName(),
                                    commands[3], commands[4], commands[5], commands[6], Integer.parseInt(commands[7]));
                            if (newMusic == null) {
                                System.out.println("Publishing music failed");
                            } else {
                                System.out.println(newMusic);
                            }
                        }
                    } else if (commands[1].equals("P")) {
                        if (commands.length != 7) {
                            System.out.println("Invalid number of arguments");
                        } else {
                            Podcast newPodcast = ArtistController.getArtistController().publishPodcast(
                                    commands[2], ArtistController.getArtistController().getArtist().getFullName(),
                                    commands[3], commands[4], commands[5], commands[6]);
                            if (newPodcast == null) {
                                System.out.println("Publishing podcast failed");
                            }
                            else {
                                System.out.println(newPodcast);
                            }
                        }
                    }
                }
            }
            default -> System.out.println("Invalid command");
        }
    }
}
