package com.example.simplenotes;

import android.provider.BaseColumns;

public class NoteDatabaseContract {

    public NoteDatabaseContract() {
    }

    public static class NoteDatabase implements BaseColumns {

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_COL1 = "title";
        public static final String COLUMN_NAME_COL2 = "content";

    }}
