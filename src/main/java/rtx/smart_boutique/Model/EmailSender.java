package rtx.smart_boutique.Model;


import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Session;

public class EmailSender {

        public static void sendOTPEmail(String toEmail, String username, String otp) {
            final String fromEmail = "ngarikelvin1015@gmail.com";
            final String password = "birw ksvv ohai zpmi";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(fromEmail, password);
                }
            });


            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(fromEmail, "Support Team"));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                msg.setSubject("Your OTP Code");
                msg.setText("Hi " + username + ",\n\nYour OTP code is: " + otp + "\n\nIt will expire in 5 minutes.");

                Transport.send(msg);
                System.out.println("OTP sent to " + toEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
