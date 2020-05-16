package com.example.liaotian.util;

public class MessageEvent
{
    private int eventType;
    private Object message;

    public MessageEvent(int eventType, Object message)
    {
        this.eventType = eventType;
        this.message = message;
    }

    public MessageEvent()
    {
    }

    public int getEventType()
    {
        return eventType;
    }

    public void setEventType(int eventType)
    {
        this.eventType = eventType;
    }

    public Object getMessage()
    {
        return message;
    }

    public void setMessage(Object message)
    {
        this.message = message;
    }
}
