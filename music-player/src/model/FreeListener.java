package model;

import java.util.Date;

public class FreeListener extends Listener {
    private static final int MAX_MUSICS_NUMBER_IN_PLAYLIST = 10;
    private static final int MAX_PLAYLIST_CREATION_NUMBER = 3;

    public FreeListener(String username, String password, String fullName, String email, String phoneNumber, Date dateOfBirth) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
    }

    public static int getMaxMusicsNumberInPlaylist() {
        return MAX_MUSICS_NUMBER_IN_PLAYLIST;
    }
    public static int getMaxPlaylistCreationNumber() {
        return MAX_PLAYLIST_CREATION_NUMBER;
    }
}
