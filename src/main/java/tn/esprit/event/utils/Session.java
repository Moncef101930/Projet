package tn.esprit.event.utils;

import tn.esprit.event.entity.Utilisateur;

public class Session {
    private static Utilisateur currentUser;

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }
}
