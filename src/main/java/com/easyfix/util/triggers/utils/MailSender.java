package com.easyfix.util.triggers.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.model.emailContentForPostJob;




public class MailSender {
	
	
	static Logger log = LogManager.getLogger(MailSender.class);
	public static void main (String[] arg){
		try {
			List<emailContentForPostJob> contentList = MailSender.ReadEmailForjobCreation();
			System.out.println(contentList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<emailContentForPostJob> ReadEmailForjobCreation() throws Exception{
		 
		List<emailContentForPostJob> contentList = new ArrayList<emailContentForPostJob>();
		String receivingHost="imap.gmail.com";//for imap protocol
		 
	        Properties props2=System.getProperties();
	 
	        props2.setProperty("mail.store.protocol", "imaps");
	        // I used imaps protocol here
	 
	        Session session2=Session.getDefaultInstance(props2, null);
	 
	            try {
	 
	                    Store store=session2.getStore("imaps");
	 
	                    store.connect(receivingHost,"solutions@easyfix.in", "ukhxczononsoptup");
	 
	                    Folder folder=store.getFolder("INBOX");//get inbox
	 
	                    folder.open(Folder.READ_ONLY);//open folder only to read
	 
	                  //  Message message[]=folder.getMessages();
	                    Calendar cal = Calendar.getInstance();
	                    cal.setTime(new Date());
	                    Date now = cal.getTime();
	                    System.out.println(now);
	                    cal.setTime(new Date(cal.getTimeInMillis()-600000));
	                    Date previousDate = cal.getTime();
	                    System.out.println(previousDate);
	                    SearchTerm olderThan = new ReceivedDateTerm(ComparisonTerm.LE, now);
	                    SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, previousDate);
	                    SearchTerm andTerm = new AndTerm(olderThan, newerThan);
	                    Message message[] = folder.search(andTerm);
	                    System.out.println(message.length);
	                    for(int i=0;i<message.length;i++){
	                    	try{
	                    		Address[] fromadd = message[i].getFrom();
	                    		InternetAddress a = (InternetAddress) fromadd[0];
		                    	String from = a.getAddress().trim();
		                    	String subj = message[i].getSubject();
		                    	int emailId = message[i].getMessageNumber();
		                    	if(!from.equalsIgnoreCase("info@emails.quikr.com")){
		                    		System.out.println("id:"+message[i].getMessageNumber()+"skipped as from "+from);
		                    		continue;
		                    	}
		                    	if(!subj.contains("Confirmed: Quikr Helpr Booking ID")){
		                    		System.out.println("id:"+message[i].getMessageNumber()+"skipped as sub "+subj);
		                    		continue;
		                    	}
		                    	
	                        //print subjects of all mails in the inbox
	                    	
	                    	
	                    	
	                    	//System.out.println(from);
	                    	Multipart m =  (Multipart) message[i].getContent();
	                    	System.out.println(m.getCount());
	                    	for (int j = 0; j < m.getCount(); j++) {

	                            BodyPart bodyPart = m.getBodyPart(j);
	                            if(!bodyPart.getContent().toString().contains("Phone Number")){
	                            	System.out.println("bodyPart skipped as does not conatin phone number");
	                            	continue;
	                            }
	                            System.out.println(m.getContentType());
	                            String disposition = bodyPart.getDisposition();
	                            System.out.println("disposition::"+disposition);
	                              if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) { // BodyPart.ATTACHMENT doesn't work for gmail
	                                  System.out.println("Mail have some attachment");

	                                  DataHandler handler = bodyPart.getDataHandler();
	                                  System.out.println("file name : " + handler.getName());                                 
	                                }
	                              else { 
	                            	  System.out.println("subj:"+subj);
	                            	 
	                            	  System.out.println("emailId:"+emailId);
	                            	  System.out.println("from:"+from);
	                                  System.out.println("Body: "+bodyPart.getContent());
	                            	  
	                                  String content= bodyPart.getContent().toString();
	                                  emailContentForPostJob job = new emailContentForPostJob();
	                                  job.setEmailContent(content);
	                                  job.setFrom(from);
	                                  job.setMessageId(emailId);
	                                  job.setSubject(subj);
	                                  contentList.add(job);
	                                  
	                                }
	 
	                      //  System.out.println(message[i].getSubject()+"::"+message[i].getMessageNumber()+":"+message[i].getFrom().toString()+":"+message[i].getContent().toString());
	 
	                        //anything else you want
	 
	                    }
	                    	}
	                    	catch(Exception e){
	                    		e.printStackTrace();
	                    	}
	 
	                    
	 
	            }
	                  //close connections
	               	 
	                    folder.close(true);
	 
	                    store.close();
	            }catch (Exception e) {
	 
	                    System.out.println(e.toString());
	 
	            }
	            return contentList;
	}
	
	public static void email(String recipient, String content, String subject, PropertyReader props, String filePath,String[] cc) {
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
            FileDataSource fds = new FileDataSource(filePath);
            MimeBodyPart excelBodyPart = new MimeBodyPart();
            excelBodyPart.setDataHandler(new DataHandler(fds));
            excelBodyPart.setFileName("EmailReport_"+DateUtils.getYesterdayDate()+".xlsx");
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(excelBodyPart);
             
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
