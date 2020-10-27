package com.easyfix.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.Jobs.dao.JobDaoImpl;
import com.easyfix.util.triggers.model.MailMessage;

public class emailSender {
	private static final Logger logger = LogManager.getLogger(emailSender.class);
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	// TODO FOR PRE-PROD
	//	private static final String SMTP_HOST_NAME = "asdsmtp.dfsendgrid.ddfghnet";
	private static final int SMTP_HOST_PORT = 2525;
	private static final String SMTP_AUTH_USER = "lalit1viewtech";
//	private static final String SMTP_AUTH_PWD  = "sffaf@!sghak738839&*()^";
	private static final String SMTP_AUTH_PWD  = "Phs2ERh@&!*Ghsla2kd7";
	public  void email(String to, String from,String sub, String mail) {
		
        String smtpHost = "smtp.sendgrid.net"; //replace this with a valid host
        int smtpPort = 2525; //replace this with a valid port
                 
        String sender = from;//"kundan.kumar2@channelplay.in"; //replace this with a valid sender email address
        String recipient = to;//"kundan.kumar2@channelplay.in"; //replace this with a valid recipient email address
        String content = mail;//this will be the text of the email
        String subject = sub; //this will be the subject of the email
         
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);     
        Session session = Session.getInstance(properties, null);
         
    
         
        try {           
           
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
           // textBodyPart.setText(content);
            htmlBodyPart.setContent(content, "text/html");
      //      FileDataSource fds = new FileDataSource("C:/kpi.xlsx");
      //      MimeBodyPart excelBodyPart = new MimeBodyPart();
      //      excelBodyPart.setDataHandler(new DataHandler(fds));
      //     excelBodyPart.setFileName("test.xlsx");
                         
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(htmlBodyPart);
      //      mimeMultipart.addBodyPart(excelBodyPart);
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport.send(mimeMessage);
             
            System.out.println("sent from " + sender + 
                    ", to " + recipient + 
                    "; server = " + smtpHost + ", port = " + smtpPort);         
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
 
	public static void sendMail(MailMessage mailMessage) throws Exception {
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.starttls.enable", "true");
		mailProps.put("mail.smtp.host", SMTP_HOST_NAME); 
		mailProps.put("mail.smtp.auth", "true");
		mailProps.put("mail.smtp.port", SMTP_HOST_PORT);

		//Added by Lalit (For Sendgrid) - Start
		mailProps.put("mail.transport.protocol", "smtp");
		mailProps.put("mail.smtp.auth", "true");
		//Added by Lalit (For Sendgrid) - End
		
//		mailProps.put("mail.debug", "true");

		Session mailSession = Session.getInstance(mailProps, new SMTPAuthenticator(SMTP_AUTH_USER, SMTP_AUTH_PWD));

		Multipart multipart = new MimeMultipart();
		MimeBodyPart mbpText = new MimeBodyPart();
		mbpText.setText(mailMessage.getTextMessage());
		mbpText.setContent(mailMessage.getTextMessage(), "text/html");
		multipart.addBodyPart(mbpText);

		Message msg = new MimeMessage(mailSession);
		//Modified by Lalit (For SendGrid) - Start
		//InternetAddress intAdd = new InternetAddress(SMTP_AUTH_USER); 
		//InternetAddress intAdd = new InternetAddress("channeledge@channelplay.in");		
		InternetAddress intAdd = new InternetAddress("support@1viewtech.com");
		//Modified by Lalit (For SendGrid) - End
		intAdd.setPersonal(mailMessage.getSender());
		msg.setFrom(intAdd);
		msg.setReplyTo(new InternetAddress[]{new InternetAddress(mailMessage.getSender())});
		msg.setSubject(mailMessage.getSubject());
		msg.setContent(multipart);
		msg.setSentDate(new Date());
		String[] arrTo = null;
		InternetAddress[] addressTo = null;
		if(mailMessage.getRecipientTo() != null && !mailMessage.getRecipientTo().isEmpty()) {
			if(mailMessage.getRecipientTo().contains(";")) {
				arrTo = mailMessage.getRecipientTo().split(";");
				addressTo = new InternetAddress[arrTo.length];
				for(int i=0; i < arrTo.length; i++) {
					addressTo[i] = new InternetAddress(arrTo[i].trim());
				}
				msg.addRecipients(Message.RecipientType.TO, addressTo);
			} else {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.getRecipientTo().trim()));
			}
		}
		if(mailMessage.getRecipientCC()!=null && !mailMessage.getRecipientCC().isEmpty() ) {
			if(mailMessage.getRecipientCC().contains(";")) {
				arrTo = mailMessage.getRecipientCC().split(";");
				addressTo = new InternetAddress[arrTo.length];
				for(int i=0; i < arrTo.length; i++) {
					addressTo[i] = new InternetAddress(arrTo[i].trim());
				}
				msg.addRecipients(Message.RecipientType.CC, addressTo);
			} else {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(mailMessage.getRecipientCC().trim()));
			}
		}
		if(mailMessage.getRecipientBCC() != null && !mailMessage.getRecipientBCC().isEmpty()) {
			if(mailMessage.getRecipientBCC().contains(";")) {
				arrTo = mailMessage.getRecipientBCC().split(";");
				addressTo = new InternetAddress[arrTo.length];
				for(int i=0; i < arrTo.length; i++) {
					addressTo[i] = new InternetAddress(arrTo[i].trim());
				}
				msg.addRecipients(Message.RecipientType.BCC, addressTo);
			} else {
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(mailMessage.getRecipientBCC().trim()));
			}
		}

		if(mailMessage.getFileAttachment()!=null) {
			for(int i = 0; i < mailMessage.getFileAttachment().length ; i++) {
				MimeBodyPart mbpAttachment = new MimeBodyPart();
				DataSource source = new FileDataSource(mailMessage.getFileAttachment()[i]);
				mbpAttachment.setHeader("Content-Type", mailMessage.getFileAttachmentContentType()[i]);
				mbpAttachment.setDataHandler(new DataHandler(source));
				mbpAttachment.setFileName(mailMessage.getFileAttachmentFileName()[i]);
				multipart.addBodyPart(mbpAttachment);
			}
		}
		try{
			//Modified by Lalit (For SendGrid) - Start
			Transport t = mailSession.getTransport("smtp");
			t.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			t.sendMessage(msg, msg.getAllRecipients());
			logger.info(mailMessage.getSubject());
            logger.info(mailMessage.getTextMessage());
            logger.info("To:"+mailMessage.getRecipientTo());
            logger.info("CC:"+mailMessage.getRecipientCC());
            logger.info("-----------------------------------------------");
			t.close();
			//Transport.send(msg);
			//Modified by Lalit (For SendGrid) - End
		}catch(Exception e){e.printStackTrace();
		logger.info(mailMessage.getSubject());
        logger.info(mailMessage.getTextMessage());
        logger.info("To:"+mailMessage.getRecipientTo());
        logger.info("CC:"+mailMessage.getRecipientCC());
        logger.info("-----------------------------------------------");
		}
	}

    public static void main(String[] args) throws Exception {
    	emailSender demo = new emailSender();
        demo.email("kundan.kumar2@channelplay.in","kundan.kumar2@channelplay.in","hi","test");
        MailMessage m = new MailMessage();
        m.setSubject("test");
        m.setRecipientTo("kundan.kumar@easyfix.in");
        m.setTextMessage("test");
        demo.sendMail(m);
    }
}
