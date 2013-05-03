package com.example.photoalbumapp;

import java.io.Serializable;
import java.util.ArrayList;

/** @author Adithya Pothuri, Geetha Srinivasan */
@SuppressWarnings("serial")
public class Album implements Serializable {
	
	/*Variables*/
	private String name;
    private int numPhotos;
	private ArrayList <Photo> photos;
	
	public Album (String Name)
	{
		name = Name;
		setNumPhotos(0);
		photos = new ArrayList <Photo>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    /*Get the Photo ArrayList*/
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/*Set a specific photo*/
	public void setPhotos(Photo p) {
		this.photos.add(p);
		this.setNumPhotos(this.getNumPhotos() + 1);
	}

	public int getNumPhotos() {
		return numPhotos;
	}

	public void setNumPhotos(int numPhotos) {
		this.numPhotos = numPhotos;
	}
	
	public void deletePhoto(Photo p){
		this.photos.remove(p);
		this.setNumPhotos(this.getNumPhotos()-1);
	}
	
	public String toString() {
		String str = this.getName();
		return str;
	}
}
