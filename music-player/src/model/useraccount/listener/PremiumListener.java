package model.useraccount.listener;

import java.util.Date;

public class PremiumListener extends Listener {
    private int subRemainingDays;
    public PremiumListener(String username, String password, String fullName,
                           String email, String phoneNumber, Date dateOfBirth, int subRemainingDays) {
        super(username, password, fullName, email, phoneNumber, dateOfBirth);
        this.subRemainingDays = subRemainingDays;
    }
    public int getSubRemainingDays() {
        return subRemainingDays;
    }

    public void setSubRemainingDays(int subRemainingDays) {
        this.subRemainingDays = subRemainingDays;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(getFullName() + "\tuser name: " + getUsername() + "\tcredit: " + getCredit() + "\n"
                + "phone number: " + getPhoneNumber() + "\n"
                + "email: " + getEmail() + "\n"
                + "date of birth: " + getDateOfBirth() + "\n"
                + "subscription remaining days: " + subRemainingDays + "\n"
                + "favorite genres: " + getFavoriteGenres() + "\n"
                + "playlists: {");

        for (int i = 0; i < getPlaylists().size()-1; i++) {
            text.append(getPlaylists().get(i).getName());
            text.append(" ");
            text.append(getPlaylists().get(i).getID());
            text.append(", ");
        }
        text.append(getPlaylists().getLast().getName());
        text.append(" ");
        text.append(getPlaylists().getLast().getID());
        text.append("}");

        return text.toString();
    }
}
