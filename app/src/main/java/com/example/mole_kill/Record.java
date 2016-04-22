//////////////////////////////////////////////////////////////////////////
// File Name	: Record.java											//
// Date	 		: 2013.12.05											//
// Compiler 	: Android Studio 1.5.1									//
// Os	 		: Window 7												//
// Author		: Kim kyung min											//
//----------------------------------------------------------------------//
// ver			: 1.0.11												//
// Description	: mole killing game										//
//////////////////////////////////////////////////////////////////////////
package com.example.mole_kill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Record extends Activity{
	Context context;
	SQLiteDatabase myDB;
	String score;
	int value;
	ScoreData scoreDB;
	TextView[] txtName;
	TextView[] txtScore;

	public void inputInform(){
		LinearLayout localLinearLayout = (LinearLayout)View.inflate(this, R.layout.input, null);
		final View c_layout = (View)localLinearLayout;
		((TextView)localLinearLayout.findViewById(R.id.inputtxtAlarm)).setText("You got the "+this.score + " point.");
		AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
		aDialog.setTitle("Insert the score");
		aDialog.setIcon(R.drawable.gum);
		aDialog.setView(c_layout);
		final EditText eName = (EditText)c_layout.findViewById(R.id.edtName);
		aDialog.setPositiveButton("Identify", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
                // identify button click?
				Record.this.myDB.execSQL("INSERT INTO Ranking (Name, Score)VALUES ('" + eName.getText() + "', '" + Record.this.value + "');");
                // database insert
				Record.this.setting();
				Record.this.myDB.close();
				Record.this.scoreDB.close();
			}
		}).setView(localLinearLayout).show();

	}
	protected void onCreate(Bundle paramBundle){
		super.onCreate(paramBundle);
		setContentView(R.layout.score);
		this.context = this;

		final Button btnReset2 = (Button)findViewById(R.id.ScoReset2);
		final Button btnReset = (Button)findViewById(R.id.ScoReset);
		final Button btnReturn = (Button)findViewById(R.id.ScoReturn);
		this.txtName = new TextView[10];
		this.txtScore = new TextView[10];
		this.txtName[0] = ((TextView)findViewById(R.id.txtName1));
		this.txtName[1] = ((TextView)findViewById(R.id.txtName2));
		this.txtName[2] = ((TextView)findViewById(R.id.txtName3));
		this.txtName[3] = ((TextView)findViewById(R.id.txtName4));
		this.txtName[4] = ((TextView)findViewById(R.id.txtName5));
		this.txtName[5] = ((TextView)findViewById(R.id.txtName6));
		this.txtName[6] = ((TextView)findViewById(R.id.txtName7));
		this.txtName[7] = ((TextView)findViewById(R.id.txtName8));
		this.txtName[8] = ((TextView)findViewById(R.id.txtName9));
		this.txtName[9] = ((TextView)findViewById(R.id.txtName10));
		this.txtScore[0] = ((TextView)findViewById(R.id.txtScore1));
		this.txtScore[1] = ((TextView)findViewById(R.id.txtScore2));
		this.txtScore[2] = ((TextView)findViewById(R.id.txtScore3));
		this.txtScore[3] = ((TextView)findViewById(R.id.txtScore4));
		this.txtScore[4] = ((TextView)findViewById(R.id.txtScore5));
		this.txtScore[5] = ((TextView)findViewById(R.id.txtScore6));
		this.txtScore[6] = ((TextView)findViewById(R.id.txtScore7));
		this.txtScore[7] = ((TextView)findViewById(R.id.txtScore8));
		this.txtScore[8] = ((TextView)findViewById(R.id.txtScore9));
		this.txtScore[9] = ((TextView)findViewById(R.id.txtScore10));
		this.score = getIntent().getStringExtra("score");               // transmit data get
		this.value = Integer.parseInt(score);
		btnReset2.setOnClickListener(new View.OnClickListener() {       // reset2 button click?
			public void onClick(View v) {
                int i = 0;
                Record.this.scoreDB = new ScoreData(Record.this, null, null, 0);
                Record.this.myDB = Record.this.scoreDB.getReadableDatabase();
                // database get
                Record.this.myDB.execSQL("delete from Ranking where 1");
                // all data delete
                for(;i<10;i++){
                    Record.this.txtName[i].setText("--------");
                    Record.this.txtScore[i].setText("----------");
                    // resetting
                }
                Record.this.myDB.close();
                Record.this.scoreDB.close();
			}
		});
		btnReset.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Log.d("click", "resetbutton");
				Record.this.startActivity(new Intent(Record.this,Play.class));
				finish(); // add position
			}
		});
	    btnReturn.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		setting();
		inputInform();
	}

	public void setting(){
		this.scoreDB = new ScoreData(this, null, null, 0);
		this.myDB = this.scoreDB.getReadableDatabase(); // database get
		Cursor localCursor = this.myDB.rawQuery("SELECT * FROM Ranking ORDER BY Score DESC", null);
		if (localCursor != null){
			boolean bool = localCursor.moveToFirst();
			int i = 0;
			if (bool) {
                do {
                    if (i < 10) {
                        this.txtName[i].setText(localCursor.getString(1));
                        this.txtScore[i].setText(localCursor.getString(2));
                        i++;
                    }
                }while (localCursor.moveToNext());
            }
		}
	}
}