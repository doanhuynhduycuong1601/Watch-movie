package Utils;

import java.net.http.HttpRequest;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class SendMail {
	private static String email = "doanhuynhduycuong16011601@gmail.com";
    private static String password = "ghhnobgajhmiathp";
	private static Session truyCap() {

	    String host = "smtp.gmail.com";
	    String port = "587";

	    Properties props = new Properties();
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
	    return session;
	}
	
	public static void sendCodeRegister(String to, String subject, String body) {
		Session session = truyCap();
		Message msg = new MimeMessage(session);
        try {
			msg.setFrom(new InternetAddress(email));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	        msg.setSubject(subject);
	        msg.setText(body);

	        Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		      
	}
	    	  
}

