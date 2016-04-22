//////////////////////////////////////////////////////////////////////////
// File Name	: MainActivity.java										//
// Date	 		: 2013.12.05											//
// Compiler 	: Android Studio 1.5.1									//
// Os	 		: Window 7												//
// Author		: Kim kyung min											//
//----------------------------------------------------------------------//
// ver			: 1.0.1													//
// Description	: mole killing game										//
//////////////////////////////////////////////////////////////////////////
package com.example.mole_kill;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class MainActivity extends Activity {
	Button[] imgButton = new Button[3];			// basic button ( start, record view, exit )
	private static MediaPlayer mp;
	@Override
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mp = MediaPlayer.create(this,R.raw.cannon);		// music setting
		mp.setLooping(true);							// music loop
		mp.start();										// music start
		imgButton[0] = (Button)findViewById(R.id.Start);
		imgButton[1] = (Button)findViewById(R.id.Score);
		imgButton[2] = (Button)findViewById(R.id._Exit);
		this.imgButton[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	// start button -> play.class intent
				MainActivity.this.startActivity(new Intent(MainActivity.this,Play.class));
			}
		});
		this.imgButton[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	// review button -> score.class intent
				MainActivity.this.startActivity(new Intent(MainActivity.this,Score.class));
				//record view
			}
		});
		this.imgButton[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	// exit button -> finish
				finish();
				//exit
			}
		});
	}
	public void onBackPressed()	// backpress -> finish
	{
		finish();
		super.onBackPressed();
	}
	public void onPause(){
		super.onPause();
		mp.pause();			// music pause
	}
	public void onResume(){
		super.onResume();
		mp.start();			// music restart
	}
}