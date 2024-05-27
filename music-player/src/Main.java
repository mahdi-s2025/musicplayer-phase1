import controller.ListenerController;
import view.MainView;

import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        MainView.getMainView().start();
        for (ScheduledExecutorService executor : ListenerController.getListenerController().getExecutors()) {
            executor.shutdown();
        }
    }
}
