package msh.myonlineshop.handlers;

import android.content.Context;

import msh.myonlineshop.models.User;

public class CurrentUserHandler {
    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        CurrentUserHandler.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean IsLoggedIn() {
        return getCurrentUser() != null &&
                getCurrentUser().getToken() != null &&
                !getCurrentUser().getToken().equals("");
    }

    public static void nullifyUser(Context context) {
        setCurrentUser(null);
        UserDbHandler userDbHandler = new UserDbHandler(context);
        userDbHandler.deleteAllUsers();
    }

}
