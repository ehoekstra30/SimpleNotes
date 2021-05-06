package com.example.simplenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import com.example.simplenotes.NoteDatabaseContract.NoteDatabase;

public class MainActivity extends AppCompatActivity {

    NoteDatabaseDAL dbHelper;
    SQLiteDatabase db;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter noteAdapter;
    private RecyclerView.LayoutManager layoutManager;


    Button btnRegister;
    List<NoteDetails> noteDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDatabaseDAL(this);
        db = dbHelper.getReadableDatabase();

        //view/button declarations
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        btnRegister = (Button) findViewById(R.id.bt_register);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        noteDetailsList = new ArrayList<NoteDetails>();

        noteDetailsList.clear();

        Cursor c1 = db.query(NoteDatabase.TABLE_NAME, null, null, null, null, null, null);
        if(c1 != null && c1.getCount() != 0){

            noteDetailsList.clear();
            while(c1.moveToNext()) {
                NoteDetails noteDetailsItem = new NoteDetails();
                noteDetailsItem.setNoteID(c1.getInt(c1.getColumnIndex(NoteDatabase._ID)));
                noteDetailsItem.setTitle(c1.getString(c1.getColumnIndex(NoteDatabase.COLUMN_NAME_COL1)));
                noteDetailsItem.setContent(c1.getString(c1.getColumnIndex(NoteDatabase.COLUMN_NAME_COL2)));
                noteDetailsList.add(noteDetailsItem);
            }
        }
        c1.close();

        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteDetailsAdapter(noteDetailsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);

    }
    @Override
    protected void onDestroy()
    {
        db.close();
        super.onDestroy();
    }
}
