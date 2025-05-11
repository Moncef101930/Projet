package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;
import tn.esprit.event.utils.BCryptPass;
import tn.esprit.event.utils.Session;

public class Login {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(email.isEmpty() || password.isEmpty()){
            errorLabel.setText("Please fill in all fields.");
            return;
        }
        Utilisateur user = serviceUtilisateur.findByEmail(email);
        if(user != null && BCryptPass.checkPass(password, user.getMot_de_passe().replace("$2y$","$2a$"))){
            errorLabel.setText("Login successful!");
            try {
                Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader;
                if(user.getRole() == Role.ADMIN) {
                    loader = new FXMLLoader(MainFX.class.getResource("dashbord_admin.fxml"));
                } else if(user.getRole() == Role.USER) {
                    Session.setCurrentUser(user);
                    loader = new FXMLLoader(MainFX.class.getResource("dashbord_user.fxml"));
                }else{
                    Session.setCurrentUser(user);
                    loader = new FXMLLoader(MainFX.class.getResource("dashbord.fxml"));
                }
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid email or password.");
        }
    }
    @FXML
    private void goToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("signup.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void goToForgot(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("forgot-password.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
