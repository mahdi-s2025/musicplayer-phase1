package view;

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

    }

}
