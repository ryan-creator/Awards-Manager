package Emails;

import dao.DbDAO;
import dao.StorageInterface;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainEmails {

    public void sendPlainTextEmail(
            final String userName, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        //properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
//        Authenticator auth = new Authenticator() {
//            @Override
//            public PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(userName, password);
//            }
//        };

        Session session = Session.getInstance(properties);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        System.out.println("msg " + msg);
        msg.setFrom(new InternetAddress(userName));
        System.out.println("userName " + userName);
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        System.out.println("Address: " + toAddresses);
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);

    }
    
    private static final StorageInterface dao = new DbDAO();

    //Global Settings:
    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = "user_email";
    String password = "email_pass";

    public String forgotPassword(String userID, String resetLink) {

        // outgoing message information
        String address = dao.getUser(userID).getEmail();
        String subject = "Teaching Awards - Password Reset Link";
        String name = dao.getUser(userID).getFirstName() + dao.getUser(userID).getLastName();
        String message = "Hi " + name + ". You requested a password reset link, please follow this link to reset your password: " + resetLink;

        MainEmails mailer = new MainEmails();
        
        try {
            mailer.sendPlainTextEmail(mailFrom, address,
                    subject, message);
            System.out.println("Email sent.");
        } catch (MessagingException ex) {
            System.out.println("Failed to sent email.");
        }

        return null;
    }
    
    public String updatedApplicationNotification(String userID, String statusChange) {

        // outgoing message information
        String mailTo = dao.getUser(userID).getEmail();
        String subject = "Teaching Awards - Password Reset Link";
        String name = dao.getUser(userID).getFirstName() + dao.getUser(userID).getLastName();
        String message = "message";

        MainEmails mailer = new MainEmails();

        try {
            mailer.sendPlainTextEmail(mailFrom, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }

        return null;
    }

    
}
