package org.wso2.custom.handler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "asgardeotest3@gmail.com";
    private static final String SMTP_PASSWORD = "urbdnetekpwmznxf";

    public static void sendEmail(List<String> recipients, String newUser, String tenantDomain) {
        if (recipients == null || recipients.isEmpty()) {
            System.out.println("No admin emails found to send notifications.");
            return;
        }

        String subject = "New User Registration Alert";
        String messageBody = "User " + newUser + " has been added to tenant: " + tenantDomain;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));

            // Add multiple recipients
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            System.out.println("Email sent successfully to Admins.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
