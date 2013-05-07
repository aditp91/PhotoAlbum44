package com.example.photoalbumapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AlbumInfo extends Activity{

	public Album selectedAlbum;
	public Button addPhoto, removePhoto, displayPhoto, movePhoto; 
	public EditText text1, text2, text;
	public ArrayList<Photo> photoList;
	public static Photo selectedPhoto;
	public MyAlbumList myList;
	final int SELECT_IMAGE = 1234;
	public static String currPath;
	public static int currpos;
	public static ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albuminfo);

		myList = MainActivity.myList;
		this.selectedAlbum = MainActivity.selectedAlbum;

		TextView myTextView = (TextView) findViewById(R.id.album_name);
		myTextView.setText("Album name: " + selectedAlbum);

		/* set up the list view to hold route names */
		listView = (ListView)findViewById(R.id.photo_list);

		// get the photos from the selected album
		photoList = selectedAlbum.getPhotos();

		// now display it onto the app
		final ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
		listView.setAdapter(adapter);

		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v , int position, long id) {
				selectedPhoto = (Photo) listView.getItemAtPosition(position);
				/*Add Thumbnail*/
				File file = new File(selectedPhoto.getRealPath());
				ImageView ImgView = (ImageView)findViewById(R.id.thumbnail);
				Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
				ImgView.setImageBitmap(bmp);
				currpos = position;
			}
		});

		/* when delete button is clicked, delete Photo */
		removePhoto=(Button) findViewById(R.id.remove_photo);
		removePhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				myList.deletePhoto(selectedAlbum, selectedPhoto);
				photoList.clear();
				for(Photo p: selectedAlbum.getPhotos()){
					photoList.add(p);
				}
				listView.setAdapter(adapter);
				/*write to file*/
				try {
					MainActivity.backend.write(MainActivity.user);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();

			}
		});

		/* when display button is clicked, display Photo */
		displayPhoto=(Button) findViewById(R.id.display_photo);
		displayPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*open new activity for slideshow*/
				if(selectedPhoto == null || selectedPhoto.getFileName() == null){
					Intent i = new Intent(AlbumInfo.this, errorView.class);
					startActivity(i);
				} else {
					currPath = selectedPhoto.getRealPath();
					Intent j = new Intent(AlbumInfo.this, PhotoInfo.class);
					startActivity(j);
				}
			}
		});

		/* when add button is clicked, add Photo */
		addPhoto=(Button) findViewById(R.id.add_photo);
		addPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(Intent.createChooser(i, "Choose Photo To Add"),SELECT_IMAGE);
			}
		});

		/* when move button is clicked, move Photo */
		movePhoto=(Button) findViewById(R.id.move_photo);
		movePhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//MOVE PHOTO IMPLEMENTATION HERE
				text = (EditText) findViewById(R.id.editText2);
				String moveToAlbum = text.getText().toString();
				//find new album and get the album object
				Album getAlbum = null;
				for(Album a: MainActivity.myList.getAlbums()){
					if(a.getName().equals(moveToAlbum)){
						getAlbum = a;
					}
				}
				if(getAlbum != null){
					//add photo to new album
					getAlbum.setPhotos(selectedPhoto);
					//delete photo from old album
					photoList.remove(selectedPhoto);
					selectedAlbum.deletePhoto(selectedPhoto);
					/*write to file*/
					try {
						MainActivity.backend.write(MainActivity.user);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
			}
		});			
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		final ListView listView = (ListView)findViewById(R.id.photo_list);
		final ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
		listView.setAdapter(adapter);
		if (requestCode == SELECT_IMAGE){
			if (resultCode == Activity.RESULT_OK) {
				boolean isThere = false;
				Uri selectedImage = data.getData();
				String realPath = getRealPathFromURI(selectedImage);
				File file = new File(realPath);
				Photo p = new Photo(file.getName(), realPath);
				for(Photo photo: selectedAlbum.getPhotos()){
					if(photo.getFileName().equals(file.getName())){
						isThere = true;
					}
				}
				if(isThere == false){
					myList.addPhoto(selectedAlbum, p);
					/*write to file*/
					try {
						MainActivity.backend.write(MainActivity.user);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
