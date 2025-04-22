package co.edu.uniquindio.seci_proyect.services.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface EmailService {
    void sendActivationEmail(String to, String activationCode);
    void sendPasswordResetEmail(String to, String resetCode);
    void sendReportNotificationEmail(String to, String reportTitle, String comment);

    void sendCommentNotification(String string, @NotBlank String title, @NotBlank @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres") String content);
}