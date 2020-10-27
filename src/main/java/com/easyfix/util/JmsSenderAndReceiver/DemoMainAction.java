package com.easyfix.util.JmsSenderAndReceiver;
import java.util.List;

import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.http.HttpServlet;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.easyfix.util.JmsSenderAndReceiver.service.ExotelLoggingService;
import com.opensymphony.xwork2.ModelDriven;
public class DemoMainAction extends CommonAbstractAction implements ModelDriven<ExotelCallDetails>{
	
	private String msg;
	private ExotelCallDetails exotelCallDetails;
	private static List<ExotelCallDetails> JmsMsgList;
	private User userObj;
	private static final long serialVersionUID = 1L;
	private  JmsMessageSender jmsMessageSender;
	private ExotelLoggingService exotelLoggingService;
	

	public String showPopUpOnScreen(){
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		
		String callSid = requestObject.getParameter("callSid");
		String fromNumber = requestObject.getParameter("fromNumber").trim();
		
		fromNumber = fromNumber.substring(fromNumber.length()-10,fromNumber.length());
		
		String agentNumber = requestObject.getParameter("agentNumber").trim();
		agentNumber = agentNumber.substring(agentNumber.length()-10,agentNumber.length());
		
		String type = requestObject.getParameter("type");
		
		exotelCallDetails.setAgentNumber(agentNumber);
		exotelCallDetails.setCallSid(callSid);
		exotelCallDetails.setFromNumber(fromNumber);
		exotelCallDetails.setType(type);
		
		requestObject.setAttribute("userMobile", userObj.getMobileNo());
		
//		System.out.println("in showPopUpOnScreen "+exotelCallDetails);
		
		
		return SUCCESS;
	}
	
	
	public String extoelCallBooking(){
		try {
			String callSid = requestObject.getParameter("callSid");
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			
			if(exotelCallDetails.getCrmUserId() == userObj.getUserId() && exotelCallDetails.getCallSid().equalsIgnoreCase(callSid) )
				exotelCallDetails.setIsSeenByCrmUser(1);
			//update db
			ExotelCallDetails userCall = new ExotelCallDetails();
			userCall.setCallSid(callSid);
			userCall.setCrmUserId(userObj.getUserId());
			exotelLoggingService.updateExotelCallDetails(userCall);
			
			//open book job page
			String mobNo = requestObject.getParameter("mobileNo");
			requestObject.setAttribute("mobNo", mobNo);
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return SUCCESS;
	}
	
	
	
	public String jmsConnector(){
		return SUCCESS;
	}
	
	
	
	/*public String sendMsgToJms() {

		System.out.println("in Demo main sending msg");
		try{
			for(int i=0; i<10; i++)
	    jmsMessageSender.send("hello JMS kundan welcomes you");
	  //  jmsMessageSender.receive();     
	    // send to a code specified destination
	    Queue queue = new ActiveMQQueue("AnotherDest");
	    Topic topic = new ActiveMQTopic("kundan");
	    jmsMessageSender.send(topic, "hello Another Message");
	   
	  // System.out.println("exiting Demo main");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    // close spring application context
	 //   ((ClassPathXmlApplicationContext)ctx).close();
	   // ((GenericXmlApplicationContext)ctx).close();
	return SUCCESS;  
	}*/
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ExotelCallDetails> getJmsMsgList() {
		return JmsMsgList;
	}

	public void setJmsMsgList(List<ExotelCallDetails> jmsMsgList) {
		JmsMsgList = jmsMsgList;
	}
	public JmsMessageSender getJmsMessageSender() {
		return jmsMessageSender;
	}

	public void setJmsMessageSender(JmsMessageSender jmsMessageSender) {
		this.jmsMessageSender = jmsMessageSender;
	}
	
	public  ExotelCallDetails getExotelCallDetails() {
		return exotelCallDetails;
	}
	
	public User getUserObj() {
		return userObj;
	}
	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}


	public ExotelLoggingService getExotelLoggingService() {
		return exotelLoggingService;
	}


	public void setExotelLoggingService(ExotelLoggingService exotelLoggingService) {
		this.exotelLoggingService = exotelLoggingService;
	}


	@Override
	public ExotelCallDetails getModel() {
		return exotelCallDetails = new ExotelCallDetails();
	}

}
