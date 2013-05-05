package com.example.photoalbumapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhotoInfo extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoinfo);
		
		TextView myTextView = (TextView) findViewById(R.id.photoname);
		myTextView.setText("Photo name: " + AlbumInfo.selectedPhoto);
	
	}
	
}
