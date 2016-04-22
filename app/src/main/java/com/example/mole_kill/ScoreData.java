//////////////////////////////////////////////////////////////////////////
// File Name	: ScoreData.java										//
// Date	 		: 2013.12.05											//
// Compiler 	: Android Studio 1.5.1									//
// Os	 		: Window 7												//
// Author		: Kim kyung min											//
//----------------------------------------------------------------------//
// ver			: 1.0.1													//
// Description	: mole killing game										//
//////////////////////////////////////////////////////////////////////////
package com.example.mole_kill;
import android.util.Log;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreData extends SQLiteOpenHelper{
	public ScoreData(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
	{
		super(paramContext, "List.db", null, 1);
	}
	public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	{
		paramSQLiteDatabase.execSQL("Create table Ranking ( _id integer primary key autoincrement, Name text not null, Score numeric not null);");
	}
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	{
		paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS Ranking");
		onCreate(paramSQLiteDatabase);
	}
}
