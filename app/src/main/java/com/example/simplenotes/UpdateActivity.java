package com.example.simplenotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.simplenotes.NoteDatabaseContract.NoteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    NoteDatabaseHelper dbHelper;
    SQLiteDatabase db;

    List<NoteDetails> noteDetailsList;

    EditText titleBox, contentBox;

    String title, content;

    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_activity);
        dbHelper = new NoteDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        titleBox = (EditText) findViewById(R.id.titleEditText);
        contentBox = (EditText) findViewById(R.id.contentEditText);

        final int rowID = getIntent().getIntExtra("NOTEID",-1);

        Cursor c1 = db.query(NoteDatabase.TABLE_NAME,null,NoteDatabase._ID + " = " + rowID,null,null,null,null);

        noteDetailsList =new ArrayList<NoteDetails>();

        noteDetailsList.clear();;

        if(c1 != null &&c1.getCount() != 0 ){

            while(c1.moveToNext()){

                titleBox.setText(c1.getString(c1.getColumnIndex(NoteDatabase.COLUMN_NAME_COL1)));
                contentBox.setText(c1.getString(c1.getColumnIndex(NoteDatabase.COLUMN_NAME_COL2)));


            }
            btnUpdate.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    title = titleBox.getText().toString();
                    content = contentBox.getText().toString();

                    ContentValues values = new ContentValues();

                    values.put(NoteDatabase.COLUMN_NAME_COL1, title);
                    values.put(NoteDatabase.COLUMN_NAME_COL2, content);

                    int updateID = db.update(NoteDatabase.TABLE_NAME,values,NoteDatabase._ID + " = " + rowID,null);

                    if(updateID != -1){
                        Toast.makeText(UpdateActivity.this,"Note Updated",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(UpdateActivity.this, "oh god oh fuck", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
