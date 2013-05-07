package com.example.photoalbumapp; 

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

/** @author Adithya Pothuri, Geetha Srinivasan */

public class Backend {

	private static Backend instance = null;
	private Context ctx;
	final String FILE = new String("file1");
	public ArrayList<Album> albumList;
	public Album album;
	public User user;
	//public ArrayList <Album> albumList = MainActivity.myList.getAlbums(); 
	
	public Backend(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		File fl = new File(ctx.getFilesDir() + File.separator + FILE);
		if(fl.exists()){
			try {
				user = read();	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			user = new User("username", "first last");
			try {
				write(user);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		else{
//			try {
//				if(!albumList.isEmpty()){
//					for(Album a: albumList){
//						write(a);
//						
//					}
//				}
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		
	}
	public static Backend getInstance(Context context){
		if(instance == null){
			instance = new Backend(context);
		}
		return instance;
		
	}
	public User read() throws ClassNotFoundException, IOException {
		/* Deserialize an object */
		FileInputStream fis = ctx.openFileInput(FILE);
		ObjectInputStream is = new ObjectInputStream(fis);
	    User user = (User) is.readObject();
//		//System.out.println("Filename: " + a.getName());
//		albumList.add(a);
//		album = a;
		is.close();
		return user;
		
	}


	public void write(User u) throws FileNotFoundException, IOException {
		/* Serialize an object during logout so get user from userlist*/
		FileOutputStream fos = ctx.openFileOutput(FILE, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(u);
		os.flush();
		os.close();
	}

	public boolean deleteUser(String userID) throws IOException, ClassNotFoundException {
		String location = "data/users/";
		File dir = new File(location);

		for (File file : dir.listFiles()) {
			//file exists so delete it
			if (file.getName().startsWith(userID) && file.getName().endsWith((".ser"))) {
				file.delete();
				return true;
			}
		}
		return false;

	}

	public ArrayList<String> getUserList () {

		String location = "data/users/";
		File dir = new File(location);
		ArrayList <String> users = new ArrayList<String>();

		for (File file : dir.listFiles()) {

			//Retrieve all files
			users.add(file.getName().substring(0, file.getName().indexOf(".")));
		}
		return users;
	}


	public Album findAlbum(String albumName, User user) {
		for(Album a : user.getUserAlbums()){
			if(a.getName().equals(albumName)){
				return a;
			}
		}
		return null;
	}

	public Photo findPhoto(String filename, Album album) {
		if(album == null){
			return null;
		}
		for(Photo p : album.getPhotos()){
			if(p.getFileName().equals(filename)){
				return p;
			}
		}
		return null;
	}
}