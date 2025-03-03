package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;
import tn.esprit.event.utils.BCryptPass;
import tn.esprit.event.utils.EmailSender;

import java.util.Random;

public class ForgotPassword {

    @FXML
    private TextField codeField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private VBox emailPane;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private VBox resetPane;

    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    private String generatedCode;

    @FXML
    private void handleSendCode(ActionEvent event) {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            messageLabel.setText("Please enter your email.");
            return;
        }

        Utilisateur user = serviceUtilisateur.findByEmail(email);
        if (user == null) {
            messageLabel.setText("No account exists with that email.");
            return;
        }

        generatedCode = String.format("%06d", new Random().nextInt(999999));
        boolean sent = EmailSender.sendEmail(email, "Password Reset Code", "Your password reset code is: " + generatedCode);
        if (sent) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Code sent to your email.");
            // Hide email pane and show reset pane
            emailPane.setVisible(false);
            emailPane.setManaged(false);
            resetPane.setVisible(true);
            resetPane.setManaged(true);
        } else {
            messageLabel.setText("Failed to send email. Please try again later.");
        }
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String inputCode = codeField.getText().trim();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (inputCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return;
        }
        if (!inputCode.equals(generatedCode)) {
            messageLabel.setText("Invalid code.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }
        String hashedPassword = BCryptPass.hashPass(newPassword);
        boolean updated = serviceUtilisateur.updatePasswordByEmail(emailField.getText().trim(), hashedPassword);
        if (updated) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Password reset successful.");
            try {
                FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Failed to update password.");
        }
    }
}
