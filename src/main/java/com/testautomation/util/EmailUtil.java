package com.testautomation.util;

import java.io.File;
import java.security.Security;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailUtil {


	private static Logger log = Logger.getLogger(EmailUtil.class);

	private EmailUtil() {
	}

	public static void send(String from, String password, String to,
			String subject, String message, String mailhost) throws Exception {
		send(from, password, to, null, subject, message, mailhost);
	}

	public static void send(String from, String password, String to, String cc,
			String subject, String message, String mailhost) throws Exception {

		sendWithAttachment(from, password, to, cc, subject, message, mailhost,
				null);
		
	}
	
	public static void sendWithAttachment(String to, String cc,
			String subject, String message, String attachFilePath) throws Exception{
		TreeSet<String> attachFilePathList = new TreeSet<String>();
		if(attachFilePath != null && attachFilePath.length()> 0) {
			attachFilePathList.add(attachFilePath);
		}
		
		sendWithAttachment("vdsphase2@gmail.com", "VDS5LITE", 
				to, cc, subject, message, "smtp.gmail.com", attachFilePathList);
	}
	

	public static void sendWithAttachment(String from, String password, String to, String cc,
			String subject, String message,String mailhost, TreeSet<String> attacheFilePathSet) throws Exception{

		try {
			Session session = null;
			// check for mandatory fields
			if (from == null) {
				throw new Exception(
						"Parameter from in method send in class EmailUtil can't be null");
			}
			
			if (to == null) {
				throw new Exception(
						"Parameter to in method send in class EmailUtil can't be null");
			}

			if (subject == null) {
				throw new Exception(
						"Parameter subject in method send in class EmailUtil can't be null");
			}

			if (message == null) {
				throw new Exception(
						"Parameter message in method send in class EmailUtil can't be null");
			}

			if (session == null) {
				Properties p = new Properties();
								
				p.put("mail.host", mailhost);
				//For Tspl Environment we need to enable this and for Client Environment we need to disaple this - Start
				//if(ApplicationConstants.MAIL_SMTP==null || ApplicationConstants.MAIL_SMTP.equalsIgnoreCase("smtp.gmail.com")){
				p.put("mail.smtp.auth", "true");
				p.put("mail.smtp.port", "465");
				p.put("mail.smtp.starttls.enable", "true");
				p.put("mail.smtp.socketFactory.port",  "465");
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				p.put("mail.smtp.socketFactory.fallback", "false");
				p.put("ssl.SocketFactory.provider", "com.ibm.jsse2.SSLSocketFactoryImpl");
				p.put("ssl.ServerSocketFactory.provider", "com.ibm.jsse2.SSLServerSocketFactoryImpl");
				/*
				 * }else{ Security.setProperty("ssl.SocketFactory.provider",
				 * "com.ibm.jsse2.SSLSocketFactoryImpl");
				 * Security.setProperty("ssl.ServerSocketFactory.provider",
				 * "com.ibm.jsse2.SSLServerSocketFactoryImpl"); }
				 */
				//For Tspl Environment we need to enable this and for Client Environment we need to disaple this - End				
				Authenticator auth = new SMTPAuthenticator(from, password);

				session = Session.getInstance(p, auth);
				session.setDebug(false);
			}

			Transport bus = session.getTransport("smtp");
			bus.connect();
			
			MimeMessage msg = new MimeMessage(session);

			
			// set from address
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);

			String toReceipArray[] = convertStringToArray(to);
			Address toAddr[] = new InternetAddress[toReceipArray.length];
			for (int i = 0; i < toReceipArray.length; i++) {
				// set to address
				toAddr[i] = new InternetAddress(toReceipArray[i]);
			}
			msg.addRecipients(Message.RecipientType.TO, toAddr);

			// set cc if its passed in
			if (cc != null) {
				String ccReceipArray[] = convertStringToArray(cc);
				Address ccAddr[] = new InternetAddress[ccReceipArray.length];
				for (int i = 0; i < ccReceipArray.length; i++) {
					// set to address
					ccAddr[i] = new InternetAddress(ccReceipArray[i]);
				}
				msg.addRecipients(Message.RecipientType.CC, ccAddr);
			}
			
			msg.setSubject(subject);
					Multipart multipart = new MimeMultipart();
			
			MimeBodyPart textOrHTMLPart = new MimeBodyPart();
			textOrHTMLPart.setContent(message, "text/html; charset=utf-8");
			multipart.addBodyPart(textOrHTMLPart);
			// Preparing attachment
			if(attacheFilePathSet != null && !attacheFilePathSet.isEmpty()){
				addAttachement(multipart, attacheFilePathSet);
				attacheFilePathSet.clear();
				log.info("attached path "+attacheFilePathSet);
			}
			msg.setContent(multipart);
			msg.saveChanges();
			log.info("Mail sent successfully to :: "+to);
			//Transport.send(msg);
			bus.send(msg);
			bus.close();
		} catch (Exception e) {
			// Don't use the Application logger for catch this exception ,
			// because exception email send repeatedly when problem occurred in			// mail server
			log.error("Error in mail sending [EmailUtil.sendWithAttachment]"+e);
			//Logger.getLogger(EmailUtil.class.getName()).log(null, e);
			throw new Exception(e);
		}
	
	}

	 public static String[] convertStringToArray(String aString) {

	        String[] splittArray = null;
	        if (aString != null && !aString.equalsIgnoreCase("")) {
	            splittArray = aString.split(",");	            
	        } else {
	            splittArray = new String[1];
	            splittArray[0] = "";
	        }
	        return splittArray;
	    }
	 
	private static Multipart addAttachement(Multipart multipart, TreeSet<String> filePathSet)
			throws MessagingException {
		// Part two is attachment

		MimeBodyPart attachFilePart = null;
		DataSource source = null;
		for (String filePath : filePathSet) {
			source = new FileDataSource(filePath);
			attachFilePart = new MimeBodyPart();
			attachFilePart.setDataHandler(new DataHandler(source));
			attachFilePart.setFileName(source.getName());
			multipart.addBodyPart(attachFilePart);
		}

		return multipart;
	}
	
	public static boolean deleteFile(String filePath){
		File file = new File (filePath);
		return file.delete();

	}
						
	/*
	 * public static void main(String args[]) throws Exception { }
	 */
}

class SMTPAuthenticator extends javax.mail.Authenticator {

	String userName;
	String password;

	SMTPAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(userName, password);
	}

}