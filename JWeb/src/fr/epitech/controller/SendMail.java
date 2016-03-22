package fr.epitech.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet handling the mail sending
 * @author acca_b
 *
 */
public class SendMail {    
    public static void send(String dest, String title, String description) {
    	final String username = "jwebepibaby@gmail.com";
        final String password = "lemotdepasse";
    	
	    Properties props = new Properties();
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	
	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	      });
	
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(username));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(dest));
	        message.setSubject("News : " + title);
	        message.setText(title + "\n\n" + description);
	
	        Transport.send(message);

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
    }
}
