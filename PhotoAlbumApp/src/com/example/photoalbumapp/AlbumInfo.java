package com.example.photoalbumapp;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AlbumInfo extends Activity{

	public Album selectedAlbum;
	public Button addPhoto, removePhoto, displayPhoto; 
	public EditText text1, text2;
	public ArrayList<Photo> photoList;
	public static Photo selectedPhoto;
	public MyAlbumList myList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albuminfo);

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
		
	   /* when remove button is clicked, remove Photo */
		addPhoto=(Button) findViewById(R.id.add_photo);
		addPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				text1 = (EditText) findViewById(R.id.editText1);
				String getText1 = text1.getText().toString();
				text2 = (EditText) findViewById(R.id.editText2);
				String getText2 = text2.getText().toString();
				Photo p = new Photo(getText1, getText2);
				myList.addPhoto(selectedAlbum, p);
				adapter.notifyDataSetChanged();
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
		

	} 
}
