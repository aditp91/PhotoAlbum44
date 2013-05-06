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
	final String FILE = new String("file");
	public Album album;
	//public ArrayList <Album> albumList = MainActivity.myList.getAlbums(); 
	
	public Backend(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		File f = new File(ctx.getFilesDir() + File.separator + FILE);
		if(f.exists()){
			try {
				Album a = read();
				album = a;
				
			} catch (ClassNotFoundException e) {
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
	public Album read() throws ClassNotFoundException, IOException {
		/* Deserialize an object */

		FileInputStream fis = ctx.openFileInput(FILE);
		ObjectInputStream is = new ObjectInputStream(fis);
	    //User user = (User) is.readObject();
		Album a = (Album) is.readObject();
		is.close();
		return a;
		//return user;
//		User user=null; 
//		String pathname = "data/users/" + userID + ".ser";
//		String location = "data/users/";
//
//		File dir = new File(location);
//
//		for(File file : dir.listFiles())
//		{
//			if (file.getName().startsWith(userID) && file.getName().endsWith((".ser"))) {
//				FileInputStream fin = new FileInputStream(pathname);
//				ObjectInputStream fileIn = new ObjectInputStream(fin);
//				user = (User) fileIn.readObject();
//				fileIn.close();
//				fin.close();
//				return user;
//			}
//		}
//		return null;
	}

//
//	public void addUser() throws IOException {
//
//		/* Serialize an object during logout so get user from userlist*/
//		//System.out.println("user name is  : " + u.getId());
//
//		//String pathname = "users/" + u.getId() + ".ser";
////		String location = "data/users/";
//		//		File dir = new File(location);
////		FileOutputStream fw = new FileOutputStream(dir);
////		
////		ObjectOutputStream fileOut = new ObjectOutputStream(fw);
////		fileOut.writeObject(u);
////		fileOut.close();
////		fw.close();
////		return u;
//		FileOutputStream fos = ctx.openFileOutput(file, Context.MODE_PRIVATE);
//		ObjectOutputStream os = new ObjectOutputStream(fos);
//		os.writeObject(this);
//		os.close();
//		//return u;
//	}


	public void write(Album a) throws FileNotFoundException, IOException {
		/* Serialize an object during logout so get user from userlist*/
		FileOutputStream fos = ctx.openFileOutput(FILE, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(a);
		os.flush();
		os.close();
//		String pathname = "data/users/" + u.getId() + ".ser";
//		String location = "data/users/";
//
//		File dir = new File(location);
//		//if theres no files in data
//		if(dir.listFiles().length == 0){
//			FileOutputStream fw = new FileOutputStream(pathname);
//			ObjectOutputStream fileOut = new ObjectOutputStream(fw);
//			fileOut.writeObject(u);
//			fileOut.close();
//			fw.close();
//		}
//		for (File file : dir.listFiles()) {
//			//file already exists so overwrite it
//			if (file.getName().startsWith(u.getId()) && file.getName().endsWith((".ser"))) {
//				FileOutputStream fw = new FileOutputStream(pathname);
//				ObjectOutputStream fileOut = new ObjectOutputStream(fw);
//				fileOut.writeObject(u);
//				fileOut.close();
//				fw.close();
//			}
//			else{ //creating file
//				FileOutputStream fw = new FileOutputStream(pathname);
//				ObjectOutputStream fileOut = new ObjectOutputStream(fw);
//				fileOut.writeObject(u);
//				fileOut.close();
//				fw.close();
//			}
//		}
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