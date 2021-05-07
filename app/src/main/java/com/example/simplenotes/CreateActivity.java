package com.example.simplenotes;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplenotes.NoteDatabaseContract.NoteDatabase;



public class RegistrationActivity extends AppCompatActivity
{
    NoteDatabaseDAL dbHelper;
    SQLiteDatabase db;

    String title, content;

    private EditText titleEditText, contentEditText;

    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setting content view/instance state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        dbHelper = new NoteDatabaseDAL(this);
        db = dbHelper.getWritableDatabase();

        titleEditText = (EditText)findViewById(R.id.et_title);
        contentEditText = (EditText)findViewById(R.id.et_content);

        btnRegister = (Button) findViewById(R.id.bt_register);

        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                title = titleEditText.getText().toString();
                content = contentEditText.getText().toString();

                ContentValues values = new ContentValues();

                values.put(NoteDatabase.COLUMN_NAME_COL1,title);
                values.put(NoteDatabase.COLUMN_NAME_COL2,content);

                long rowID = db.insert(NoteDatabase.TABLE_NAME,null,values);

                if(rowID != -1){

                    Toast.makeText(RegistrationActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "oh god oh fuck", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //FUNCTION      : onDestroy
    //PARAMETERS    : void
    //RETURNS       : void
    //DESCRIPTION   : Closes the db upon the destruction of the page
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
