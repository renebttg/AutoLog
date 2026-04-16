package com.example.autolog.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Rene
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String to, String resetUrl) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Password Reset Request");

        String htmlContent = "<!DOCTYPE html>" +
                "<html>" +
                "<body style=\"font-family: Arial, sans-serif;\">" +
                "<div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd;\">" +
                "<h2 style=\"color: #333; text-align: center;\">Redefinir sua senha</h2>" +
                "<p style=\"color: #555; font-size: 16px;\">Olá,</p>" +
                "<p style=\"color: #555; font-size: 16px;\">" +
                "Recebemos uma solicitação para redefinir sua senha. " +
                "Se você não fez essa solicitação, por favor, ignore este e-mail. Caso contrário, clique no botão abaixo para redefinir sua senha:" +
                "</p>" +
                "<div style=\"text-align: center; margin: 20px 0;\">" +
                "<a href=\"" + resetUrl + "\" style=\"display: inline-block; padding: 12px 20px; background-color: #007bff; color: #ffffff; text-decoration: none; border-radius: 5px; font-size: 18px;\">Resetar Senha</a>" +
                "</div>" +
                "<p style=\"color: #555; font-size: 14px;\">Se o botão não funcionar, copie e cole o link abaixo em seu navegador:</p>" +
                "<p style=\"color: #007bff; font-size: 14px;\">" + resetUrl + "</p>" +
                "<br><br>" +
                "<p style=\"text-align: center;\">" +
                "</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
    }



