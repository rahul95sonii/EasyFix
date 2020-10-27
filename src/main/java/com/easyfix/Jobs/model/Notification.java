package com.easyfix.Jobs.model;

public class Notification
{
	private String message;

	private String title;

	private String flag;

	private String priority;

	private String type;

	private String key;
	
	
	public Notification(String message, String title, String flag, String priority, String type, String key)
	{
		super();
		this.message = message;
		this.title = title;
		this.flag = flag;
		this.priority = priority;
		this.type = type; //1: yes/no  2: any other notification
		this.key = key;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [message = " + message + ", title = " + title + ", flag = " + flag + ", priority = " + priority + ", type = "
				+ type + ", key = " + key + "]";
	}
}