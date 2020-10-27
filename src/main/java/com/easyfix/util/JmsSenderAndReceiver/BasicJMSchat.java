package com.easyfix.util.JmsSenderAndReceiver;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.swing.JOptionPane;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;

import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.easyfix.util.JmsSenderAndReceiver.service.ExotelLoggingService;

public class BasicJMSchat implements MessageListener {

	private UserService userService;
	 private JmsTemplate jmsTemplate;
	 private String JmsmsgText;
	 private List<ExotelCallDetails> JmsMsgList = new ArrayList<ExotelCallDetails>();
	 private ExotelCallDetails exotelCallDetails;
	 private ExotelLoggingService exotelLoggingService;

	@Override
	public void onMessage(Message msg) {
		if(msg instanceof TextMessage ){
			TextMessage t = (TextMessage) msg;
			try {

				String s = t.getText();
			
				ObjectMapper mapper = new ObjectMapper();
				ExotelCallDetails exotelCallDetails =mapper.readValue(s, ExotelCallDetails.class);
				
				if(exotelCallDetails.getAgentNumber().trim().length()>10){
					int length =exotelCallDetails.getAgentNumber().trim().length(); 
					String num = exotelCallDetails.getAgentNumber().trim().substring(length-10,length);
					exotelCallDetails.setAgentNumber(num);
				}
				if(exotelCallDetails.getFromNumber().trim().length()>10){
					int length =exotelCallDetails.getFromNumber().trim().length(); 
					String num = exotelCallDetails.getFromNumber().trim().substring(length-10,length);
					exotelCallDetails.setFromNumber(num);
				}
	//			User user = userService.getUserDetailsByMobleNo(exotelCallDetails.getAgentNumber());
	//			exotelCallDetails.setCrmUserId(user.getUserId());
	//			exotelCallDetails.setIsSeenByCrmUser(0);  //0: not seen ,1: seen
				//logging in db
				exotelLoggingService.persistExotelCallDetails(exotelCallDetails);
				//setting call details to the action page
	//			DemoMainAction.exotelCallDetails=exotelCallDetails;
				
				System.out.println("in BasicJMSchat "+exotelCallDetails);
				System.out.println(exotelCallDetails.getAgentNumber());
				System.out.println(userService.getUserDetailsByMobleNo(exotelCallDetails.getAgentNumber()));
		
				
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(msg instanceof ObjectMessage){
			try {
			//	ExotelCallDetails call = (ExotelCallDetails) msg.getObjectProperty("exotelCallDetails");
				System.out.println(msg.getObjectProperty("cusNo")+"=="+msg.getObjectProperty("staus"));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		System.out.println("msg read in basicjmschat:::: "+msg);
		System.out.println("above msg was listned in basicjmschat");
		}
	//	DemoMainAction demoMainAction = new DemoMainAction();
		//demoMainAction.setJmsMsgList(this.JmsMsgList);
	//	demoMainAction.setExotelCallDetails(exotelCallDetails);
		//demoMainAction.showPopUpOnScreen();
		//JOptionPane.showMessageDialog(null, getJmsmsgText());
		
	}

	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}


	public List<ExotelCallDetails> getJmsMsgList() {
		return JmsMsgList;
	}


	public void setJmsMsgList(List<ExotelCallDetails> jmsMsgList) {
		JmsMsgList = jmsMsgList;
	}
	public String getJmsmsgText() {
		return JmsmsgText;
	}


	public void setJmsmsgText(String jmsmsgText) {
		JmsmsgText = jmsmsgText;
	}


	public ExotelCallDetails getExotelCallDetails() {
		return exotelCallDetails;
	}


	public void setExotelCallDetails(ExotelCallDetails exotelCallDetails) {
		this.exotelCallDetails = exotelCallDetails;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public ExotelLoggingService getExotelLoggingService() {
		return exotelLoggingService;
	}


	public void setExotelLoggingService(ExotelLoggingService exotelLoggingService) {
		this.exotelLoggingService = exotelLoggingService;
	}

}
