package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendActivationEmail(String to, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Activa tu cuenta en SecL");
        message.setText("Tu código de activación es: " + activationCode +
                "\nEste código expirará en 15 minutos.");
        mailSender.send(message);
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String to, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Restablece tu contraseña en SecL");
        message.setText("Tu código para restablecer la contraseña es: " + resetCode +
                "\nEste código expirará en 15 minutos.");
        mailSender.send(message);
    }

    @Override
    @Async
    public void sendCommentNotification(String to, String reportTitle, String comment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Nuevo comentario en tu reporte: " + reportTitle);
        message.setText("Has recibido un nuevo comentario en tu reporte '" + reportTitle + "':\n\n" +
                comment + "\n\nIngresa a la plataforma para ver más detalles.");
        mailSender.send(message);
    }

    @Override
    @Async
    public void sendReportStatusChangeNotification(String to, String reportTitle, String newStatus, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Cambio de estado en tu reporte: " + reportTitle);
        message.setText("El estado de tu reporte '" + reportTitle + "' ha cambiado a: " + newStatus +
                (reason != null ? "\nRazón: " + reason : "") +
                "\n\nIngresa a la plataforma para ver más detalles.");
        mailSender.send(message);
    }
}