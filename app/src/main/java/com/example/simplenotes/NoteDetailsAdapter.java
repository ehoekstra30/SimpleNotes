package com.example.simplenotes;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.simplenotes.NoteDatabaseContract.NoteDatabase;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteDetailsAdapter extends RecyclerView.Adapter<NoteDetailsAdapter.NoteViewHolder> {

    List<NoteDetails> noteDetailsList;
    Context context;
    NoteDatabaseDAL dbHelper;
    SQLiteDatabase db;

    public NoteDetailsAdapter(List<NoteDetails> noteDetailsList)
    {
        this.noteDetailsList = noteDetailsList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item,parent,false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, final int position){

        NoteDetails noteDetails = noteDetailsList.get(position);

        holder.noteTitle.setText(noteDetails.getTitle());
        holder.noteContent.setText(noteDetails.getContent());

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NoteDetails noteDetails = noteDetailsList.get(position);
                final int noteID = noteDetails.getNoteID();
                dbHelper = new NoteDatabaseDAL(context);
                db = dbHelper.getWritableDatabase();
                PopupMenu menu = new PopupMenu(context,holder.ivMenu);

                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.delete:
                                db.delete(NoteDatabase.TABLE_NAME,NoteDatabase._ID + " = " + noteID,null);
                                notifyItemRangeChanged(position,noteDetailsList.size());
                                noteDetailsList.remove(position);
                                notifyItemRemoved(position);
                                db.close();
                                break;
                            case R.id.update:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("NOTEID", noteID);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteDetailsList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle, noteContent;

        ImageView ivMenu;

        public NoteViewHolder(View itemView){

        super(itemView);

            noteTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            noteContent = (TextView) itemView.findViewById(R.id.noteContent);

            ivMenu = (ImageView)itemView.findViewById(R.id.iv_menu);

        }
    }
}
