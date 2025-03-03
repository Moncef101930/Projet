package tn.esprit.event.utils;



import org.mindrot.jbcrypt.BCrypt;

public class BCryptPass {

    public static String hashPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(10));
    }//test123->sjdhfshuUJHGHIYUGHYGdfsfk

    public static boolean checkPass(String plainPass, String hashedPass) {
        return BCrypt.checkpw(plainPass, hashedPass);
    }//sjdhfshuUJHGHIYUGHYGdfsfk
}
