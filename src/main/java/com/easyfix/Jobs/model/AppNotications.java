package com.easyfix.Jobs.model;

public class AppNotications
{
	private Data data;

	private String to;

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [to = " + to + ", data = " + data + "]";
	}
}
