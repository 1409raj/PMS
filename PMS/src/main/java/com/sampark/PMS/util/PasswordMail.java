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
import org.springframework.scheduling.annotation.Async;
import com.sampark.PMS.PMSConstants;
import com.sampark.PMS.dto.EmployeeEmails;

public class PasswordMail {
	

	@Async
	public static Boolean send(EmployeeEmails object) {
		Boolean check = false;
		Properties props = new Properties();
		 props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.host", "smtp.office365.com");
         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.starttls.enable", "true");

		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PMSConstants.SEND_FORM, PMSConstants.SEND_FORM_PASSWORD);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PMSConstants.SEND_FORM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(object.getSendTo()));
			BodyPart messageBodyPart1 = new MimeBodyPart();
//			BodyPart messageBodyPart2 = new MimeBodyPart();
//			BodyPart messageBodyPart3 = new MimeBodyPart();
//			BodyPart messageBodyPart4 = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			message.setSubject(object.getEmailSubject());
			messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//			if (object.getEmailType().equals(PMSConstants.NEW_EMPLOYEE)) {
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			}
//			else if (object.getEmailType().equals(PMSConstants.EMPLOYEE_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi " + object.getEmployeeName() + ",</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(messages.getString("email.employee.appraisal.approval.text"));
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			}
//			else if (object.getEmailType().equals(PMSConstants.MANAGER_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(
////						object.getEmployeeName() + " " + messages.getString("email.manager.appraisal.approval.text"));
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			} 
//			else if (object.getEmailType().equals(PMSConstants.EMPLOYEE_APPRAISAL_REJECT)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi " + object.getEmployeeName() + ",</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(messages.getString("email.appraisal.reject"));
////				messageBodyPart3.setContent(messages.getString("email.new.employee.content"), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent("Dear "+object.getEmployeeName() + "<br><br>"
//						+ "Your appraisal has been rejected, please log into the PMS system to complete this action." + "<br><br>" 
//						+ "URL <a href='http://95.216.153.174:8080/PMS/login'>Click here to login PMS tool</a>"
//						+ "<br><br>" + "Best Regards" + "<br>" + "Team HR", "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			}
//
//			else if (object.getEmailType().equals(PMSConstants.MID_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2
////						.setText(object.getEmployeeName() + " " + messages.getString("email.manager.appraisal.reject"));
////				messageBodyPart3.setContent(messages.getString("email.new.employee.content"), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent("Dear Manager"+ "<br><br>"
//						+ "Your team member – "+ object.getEmployeeName()+" appraisal has been rejected, please log into the PMS system to complete this action." + "<br><br>" 
//						+ "URL <a href='http://95.216.153.174:8080/PMS/login'>Click here to login PMS tool</a>"
//						+ "<br><br>" + "Best Regards" + "<br>" + "Team HR", "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//				
//			}
//
//			else if (object.getEmailType().equals(PMSConstants.FINAL_FIRST_LEVEL_MANAGER_APPRAISAL_REJECTED)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2
////						.setText(object.getEmployeeName() + " " + messages.getString("email.manager.appraisal.reject"));
////				messageBodyPart3.setContent(messages.getString("email.new.employee.content"), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent("Dear Manager"+ "<br><br>"
//						+ "Your team member – "+ object.getEmployeeName()+" appraisal has been rejected, please log into the PMS system to complete this action." + "<br><br>" 
//						+ "URL <a href='http://95.216.153.174:8080/PMS/login'>Click here to login PMS tool</a>"
//						+ "<br><br>" + "Best Regards" + "<br>" + "Team HR", "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//				
//			}
//
//			else if (object.getEmailType().equals(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_REJECTED)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2
////						.setText(object.getEmployeeName() + " " + messages.getString("email.manager.appraisal.reject"));
////				messageBodyPart3.setContent(messages.getString("email.new.employee.content"), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent("Dear Manager" + "<br><br>"
//						+ "Your team member – "+ object.getEmployeeName()+" appraisal has been rejected, please log into the PMS system to complete this action." + "<br><br>" 
//						+ "URL <a href='http://95.216.153.174:8080/PMS/login'>Click here to login PMS tool</a>"
//						+ "<br><br>" + "Best Regards" + "<br>" + "Team HR", "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			}
//
//			 else if (object.getEmailType().equals(PMSConstants.FIRST_LEVEL_MANAGER_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(object.getEmployeeName() + " "
////						+ messages.getString("email.midFirstLevel.appraisal.approval.text"));
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//				
//			} else if (object.getEmailType().equals(PMSConstants.SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(
////						object.getEmployeeName() + " " + messages.getString("email.manager.appraisal.approval.text"));
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			} else if (object.getEmailType().equals(PMSConstants.FINAL_EMPLOYEE_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi " + object.getEmployeeName() + ",</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(messages.getString("email.final.appraisal.approval.text"));
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent("Dear " + object.getEmployeeName() + "<br><br>"
//						+ "Your goals are approved by manager." + "<br><br>" 
//						+ "URL <a href='http://95.216.153.174:8080/PMS/login'>Click here to login PMS tool</a>"
//						+ "<br><br>" + "Best Regards" + "<br>" + "Team HR", "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//				
//			} else if (object.getEmailType().equals(PMSConstants.FINAL_SECOND_LEVEL_MANAGER_APPRAISAL_APPROVAL)) {
////				message.setSubject(object.getEmailSubject());
////				messageBodyPart1.setContent("<p>Hi,</p> <br>", "TEXT/HTML");
////				messageBodyPart2.setText(messages.getString("email.final.manager.appraisal.approval.text") + " "
////						+ object.getEmployeeName());
////				messageBodyPart3.setContent(object.getEmailContent(), "text/html");
////				messageBodyPart4.setContent(
////						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
////						"TEXT/HTML");
////				multipart.addBodyPart(messageBodyPart1);
////				multipart.addBodyPart(messageBodyPart2);
////				multipart.addBodyPart(messageBodyPart3);
////				multipart.addBodyPart(messageBodyPart4);
//				
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//			} else {
//				message.setSubject(object.getEmailSubject());
//				messageBodyPart1.setContent(object.getEmailContent(), "TEXT/HTML");
//				messageBodyPart2.setContent(
//						"<img border='0' width='390' height='168' style='width: 4.0625in; height: 1.75in' src='https://images.freeimages.com/images/large-previews/a93/butterfly-1390143.jpg'>",
//						"TEXT/HTML");
//				multipart.addBodyPart(messageBodyPart1);
//				multipart.addBodyPart(messageBodyPart2);
//			}
			multipart.addBodyPart(messageBodyPart1);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Successfully");
			check = true;
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return check;
	}
}
