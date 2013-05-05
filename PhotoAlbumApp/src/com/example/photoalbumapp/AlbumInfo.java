package com.example.photoalbumapp;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
	public Button addPhoto, removePhoto, displayPhoto; 
	public EditText text1, text2;
	public ArrayList<Photo> photoList;
	public static Photo selectedPhoto;
	public MyAlbumList myList;
	final int SELECT_IMAGE = 1234;
	public static ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albuminfo);
		
		myList = MainActivity.myList;
		this.selectedAlbum = MainActivity.selectedAlbum;

		TextView myTextView = (TextView) findViewById(R.id.album_name);
		myTextView.setText("Album name: " + selectedAlbum);

		/* set up the list view to hold route names */
		final ListView listView = (ListView)findViewById(R.id.photo_list);

		// get the photos from the selected album
		photoList = selectedAlbum.getPhotos();

		// now display it onto the app
		final ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
		listView.setAdapter(adapter);

		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v , int position, long id) {
				selectedPhoto = (Photo) listView.getItemAtPosition(position);
			}
		});
		
		/* when delete button is clicked, delete Photo */
		removePhoto=(Button) findViewById(R.id.remove_photo);
		removePhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				myList.deletePhoto(selectedAlbum, selectedPhoto);
				adapter.notifyDataSetChanged();

			}
		});

		/* when display button is clicked, display Photo */
		displayPhoto=(Button) findViewById(R.id.display_photo);
		displayPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*open new activity for slideshow*/
				Intent j = new Intent(AlbumInfo.this, PhotoInfo.class);
				startActivity(j);
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
		
		
	} 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		final ListView listView = (ListView)findViewById(R.id.photo_list);
		final ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
		listView.setAdapter(adapter);
		if (requestCode == SELECT_IMAGE){
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				System.out.println("User Info of selected image is: " + selectedImage.getPath());
				String realPath = getRealPathFromURI(selectedImage);
				File file = new File(realPath);
				System.out.println("file Absolute Path: " + realPath);
				/*Set the image to the current photo selected to display in slideshow*/
				/*Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			    imageView=(ImageView)findViewById(R.id.imageView);
				imageView.setImageBitmap(myBitmap);*/
				Photo p = new Photo(file.getName(), "");
				myList.addPhoto(selectedAlbum, p);
				adapter.notifyDataSetChanged();
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
