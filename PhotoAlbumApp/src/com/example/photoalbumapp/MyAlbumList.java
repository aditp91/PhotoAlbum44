package com.example.photoalbumapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import android.content.Context;

/**
 * This class implements the Singleton pattern: only one instance can be
 * created. 
 * 
 * @author Sesh Venugopal
 *
 */
public class MyAlbumList {
	
	/* Global Variables */
	private static MyAlbumList albumList=null; // single instance
	private ArrayList<Album> albums; // holds albums in a sorted array list
	private int maxId; // keep track of max id dealt out so far
	
	// make constructor private for single instance control
	private MyAlbumList() {
		albums = new ArrayList<Album>();
		maxId = -1;
	}
	
	// deal out the singleton
	public static MyAlbumList getInstance() {
		if (albumList == null) {
			albumList = new MyAlbumList();
		}
		return albumList;
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void add(String name) {
		// name and artist are mandatory
		if (name == null) {
			throw new IllegalArgumentException("Name is mandatory");
		}
		// set id to next available
		maxId++;
		
		// create album object
		Album album = new Album(name);
		albums.add(album);
		
		// write through
		//store();

	}
//
//	public int getPos(Album album) {
//		if (albums.size() == 0) {
//			return -1;
//		}
//
//		// search in array list for name match, then id match
//		int lo=0, hi=albums.size()-1;
//		
//		while (lo <= hi) {
//			int mid = (lo+hi)/2;
//			Album lalbum = albums.get(mid);
//			int c = album.compareTo(lalbum);
//			if (c == 0) {  // need to compare id
//				if (album.id == lalbum.id) {
//					return mid;
//				}
//				// check left
//				int i=mid-1;
//				while (i >= 0) {
//					lalbum = albums.get(i);
//					if (album.compareTo(lalbum) == 0 && album.id == lalbum.id) {
//						return i;
//					}
//					i--;
//				}
//				// check right
//				i = mid+1;
//				while (i < albums.size()) {
//					lalbum = albums.get(i);
//					if (album.compareTo(lalbum) == 0 && album.id == lalbum.id) {
//						return i;
//					}
//					i++;
//				}
//				return -1;
//			}
//			if (c < 0) {
//				hi = mid-1;
//			} else {
//				lo = mid+1;
//			}
//		}
//		return -1;
//	}
//
//
//	public void load() throws IOException {
//
//	}
//
//	public ArrayList<Album> remove(Album album) throws NoSuchElementException {
//		int pos = getPos(album);
//		if (pos == -1) {
//			throw new NoSuchElementException();
//		}
//		albums.remove(pos);
//		return albums;
//	}
//
//	public ArrayList<Album> update(Album album) throws NoSuchElementException {
//		// since name could be updated, best to sequentially search on id
//		for (int i=0; i < albums.size(); i++) {
//			if (albums.get(i).id == album.id) {
//				albums.set(i, album);
//				return albums;
//			}
//		}
//		throw new NoSuchElementException();
//	}

}
