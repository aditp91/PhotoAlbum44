package com.example.photoalbumapp;

import java.io.Serializable;

/** @author Adithya Pothuri, Geetha Srinivasan */
public class Tag implements Serializable{
	
	private String tagType;
	private String tagValue;
	
	public Tag(String type, String value){
		this.setTagType(type);
		this.setTagValue(value);
	}
	
	public String getTagValue() {
		return tagValue;
	}
	
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	
	public String getTagType() {
		return tagType;
	}
	
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	public String toString() {
		String str = this.tagType + ": " + this.tagValue;
		return str;
	}
}