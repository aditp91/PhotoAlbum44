package com.example.photoalbumapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	public Button viewAlbum;
	
	public MyAlbumList myList;
	public static Album selectedAlbum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/* set up the list view to hold route names */
		final ListView listView = (ListView)findViewById(R.id.album_list);

		// get all the initial album names from the strings.xml
		String[] albums = getResources().getStringArray(R.array.album_array);
		myList = MyAlbumList.getInstance();

		// break each song into name and artist
		for (int i=0; i < albums.length; i++) {
			myList.add(albums[i]);
		}

		// now display it onto the app
		ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, myList.getAlbums());
		listView.setAdapter(adapter);

		adapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v , int position, long id) {
				selectedAlbum = (Album) listView.getItemAtPosition(position);
			}
		});

		/* when button is clicked, open new activity */
		viewAlbum=(Button) findViewById(R.id.open_view);
		viewAlbum.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/* open a new 'album view' activity then show its contents.. */
				Intent i = new Intent(MainActivity.this, AlbumInfo.class);
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
