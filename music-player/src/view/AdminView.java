package view;

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

    }
}
