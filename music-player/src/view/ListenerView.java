package view;

import controller.CommonController;
import controller.ListenerController;
import model.Playlist;
import model.PremiumPlans;
import model.audio.Audio;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenerView {
    private static ListenerView listenerView;
    private ListenerView() {}

    public static ListenerView getListenerView() {
        if (listenerView == null) {
            listenerView = new ListenerView();
        }
        return listenerView;
    }

    public void start(String[] commands) {
        switch (commands[0]) {
            case "GetSuggestions" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    ArrayList<Map.Entry<Audio, Integer>> suggestions = ListenerController.getListenerController().recommendedAudios();
                    int numOfSuggestions = Integer.parseInt(commands[1]);
                    if (suggestions.size() < numOfSuggestions) {
                        numOfSuggestions = suggestions.size();
                    }
                    for (int i = 0; i < numOfSuggestions; i += 3) {
                        System.out.print(suggestions.get(i).getKey().getTitle() + " " +
                                suggestions.get(i).getKey().getArtistName() + " " +
                                "ID:" + suggestions.get(i).getKey().getID());
                        if (i+1 < numOfSuggestions) {
                            System.out.print("  |  " + suggestions.get(i+1).getKey().getTitle() + " " +
                                    suggestions.get(i+1).getKey().getArtistName() + " " +
                                    "ID:" + suggestions.get(i+1).getKey().getID());
                        }

                        if (i+2 < numOfSuggestions) {
                            System.out.println("  |  " + suggestions.get(i+2).getKey().getTitle() + " " +
                                    suggestions.get(i+2).getKey().getArtistName() + " " +
                                    "ID:" + suggestions.get(i+2).getKey().getID());
                        }
                    }
                    System.out.println();
                }
            }
            case "Artists" -> {
                if (commands.length != 1) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.print("Artists: {");
                    for (Artist artist : CommonController.getArtistList()) {
                        System.out.print("(" + artist.getFullName() + ", " + artist.getUsername() + "), ");
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
            case "Follow" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println(ListenerController.getListenerController().follow(commands[1])
                    ? "Following was successful" : "Following failed");
                }
            }
            case "Search" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.print("Audios: {");
                    for (Audio audio : CommonController.searchAudio(commands[1])) {
                        System.out.print("(" + audio.getTitle() + ", " + audio.getID() + "), ");
                    }
                    System.out.println("}");

                }
            }
            case "Sort" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    ArrayList<Audio> sortedAudios = CommonController.sortAudios(commands[1]);
                    if (sortedAudios == null) {
                        System.out.println("Sorting failed");
                    } else {
                        for (Audio audio : sortedAudios) {
                            System.out.println(audio.getTitle() + " ID:" + audio.getID() + " Likes:" +
                                    audio.getLikeNumber() + " Plays:" + audio.getPlayNumber());
                        }
                    }
                }
            }
            case "Filter" -> {
                if (commands.length != 3) {
                    System.out.println("Wrong number of arguments");
                } else {
                    List<Audio> filteredList = CommonController.filterAudios(commands[1], commands[2]);
                    if (filteredList == null) {
                        System.out.println("Filtering failed");
                    } else {
                        for (Audio audio : filteredList) {
                            System.out.println(audio.getTitle() + " ID:" + audio.getID() + " Artist Name: " +
                                    audio.getArtistName() + " Genre: " + audio.getGenre() +
                                    " Publish Date: " + audio.getPublishDate());
                        }
                    }
                }
            }
            case "Add" -> {
                if (commands.length != 3) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println(ListenerController.getListenerController().addAudioToPlaylist(commands[1],
                            Integer.parseInt(commands[2])) ? "Audio added successfully" : "Adding audio to playlist failed");
                }
            }
            case "ShowPlaylists" -> System.out.println(commands.length != 1 ? "Wrong number of arguments" :
                        ListenerController.getListenerController().getPlaylists());

            case "SelectPlaylist" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    Playlist targetPlaylist = ListenerController.getListenerController().findListenerPlaylist(commands[1]);
                    System.out.println(targetPlaylist != null ? targetPlaylist : "Playlist not found");
                }
            }
            case "Play" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    Audio targetAudio = ListenerController.getListenerController().playAudio(Integer.parseInt(commands[1]));
                    System.out.println(targetAudio != null ? targetAudio : "Audio not found");
                }
            }
            case "Like" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println(ListenerController.getListenerController().likeAudio(Integer.parseInt(commands[1]))
                    ? "Audio liked successfully" : "Liking audio failed");
                }
            }
            case "Lyric" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    String lyric = ListenerController.getListenerController().getLyric(Integer.parseInt(commands[1]));
                    System.out.println(lyric == null ? "Lyric not found" : lyric);
                }
            }
            case "NewPlaylist" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    Playlist newPlaylist = ListenerController.getListenerController().createPlaylist(commands[1]);
                    System.out.println(newPlaylist == null ? "Creating new playlist failed" : newPlaylist);
                }
            }
            case "Followings" -> {
                if (commands.length != 1) {
                    System.out.println("Wrong number of arguments");
                } else {
                    for (UserAccount user : ListenerController.getListenerController().getFollowings()) {
                        System.out.println(user.getFullName() + "\t" + user.getUsername());
                    }
                }
            }
            case "Report" -> {
                if (commands.length != 3) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println(ListenerController.getListenerController().reportArtist(commands[1], commands[2]) == null
                    ? "Report artist failed" : commands[1] + " reported");
                }
            }
            case "IncreaseCredit" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    ListenerController.getListenerController().addToCredit(Double.parseDouble(commands[1]));
                    System.out.println("Increase credit successfully");
                }
            }
            case "ShowPremiumPlans" -> {
                if (commands.length != 1) {
                    System.out.println("Wrong number of arguments");
                } else {
                    for (PremiumPlans plan : ListenerController.getListenerController().getPremiumPlans()) {
                        System.out.println(plan.name() + "\t" + plan.getPeriod() + " Days\t" + plan.getCost() + "$");
                    }
                }
            }
            case "GetPremium" -> {
                if (commands.length != 2) {
                    System.out.println("Wrong number of arguments");
                } else {
                    System.out.println(ListenerController.getListenerController().premiumBuyOrRenewal(PremiumPlans.valueOf(commands[1]))
                            ? "Get premium plan successfully" : "Get premium failed");
                }
            }
            case "ShowSubscriptionDetails" -> {
                if (commands.length != 1) {
                    System.out.println("Wrong number of arguments");
                } else {
                    String details = ListenerController.getListenerController().getSubscriptionDetails();
                    System.out.println(details == null ? "You don't have any subscriptions" : details);
                }
            }
        }
    }

}
