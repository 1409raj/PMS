package com.sampark.PMS.util;

import java.util.Properties;
import javax.mail.BodyPart;
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

import com.sampark.PMS.PMSConstants;

public class ResetPasswordMail {
	
	public static void send(String to,String url){
		Properties props = new Properties();  
		 props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.host", "smtp.office365.com");
         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.starttls.enable", "true");
		   
		   Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {  
	 
			   protected PasswordAuthentication getPasswordAuthentication(){
				   return new PasswordAuthentication(PMSConstants.SEND_FORM, PMSConstants.SEND_FORM_PASSWORD);		   
			   }	   
		  }); 	   
		 try { 
			 
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(PMSConstants.SEND_FORM));
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		   message.setSubject("Verify One Time Password");  

		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setContent("<a href="+url+"><h3 style='background-color:blue; color:white; padding:25px; text-align:center;'> Click Here For Password Reset</h3></a>","text/html");
			
//		    BodyPart messageBodyPart2 = new MimeBodyPart();  
//		    messageBodyPart2.setText("Your account password is: "+generatepassword);

//		    BodyPart messageBodyPart3 = new MimeBodyPart();  
//		    messageBodyPart3.setContent("<p style='padding-bottom:5px;'>Sincerely,<br>Ankit </p><div style='border-top:1px solid gray; padding:5px 30px;'><span style='float:left;'><small>The message was sent from an unmonitored email address.<br>Please do not reply to this message</small></span><span  style='float:right; color:maroon;'><strong>Ankit kumar Gupta</strong></span></div>","text/html");

		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
//		    multipart.addBodyPart(messageBodyPart2);  
//		    multipart.addBodyPart(messageBodyPart3);
		 
		    message.setContent(multipart);   
		   Transport.send(message);  
		   System.out.println("Successfully"); 
		  }
		  catch (MessagingException e){ 
			  e.printStackTrace();
		      throw new RuntimeException(e);
		      
		  }   
		 }	
}
