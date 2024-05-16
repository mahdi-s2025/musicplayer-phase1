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
}
