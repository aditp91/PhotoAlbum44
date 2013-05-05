package com.example.photoalbumapp;

import java.util.ArrayList;


/**
 * This class implements the Singleton pattern: only one instance can be
 * created. 
 * 
 * @author Sesh Venugopal
 *
 */
public class MyAlbumList {

	/* Global Variables */
	private ArrayList<Album> albums; // holds albums in a sorted array list
	private ArrayList<Photo> photoList; 
	public User user;

	//private int maxId; // keep track of max id dealt out so far

	// make constructor private for single instance control
	public MyAlbumList(User userGuy) {
		this.user = userGuy;
		albums = new ArrayList<Album>();
		albums = user.getUserAlbums();
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public ArrayList<Photo> getPhotos(){
		return photoList;
	}

	public void addAlbum(String name) {
		// name and artist are mandatory
		if (name == null) {
			throw new IllegalArgumentException("Name is mandatory");
		}
		// create album object
		Album album = new Album(name);
		albums.add(album);

	}
	public void deleteAlbum(Album a) {
		// name and artist are mandatory
		if (a.getName() == null) {
			throw new IllegalArgumentException("Name is mandatory");
		}
		//delete album object
		albums.remove(a);

	}

	public void deletePhoto(Album a, Photo p) {
		// name and artist are mandatory
		if (a.getName() == null) {
			throw new IllegalArgumentException("Name is mandatory");
		}

		//delete album object
		a.deletePhoto(p);


	}
	public void addPhoto(Album a, Photo p) {
		// name and artist are mandatory
		if (a.getName() == null) {
			throw new IllegalArgumentException("Name is mandatory");
		}
		a.setPhotos(p);
	}
	
	public ArrayList<Photo> getPhotosByTag(ArrayList<Tag> inputTags){
		ArrayList<Photo> outputPhotos = new ArrayList<Photo>();
		//System.out.print("photos for user " + user.getId() + " with the following tags -\n");
		for (Tag tag : inputTags) {
			//if(tag.getTagValue().equals(",")) { continue;}
			//System.out.print(tag.getTagType() + ":" + tag.getTagValue() + " :\n");
			for (Album a : user.getUserAlbums()) {
				for (Photo p : a.getPhotos()) {
					for (Tag t : p.getTags()) {
						if (tag.getTagType() == null && tag.getTagValue().equals(t.getTagValue())) {
							//outputPhotos.add(p);
							if(!outputPhotos.contains(p))
								outputPhotos.add(p);
							//System.out.println(p.getCaption() + " - album: " + a.getName()
									//+ "- date: " + p.getTime()); 
						}
						else if (tag.getTagType() != null && tag.getTagType().equals(t.getTagType()) && tag.getTagValue().equals(t.getTagValue())) {
							//outputPhotos.add(p);
							if(!outputPhotos.contains(p))
								outputPhotos.add(p);
							//System.out.println(p.getCaption() + " - album: " + a.getName()
									//+ "- date: " + p.getTime()); 
						}
					}
				}
			}
		}
		System.out.println("outputPhotos w/ given tags = " + outputPhotos.size());
		return outputPhotos;
	}

}
