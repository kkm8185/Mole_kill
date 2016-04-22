//////////////////////////////////////////////////////////////////////////
// File Name	: Play.java												//
// Date	 		: 2013.12.05											//
// Compiler 	: Android Studio 1.5.1									//
// Os	 		: Window 7												//
// Author		: Kim kyung min											//
//----------------------------------------------------------------------//
// ver			: 1.0.7													//
// Description	: mole killing game										//
//////////////////////////////////////////////////////////////////////////
package com.example.mole_kill;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Play extends Activity {
	private static MediaPlayer mp;
	private Handler mHandler = null;
	int Value = 0;							// score
	int Count = 30, seconds = 0, clear = 0, mole_count = 0;
	int flag = 0, mp_flag = 0;
	int i, j;
	double rValue;
	int selectedIndex;
	int[] imgArray = new int[9];
	Button[] imgMole = new Button[9];
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		final Button btnReset = (Button) findViewById(R.id.btnReset);
		final Button btnReturn = (Button) findViewById(R.id.btnReturn);
		final TextView txtScore = (TextView) findViewById(R.id.txtScore);
		final TextView txtTime = (TextView) findViewById(R.id.txtTime);
		imgMole[0] = (Button) findViewById(R.id.img01);
		imgMole[1] = (Button) findViewById(R.id.img02);
		imgMole[2] = (Button) findViewById(R.id.img03);
		imgMole[3] = (Button) findViewById(R.id.img04);
		imgMole[4] = (Button) findViewById(R.id.img05);
		imgMole[5] = (Button) findViewById(R.id.img06);
		imgMole[6] = (Button) findViewById(R.id.img07);
		imgMole[7] = (Button) findViewById(R.id.img08);
		imgMole[8] = (Button) findViewById(R.id.img09);
		mp = MediaPlayer.create(this, R.raw.kisstherain);	// music setting
		mp.setLooping(true);								// music loop
		mp.start();											// music start
		btnReset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {					// start button listener
				Value = 0;									// score
				Count = 30;									// timer
				seconds = 0;								// check second
				clear = 0;
				mole_count = 0;								// total mole
				flag = 1;
				mHandler.removeMessages(0);					// handler messages delete
				mHandler.sendEmptyMessageDelayed(0, 100);	// 0.1 second -> handler start
				for (i = 0; i < 9; i++) {
					imgArray[i] = 0;						// all mole value 0 set
					imgMole[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.none));
															// all mole img is none
				}
				btnReset.setBackgroundDrawable(getResources().getDrawable(R.drawable.reset));
															// button img change
			}
		});
		btnReturn.setOnClickListener(new View.OnClickListener() {	// if return button click,
			public void onClick(View v) {
				finish();						// onDestroy function call
			}
		});
		for (int i = 0; i < 9; i++) {
			final int arrayI = i;
			imgMole[i].setClickable(true);	// setting touchlistener.
			imgMole[i].setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:		// if that is down,
							if (imgArray[arrayI] == 1) {	// if array value is 1, mole img change
								imgMole[arrayI].setBackgroundDrawable(getResources().getDrawable(R.drawable.mole_hit));
								Value += 5;					// score +5
							} else {
								Value -= 5;					// if array value is not 1 ? score -5
							}
							return true;
						case MotionEvent.ACTION_UP:			// if that is up,
							if (imgArray[arrayI] == 1) {	// if array value is 1, mole img change
								imgMole[arrayI].setBackgroundDrawable(getResources().getDrawable(R.drawable.none));
								imgArray[arrayI] = 0;		// value is 0
								mole_count -= 1;			// total mole count value -1
							} else {
								Value -= 5;					// if array value is not 1 ? score -5
							}
							return true;
					}
					return false;
				}
			});
		}
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				txtTime.setText("Time:" + Count);
				txtScore.setText("Score:" + Value);
				if (Count == 0) {							// if time is finish ??
					String str = Integer.toString(Value);
					Intent localIntent = new Intent(Play.this, Record.class);	// intent -> record class
					localIntent.putExtra("score", str);							// data transmit
					startActivity(localIntent);									// start activity ( record class )
					finish();													// current activity onDestroy
					return;
				}
				if (Value >= 200 && mp_flag == 0) {								// Greater than 200 and mp_flag == 0;
					mp.stop();													// current music is stop
					mp = MediaPlayer.create(Play.this, R.raw.magic);			// music change
					mp.setLooping(true);
					mp.start();													// music start
					mp_flag = 1;												// music flag change
				}
				mHandler.sendEmptyMessageDelayed(0, 100);
				clear = 0;
				seconds += 1;
				if (seconds == 10 || seconds == 20) {							// 10 or 20 ?
					Count -= 1;													// timer -1
					if (seconds == 20) {
						seconds = 0;
						flag = 1;												// mole change flag
					}
				}
				for (i = 0; i < 9; i++) {										// mole value check
					if (imgArray[i] == 0) clear++;
				}
				for (i = 0; i < 9; i++) {
					if (clear == 9) {											// total mole is zero ?
						imgArray[i] = 0;
						imgMole[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.none));
						flag = 1;
					}
				}

				if (Value < 100 && flag == 1) {									// less than 100 and flag is 1 ?
					for (i = 0; ; ) {
						if (i == 4 || mole_count == 4) break;					// create mole 4
						rValue = Math.random();
						selectedIndex = (int) (rValue * 10); 					// 0 ~ 9
						if (selectedIndex == 9 || selectedIndex == 10)			// exceed the range
							continue;
						if (imgArray[selectedIndex] == 1) continue;				// if overlap ? then continue;
						i += 1;
						mole_count += 1;										// total mole value ++
						imgArray[selectedIndex] = 1;							// mole value setting
						imgMole[selectedIndex].setBackgroundDrawable(getResources().getDrawable(R.drawable.mole));
																				// img change
					}
					flag = 0;
				} else if (Value < 200 && flag == 1) {							// less than 200 and flag is 1 ?
					for (i = 0; ; ) {
						if (i == 6 || mole_count == 6) break;					// create mole 6
						rValue = Math.random();
						selectedIndex = (int) (rValue * 10);
						if (selectedIndex == 9 || selectedIndex == 10)
							continue;
						if (imgArray[selectedIndex] == 1) continue;
						i += 1;
						mole_count += 1;
						imgArray[selectedIndex] = 1;
						imgMole[selectedIndex].setBackgroundDrawable(getResources().getDrawable(R.drawable.mole));
					}
					flag = 0;
				} else if (Value >= 200 && flag == 1) { // more than 200 and flag is 1 ?
					for (i = 0; ; ) {
						if (i == 8 || mole_count == 8) break;	// create mole 8
						rValue = Math.random();
						selectedIndex = (int) (rValue * 10);
						if (selectedIndex == 9 || selectedIndex == 10)
							continue;
						if (imgArray[selectedIndex] == 1) continue;
						i += 1;
						mole_count += 1;
						imgArray[selectedIndex] = 1;
						imgMole[selectedIndex].setBackgroundDrawable(getResources().getDrawable(R.drawable.mole));
					}
					flag = 0;
				} else flag = 0;
			}
		};
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	public void onPause() {
		super.onPause();
		mp.pause();
	}

	public void onResume() {
		super.onResume();
		mp.start();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Play Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.mole_kill/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Play Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.mole_kill/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
	public void onDestroy(){
		mHandler.removeMessages(0);			// handler message delete
		super.onDestroy();

	}
}
