package com.easyfix.util.JmsSenderAndReceiver;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
 
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
public class JmsMessageSender {
//	@Autowired
	  private JmsTemplate jmsTemplate;
	    
	    
	 
	  public void send(final String text) {
	      
	    this.jmsTemplate.send(new MessageCreator() {
	      @Override
	      public Message createMessage(Session session) throws JMSException {
	        Message message = session.createObjectMessage();
	        //.createTextMessage(text);
/*	        ExotelCallDetails call = new ExotelCallDetails();
	        call.setAgentContact("9971273289");
	        call.setCustomerContact("9971273288");
	        call.setStatus(1);*/
	        message.setObjectProperty("cusNo", new String("9971273289"));
	        message.setObjectProperty("staus", new Integer(1));
	       
	        //set ReplyTo header of Message, pretty much like the concept of email.
	        message.setJMSReplyTo(new ActiveMQQueue("Recv2Send"));
	        return message;
	      }
	    });
	  }
	  
	  public void receive(){
		  TextMessage m = (TextMessage) jmsTemplate.receive("Send2Recv");
		  System.out.println(m);
		  System.out.println("above message war received");
		  
	  }
	    
	 
	  public void sendText(final String text) {
	    this.jmsTemplate.convertAndSend(text);
	  }
	    
	  
	  public void send(final Destination dest,final String text) {
	      
	    this.jmsTemplate.send(dest,new MessageCreator() {
	      @Override
	      public Message createMessage(Session session) throws JMSException {
	       // Message message = session.createTextMessage(text);
	    	  Message message = session.createObjectMessage();
	    	  message.setObjectProperty("cusNo", new String("999"));
		        message.setObjectProperty("staus", new Integer(1));
	        return message;
	      }
	    });
	  }


	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}


	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
