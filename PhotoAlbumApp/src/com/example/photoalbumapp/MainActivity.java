package com.example.photoalbumapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity{

	public Button viewAlbum, renameAlbum, createAlbum, deleteAlbum, searchButton;
	public EditText text;
	
	public User user;
	private Backend backend;
	public static MyAlbumList myList;
	public static Album selectedAlbum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Context ctx;
        ctx = this;
		backend = Backend.getInstance(ctx);
		
		user = new User("username", "first last");
		myList = new MyAlbumList(user);
		
		/* set up the list view to hold route names */
		final ListView listView = (ListView)findViewById(R.id.album_list);
		
		// now display it onto the app
		final ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, myList.getAlbums());
		listView.setAdapter(adapter);

		/*Set text instructions*/
		//text.setText("Enter Album Name");
		
		adapter.notifyDataSetChanged();
		/*Get selected Album*/
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v , int position, long id) {
				selectedAlbum = (Album) listView.getItemAtPosition(position);
			}
		});

		/* when open button is clicked, open new activity */
		viewAlbum=(Button) findViewById(R.id.open_view);
		viewAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/* open a new 'album view' activity then show its contents.. */
				if (selectedAlbum != null) {
					Intent i = new Intent(MainActivity.this, AlbumInfo.class);
					startActivity(i);
				}
				else {
					Intent i = new Intent(MainActivity.this, errorView.class);
					startActivity(i);
				}
			}
		});
		
		/* when rename button is clicked, rename Album */
		renameAlbum=(Button) findViewById(R.id.rename);
		renameAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				text = (EditText) findViewById(R.id.editText);
				String getText = text.getText().toString();
				selectedAlbum.setName(getText);
				adapter.notifyDataSetChanged();
			}
		});
		
		/* when create button is clicked, rename Album */
		createAlbum=(Button) findViewById(R.id.create);
		createAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean hasIt = false;
				text = (EditText) findViewById(R.id.editText);
				String getText = text.getText().toString();
				for(Album a: myList.getAlbums()){
					if(a.getName().equals(getText)){
						hasIt = true;
					}
				}
				if(hasIt == false && (!getText.equals(""))){
					myList.addAlbum(getText);
					/*write to file*/
					Album a = new Album(getText);
					try {
						backend.write(a);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		/* when delete button is clicked, delete Album */
		deleteAlbum=(Button) findViewById(R.id.delete);
		deleteAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				for(int i=0; i<myList.getAlbums().size(); i++){
					if(myList.getAlbums().get(i).getName().equals(selectedAlbum.getName())){
						myList.deleteAlbum(selectedAlbum);
						adapter.notifyDataSetChanged();
					}
				}
			}
		});
		
		/* Search Mode button pressed*/
		searchButton = (Button) findViewById(R.id.search_mode);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//go into search activity
				Intent i = new Intent(MainActivity.this, SearchMode.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
