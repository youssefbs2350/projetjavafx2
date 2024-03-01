package authentificationetajoututilisateur;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email{

    public static void envoyerMailNouvelUtilisateur() {
        // Adresse email destinataire
        String to = "youssefbs11@gmail.com";

        // Adresse email expéditeur
        final String from = "yoas@gmail.com";

        // Mot de passe de l'adresse email expéditeur
        final String password = "azerazer";

        // Propriétés SMTP pour Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Session de messagerie
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Nouvel utilisateur créé");
            message.setText("Un nouvel utilisateur a été créé.");

            // Envoi du message
            Transport.send(message);

            System.out.println("Le message a été envoyé avec succès à " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Email email = new Email();
        email.envoyerMailNouvelUtilisateur();
    }
}
