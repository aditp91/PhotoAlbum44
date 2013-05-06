package com.example.photoalbumapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/** @author Adithya Pothuri, Geetha Srinivasan */
public class Photo implements Serializable
{
	/*Variables*/
	private final String fileName;
	private final Calendar cal;
	private String realPath;
	private ArrayList<Tag> tags;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
    
    public Photo (String File, String realpath) {
		fileName = File;
		realPath = realpath;
	    cal = Calendar.getInstance();
	   	cal.set(Calendar.MILLISECOND, 0);
		tags = new ArrayList<Tag>();
	}
    
    /*Getters and Setters*/
    
	public String getFileName() 
	{
		return fileName;
	}

	public String getRealPath() 
	{
		return realPath;
	}

	public void setRealPath(String realpath) 
	{
		this.realPath = realpath;
	}

	public String getTime() 
	{
		return dateFormat.format(cal.getTime());
	}
	
	public Calendar getCalendar()
	{
		return cal;
	}
	
	/*Get the tagtype arraylist*/
	public void addTag(String type, String val) 
	{
		tags.add(new Tag(type, val));
	}
	
	public ArrayList<Tag> getTags()
	{
		return tags;
	}
	
	public void deleteTag(Tag t) {
		tags.remove(t);
	}
	
	public String toString() {
		String str = this.getFileName();
		return str;
	}
}
