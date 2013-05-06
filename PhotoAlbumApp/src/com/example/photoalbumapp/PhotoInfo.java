package com.example.photoalbumapp;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PhotoInfo extends Activity{

	public MyAlbumList myList;
	public Album selectedAlbum;
	public Photo selectedPhoto;
	public Button addTag, deleteTag, prevButton, nextButton; 
	public EditText typeText, valueText;
	public ArrayList<Tag> tagList;
	public Tag selectedTag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoinfo);
		
		myList = MainActivity.myList;
		this.selectedAlbum = MainActivity.selectedAlbum;
		this.selectedPhoto = AlbumInfo.selectedPhoto;
		
		TextView myTextView = (TextView) findViewById(R.id.photoname);
		myTextView.setText("Photo name: " + selectedPhoto);
	
		/* Display the selectedPhoto onto the layout (large picture) */
		/*Set the image to the current photo selected to display in slideshow*/
		
		File file = new File(AlbumInfo.realPath);
		ImageView ImgView = (ImageView)findViewById(R.id.view_image);
		Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
		ImgView.setImageBitmap(bmp);
		
		/* set up the list view to hold route names */
		final ListView listView = (ListView)findViewById(R.id.tags_list);

		// get the photos from the selected album
		tagList = selectedPhoto.getTags();

		// now display it onto the app
		final ArrayAdapter<Tag> adapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, tagList);
		listView.setAdapter(adapter);

		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v , int position, long id) {
				selectedTag = (Tag) listView.getItemAtPosition(position);
			}
		});
		
		/* point the type and value text to the textfields */
		typeText = (EditText) findViewById(R.id.type_text);
		valueText = (EditText) findViewById(R.id.value_text);
		
		/* when add/del button is clicked, add/del Tag */
		addTag=(Button) findViewById(R.id.add_tag);
		addTag.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String type = typeText.getText().toString();
				String value = valueText.getText().toString();
				
				selectedPhoto.addTag(type, value);
				tagList = selectedPhoto.getTags();
				adapter.notifyDataSetChanged();
			}
		});
		deleteTag=(Button) findViewById(R.id.delete_tag);
		deleteTag.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectedPhoto.deleteTag(selectedTag);
				adapter.notifyDataSetChanged();
			}
		});
		
		/* Implement Slideshow buttons */
		nextButton=(Button) findViewById(R.id.next_button);
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// IMPLEMENT HERE
				
				adapter.notifyDataSetChanged();
			}
		});
		
		prevButton=(Button) findViewById(R.id.prev_button);
		prevButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				// IMPLEMENT HERE
				adapter.notifyDataSetChanged();
			}
		});
	}
}
