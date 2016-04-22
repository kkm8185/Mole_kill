//////////////////////////////////////////////////////////////////////////
// File Name	: Score.java											//
// Date	 		: 2013.12.05											//
// Compiler 	: Android Studio 1.5.1									//
// Os	 		: Window 7												//
// Author		: Kim kyung min											//
//----------------------------------------------------------------------//
// ver			: 1.0.9													//
// Description	: mole killing game										//
//////////////////////////////////////////////////////////////////////////
package com.example.mole_kill;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends Activity
{
	SQLiteDatabase myDB;
	ScoreData scoreDB;
	TextView[] txtName;
	TextView[] txtScore;

	protected void onCreate(Bundle paramBundle){
	    super.onCreate(paramBundle);
	    setContentView(R.layout.score);
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
	    this.scoreDB = new ScoreData(this, null, null, 0);
	    this.myDB = this.scoreDB.getReadableDatabase();
	    Cursor localCursor = this.myDB.rawQuery("select * from Ranking order by Score desc", null);
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
                } while (localCursor.moveToNext());
            }
	    }
	    if (this.myDB != null)    	this.myDB.close();
		btnReset2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                int i = 0;
                Score.this.scoreDB = new ScoreData(Score.this, null, null, 0);
                Score.this.myDB = Score.this.scoreDB.getReadableDatabase();
                Score.this.myDB.execSQL("delete from Ranking where 1");
                for(;i<10;i++){
                    Score.this.txtName[i].setText("--------");
                    Score.this.txtScore[i].setText("----------");
                }
				Score.this.myDB.close();
				Score.this.scoreDB.close();
			}
		});
	    btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Score.this.startActivity(new Intent(Score.this, Play.class));
                finish(); // exit
            }
        });
	    btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
	}
}