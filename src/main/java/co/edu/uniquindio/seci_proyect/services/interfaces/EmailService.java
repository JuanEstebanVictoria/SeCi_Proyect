package co.edu.uniquindio.seci_proyect.services.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface EmailService {
    void sendActivationEmail(String to, String activationCode);
    void sendPasswordResetEmail(String to, String resetCode);
    void sendReportNotificationEmail(String to, String reportTitle, String comment);
    void sendReportStatusChangeNotification(String to, String reportTitle, String newStatus, String reason);
    void sendCommentNotification(String string, @NotBlank String title, @NotBlank @Size(max = 500, message = "Comment can be longer than 500 characters") String content);

    void sendEmail(String email, String s, String s1);
}