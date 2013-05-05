package com.example.photoalbumapp;

import java.io.Serializable;
import java.util.ArrayList;

/** @author Adithya Pothuri, Geetha Srinivasan */
public class User implements Serializable {
	
	/*Variables*/
	private final String id;
	private String fullname;
	private ArrayList<Album> userAlbums;
	
	public User(String ID, String Name)
	{
		id = ID;
		fullname = Name;
		userAlbums = new ArrayList <Album>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return fullname;
	}

	public void setName(String name) {
		this.fullname = name;
	}

	public ArrayList<Album> getUserAlbums() {
		return userAlbums;
	}

	public void setUserAlbum(Album album) {
		this.userAlbums.add(album);
	}
	
	public void deleteUserAlbum(Album album) {
		this.userAlbums.remove(album);
	}
	
}
