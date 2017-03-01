package com.example.user.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by user on 3/1/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "TextDatabase";
    public static int VERSION_NUM = 1;
    public static String TABLE_NAME = "ChatTable";
    public static String MESSAGE_COLUMN = "Message";
    public static String ID_COLUMN = "_id";

    public ChatDatabaseHelper(Context ctx){
        super(ctx,TABLE_NAME,null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "onCreate Called");
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("+ ID_COLUMN + " INTEGER PRIMARY KEY, " + MESSAGE_COLUMN +" VARCHAR(256));" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion="+oldVersion+" newVersion="+newVersion);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
