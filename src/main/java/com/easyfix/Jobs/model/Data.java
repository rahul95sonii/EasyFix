package com.easyfix.Jobs.model;

public class Data
{
    private Notification notification;
    

    public Notification getNotification ()
    {
        return notification;
    }

    public void setNotification (Notification notification)
    {
        this.notification = notification;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [notification = "+notification+"]";
    }
}