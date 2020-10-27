package com.easyfix.util;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;





import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class MailSender {
	
	static Logger log = LogManager.getLogger(MailSender.class);
	
//	static boolean sendMail = false;
	static boolean sendMail = true;
	public static void email(String recipient, String content, String subject, PropertyReader props, String filePath,String[] cc,String bcc) {
		
		if(sendMail) {
		
        String smtpHost = props.getValue("SMTP.HOST.NAME"); 
        int smtpPort = Integer.parseInt(props.getValue("SMTP.HOST.PORT"));  
        
        String sender = props.getValue("sender.email"); 
        //sender="hello@easyfix.in";
        Properties mailProps = new Properties();
          
        
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
		mailProps.put("mail.smtp.port", "587");
		mailProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        String user=props.getValue("SMTP.AUTH.USER");
        String pwd= props.getValue("SMTP.AUTH.PWD");
        Session mailSession = Session.getInstance(mailProps, new SMTPAuthenticator(user,pwd));
         
        try {           
           
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
            textBodyPart.setContent(content, "text/html");
            
          //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            if(filePath!=null){
            FileDataSource fds = new FileDataSource(filePath);
            MimeBodyPart excelBodyPart = new MimeBodyPart();
            excelBodyPart.setDataHandler(new DataHandler(fds));
            excelBodyPart.setFileName("Estimate_Approval.pdf");
            mimeMultipart.addBodyPart(excelBodyPart);
            }
            
            
            mimeMultipart.addBodyPart(textBodyPart);
          
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
            InternetAddress[] ccList=null;
            
            if(cc!=null && cc.length>0){
             ccList = new InternetAddress[cc.length] ;
            for(int i=0;i<cc.length;i++){
            	InternetAddress iaCc = new InternetAddress(cc[i]);
            		ccList[i]=iaCc;
            }
            }
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(iaSender);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.addRecipients(Message.RecipientType.CC, ccList);
            mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport t = mailSession.getTransport("smtp");
            
            log.info(" gmail transporter connected status: "+t.isConnected());
            if(!t.isConnected()){
            	
            	t.connect(smtpHost, smtpPort, user, pwd);
            }
            t.send(mimeMessage);
             
            log.info("sent from " + sender + 
                    ", to " + recipient + 
                    "; server = " + smtpHost + ", port = " + smtpPort);         
        } catch(Exception ex) {
        	log.info("Error sending email to :" +recipient);
        	log.error(ex.getMessage());
            ex.printStackTrace();
        }
		}
    }
   
	
	
	public static void email(String recipient, String content, String subject, PropertyReader props, String filePath,String[] cc) {
		
		if(sendMail) {
		
        String smtpHost = props.getValue("SMTP.HOST.NAME"); 
        int smtpPort = Integer.parseInt(props.getValue("SMTP.HOST.PORT"));  
        
        String sender = props.getValue("sender.email"); 
        //sender="hello@easyfix.in";
        Properties mailProps = new Properties();
          
        
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
		mailProps.put("mail.smtp.port", "587");
		mailProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        String user=props.getValue("SMTP.AUTH.USER");
        String pwd= props.getValue("SMTP.AUTH.PWD");
        Session mailSession = Session.getInstance(mailProps, new SMTPAuthenticator(user,pwd));
         
        try {           
           
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
            textBodyPart.setContent(content, "text/html");
            
          //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            if(filePath!=null){
            FileDataSource fds = new FileDataSource(filePath);
            MimeBodyPart excelBodyPart = new MimeBodyPart();
            excelBodyPart.setDataHandler(new DataHandler(fds));
            excelBodyPart.setFileName("Invoice Approval test mail.pdf");
            mimeMultipart.addBodyPart(excelBodyPart);
            }
            
            
            mimeMultipart.addBodyPart(textBodyPart);
          
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
            InternetAddress[] ccList=null;
            
            if(cc!=null && cc.length>0){
             ccList = new InternetAddress[cc.length] ;
            for(int i=0;i<cc.length;i++){
            	InternetAddress iaCc = new InternetAddress(cc[i]);
            		ccList[i]=iaCc;
            }
            }
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(iaSender);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.addRecipients(Message.RecipientType.CC, ccList);
            mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress("it-admin@easyfix.in"));
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport t = mailSession.getTransport("smtp");
            
            log.info(" gmail transporter connected status: "+t.isConnected());
            if(!t.isConnected()){
            	
            	t.connect(smtpHost, smtpPort, user, pwd);
            }
            t.send(mimeMessage);
             
            log.info("sent from " + sender + 
                    ", to " + recipient + 
                    "; server = " + smtpHost + ", port = " + smtpPort);         
        } catch(Exception ex) {
        	log.info("Error sending email to :" +recipient);
        	log.error(ex.getMessage());
            ex.printStackTrace();
        }
		}
    }
   
	
	public static void email(String recipient, String content, String subject, PropertyReader props, String filePath) {
		
		if(sendMail) {
		
        String smtpHost = props.getValue("SMTP.HOST.NAME"); 
        int smtpPort = Integer.parseInt(props.getValue("SMTP.HOST.PORT"));  
        
        String sender = props.getValue("sender.email");
      //  System.out.println(sender+"-----------------------");
        //sender="hello@easyfix.in";
        Properties mailProps = new Properties();
          
        
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
		mailProps.put("mail.smtp.port", "587");
		mailProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        String user=props.getValue("SMTP.AUTH.USER");
        String pwd= props.getValue("SMTP.AUTH.PWD");
        Session mailSession = Session.getInstance(mailProps, new SMTPAuthenticator(user,pwd));
         
        try {           
           
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
            textBodyPart.setContent(content, "text/html");
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            if(filePath!=null){
            		FileDataSource fds = new FileDataSource(filePath);
            		MimeBodyPart excelBodyPart = new MimeBodyPart();
            		excelBodyPart.setDataHandler(new DataHandler(fds));
            		excelBodyPart.setFileName("Invoice Approval  test mail.pdf");
            		mimeMultipart.addBodyPart(excelBodyPart);
            }
            
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(iaSender);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
      
            Transport t = mailSession.getTransport("smtp");
            t.connect(smtpHost, smtpPort, user, pwd);
            t.send(mimeMessage);
             
            log.info("sent from " + sender + 
                    ", to " + recipient + 
                    "; server = " + smtpHost + ", port = " + smtpPort);         
        } catch(Exception ex) {
        	System.out.println("Error sending email to :" +recipient);
            ex.printStackTrace();
        }
		}
    }
	public static void main (String[] arg) throws IOException{
		MailSender.email("amit.swaroop@channelplay.in", "hi", "hi", new PropertyReader(),"path");
	}
   
}
