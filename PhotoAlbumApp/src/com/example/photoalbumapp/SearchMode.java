package com.example.photoalbumapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchMode extends Activity{
	
	public MyAlbumList myList;
	public ArrayList<Photo> photo_List;
	public Button searchButton;
	public EditText valueField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchmode);
		
		myList = MainActivity.myList; 
		photo_List = new ArrayList<Photo>();
		valueField = (EditText) findViewById(R.id.tag_value_text);

		/* set up the list view to hold route names */
		final ListView listView = (ListView)findViewById(R.id.result_list);

		// now display it onto the app
		final ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photo_List);
		listView.setAdapter(adapter);
		
		/* when delete button is clicked, delete Photo */
		searchButton= (Button) findViewById(R.id.search_by_tag);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String value = valueField.getText().toString();
				ArrayList<Tag> tags = new ArrayList<Tag>();
				tags.add(new Tag("", value));
				
				photo_List.clear();
				ArrayList<Photo> returnedlist = new ArrayList<Photo>();
				returnedlist = myList.getPhotosByTag(tags);
				for (Photo pic : returnedlist){
					photo_List.add(pic);
				}
				System.out.println("num photos w/ given tags = " + photo_List.size());
				adapter.notifyDataSetChanged();
			}
		});
	}
}
