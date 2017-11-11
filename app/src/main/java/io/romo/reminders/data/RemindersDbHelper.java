package io.romo.reminders.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.romo.reminders.data.RemindersContract.ReminderEntry;

public class RemindersDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminders.db";

    private static final int DATABASE_VERSION = 1;

    public RemindersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ReminderEntry.TABLE_NAME + " (" +
                ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReminderEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                ReminderEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL DEFAULT ''," +
                ReminderEntry.COLUMN_PRIORITY + " TEXT NOT NULL," +
                ReminderEntry.COLUMN_COMPLETED + " INTEGER NOT NULL," +
                ReminderEntry.COLUMN_CREATION_DATE + " INTEGER NOT NULL" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReminderEntry.TABLE_NAME);
        onCreate(db);
    }
}
