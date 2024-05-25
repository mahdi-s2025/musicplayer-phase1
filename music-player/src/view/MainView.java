package view;


import controller.AdminController;
import controller.ArtistController;
import controller.CommonController;
import controller.ListenerController;
import model.useraccount.Admin;
import model.useraccount.UserAccount;
import model.useraccount.artist.Artist;
import model.useraccount.listener.Listener;

import java.util.Scanner;

public class MainView {
    private static MainView mainView;

    private MainView() {}

    public static MainView getMainView() {
        if (mainView == null) {
            mainView = new MainView();
        }
        return mainView;
    }

    private UserAccount loggedInUser;

    public void setLoggedInUser(UserAccount loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public UserAccount getLoggedInUser() {
        return loggedInUser;
    }
//    public void setListener(Listener listener) {
//        this.listener = listener;
//    }
//
//    public Listener getListener() {
//        return listener;
//    }


//    public String getCommand() {
//        return command;
//    }
//
//    public void setCommand(String command) {
//        this.command = command;
//    }

    Scanner sc = new Scanner(System.in);

    public void start() {
        String[] commands = new String[1];
        commands[0] = "start";
        System.out.println("First of all, you should create an admin account: ");
        while (AdminController.getAdminController().getAdmin() == null) {
            commands = sc.nextLine().split(" -");
            adminSignUpView(commands);
        }
        while (!commands[0].equals("exit")) {
            commands = sc.nextLine().split(" -");
            switch (commands[0]) {
                case "Signup": {
                    signupView(commands);
                    break;
                }
                case "Login": {
                    if (commands.length != 3) {
                        System.out.println("Wrong number of arguments!");
                        break;
                    }
                    UserAccount targetUser = CommonController.login(commands[1], commands[2]);
                    if (targetUser == null) {
                        System.out.println("Login failed! Wrong username or password!");
                    } else {
                        setLoggedInUser(targetUser);
                        System.out.println("Login successful!");
                        System.out.println(getLoggedInUser());
                        if (targetUser instanceof Artist artist) {
                            ArtistController.getArtistController().updateIncome();
                            System.out.println("income: " + artist.getIncome());
                        }
                    }
                    break;
                }
                case "Logout": {
                    if (commands.length != 1) {
                        System.out.println("Wrong number of arguments!");
                        break;
                    }
                    AdminController.getAdminController().setAdmin(null);
                    ListenerController.getListenerController().setListener(null);
                    ArtistController.getArtistController().setArtist(null);
                    setLoggedInUser(null);
                    System.out.println("Logout successful!");
                    break;
                }
                case "AccountInfo": {
                    if (commands.length != 1) {
                        System.out.println("Wrong number of arguments!");
                        break;
                    }
                    if (getLoggedInUser() != null) {
                        System.out.println(getLoggedInUser());
                        if (getLoggedInUser() instanceof Artist artist) {
                            ArtistController.getArtistController().updateIncome();
                            System.out.println("income: " + artist.getIncome());
                        }
                    }
                    break;
                }
                default: {
                    switch (getLoggedInUser()) {
                        case Listener ignored -> ListenerView.getListenerView().start(commands);
                        case Artist ignored -> ArtistView.getArtistView().start(commands);
                        case Admin ignored -> AdminView.getAdminView().start(commands);
                        default -> System.out.println("You should login first!");
                    }
                }
            }
        }
    }

    private void adminSignUpView(String[] commands) {
        if (!commands[0].equals("Signup")) {
            System.out.println("Invalid command");
            return;
        } else if (commands.length != 7) {
            System.out.println("Invalid number of arguments");
            return;
        }
        int passwordStrength = CommonController.passwordStrength(commands[2]);
        if (passwordStrength == 0) {
            System.out.println("Password is too short. Choose a longer one.");
        } else if (passwordStrength == 5) {
            System.out.println("Password is too long. Choose a shorter one.");
        } else if (passwordStrength == 6) {
            System.out.println("Invalid character in password. You can only enter numbers, capital and small letters" +
                    "and these characters: ! @ # $ % ^ & * ( )");
        } else if (passwordStrength > 0 && passwordStrength < 4) {
            System.out.println("Password strength is " + passwordStrength + "/4. Choose a stronger one.");
        } else if (!CommonController.validEmail(commands[4])) {
            System.out.println("Invalid email address");
        } else if (!CommonController.validPhoneNumber(commands[5])) {
            System.out.println("Invalid phone number");
        } else {
            setLoggedInUser(AdminController.getAdminController().signUp(commands[1], commands[2], commands[3],
                    commands[4], commands[5], Integer.parseInt(commands[6])));
            System.out.println("Signed up successful");
            System.out.println(getLoggedInUser());
        }
    }

    private void signupView(String[] commands) {
        if (commands.length < 8) {
            System.out.println("Invalid number of arguments");
            return;
        } else if (CommonController.findUserAccountByUsername(commands[2]) != null) {
            System.out.println("Username already exists");
            return;
        }
        int passwordStrength = CommonController.passwordStrength(commands[3]);
        if (passwordStrength == 0) {
            System.out.println("Password is too short. Choose a longer one.");
        } else if (passwordStrength == 5) {
            System.out.println("Password is too long. Choose a shorter one.");
        } else if (passwordStrength == 6) {
            System.out.println("Invalid character in password. You can only enter numbers, capital and small letters" +
                    "and these characters: ! @ # $ % ^ & * ( )");
        } else if (passwordStrength > 0 && passwordStrength < 4) {
            System.out.println("Password strength is " + passwordStrength + "/4. Choose a stronger one.");
        } else if (!CommonController.validEmail(commands[5])) {
            System.out.println("Invalid email address");
        } else if (!CommonController.validPhoneNumber(commands[6])) {
            System.out.println("Invalid phone number");
        } else {
            switch (commands[1]) {
                case "L":
                {
                    if (commands.length != 8) {
                        System.out.println("Invalid number of arguments");
                        break;
                    } else {
                        setLoggedInUser(ListenerController.getListenerController().signUp(commands[2], commands[3], commands[4],
                                commands[5], commands[6], Integer.parseInt(commands[7])));

                        while (ListenerController.getListenerController().getListener().getFavoriteGenres().isEmpty()) {
                            setFavoriteGenresView();
                        }

                        System.out.println("Signed up successful");
                        System.out.println(getLoggedInUser());
                    }
                    break;
                }
                case "S", "P":
                {
                    if (commands.length != 9) {
                        System.out.println("Invalid number of arguments");
                        break;
                    } else {
                        Artist artist = ArtistController.getArtistController().signUp(commands[2], commands[3], commands[4],
                                commands[5], commands[6], Integer.parseInt(commands[7]), commands[8], commands[1]);
                        setLoggedInUser(artist);
                        System.out.println("Signed up successful");
                        System.out.println(getLoggedInUser());
                        System.out.println("income: " + artist.getIncome());
                    }
                    break;
                }

                default:
                    System.out.println("Invalid argument");

            }
        }
    }

    private void setFavoriteGenresView() {
        System.out.println("Now you should set your favorite genres");
        System.out.println("Note: You have to choose 1 to 4 genres.");
        System.out.println("ROCK, POP, JAZZ, HIPHOP, COUNTRY, TRUE_CRIME, SOCIETY, INTERVIEW, HISTORY");
        String[] input = sc.nextLine().split(" -");
        if (!input[0].equals("FavouriteGenres")) {
            System.out.println("Invalid command");
            System.out.println("Sign up failed");
        } else if (input.length != 2) {
            System.out.println("Invalid number of arguments");
            System.out.println("Sign up failed");
        } else if (!ListenerController.getListenerController().setFavoriteGenres(input[1])) {
            System.out.println("Invalid argument");
            System.out.println("Sign up failed");
        } else {
            System.out.println("Favourite Genres set successful");
        }
    }
}
