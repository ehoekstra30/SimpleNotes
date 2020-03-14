package com.example.simplenotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.simplenotes.NoteDatabaseContract.NoteDatabase;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";


    public NoteDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL(DELETE_USER_TABLE);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + NoteDatabase.TABLE_NAME +
            "( " + NoteDatabase._ID + " INTEGER PRIMARY KEY," +
            NoteDatabase.COLUMN_NAME_COL1 + " text," +
            NoteDatabase.COLUMN_NAME_COL2 + " text)";

    private static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + NoteDatabase.TABLE_NAME;


}
