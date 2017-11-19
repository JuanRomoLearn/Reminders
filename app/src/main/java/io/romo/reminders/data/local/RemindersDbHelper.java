/*
 * Copyright 2017 Juan Romo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.romo.reminders.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.romo.reminders.data.local.RemindersContract.ReminderEntry;

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
                ReminderEntry.COLUMN_CREATION_DATE + " TIMESTAMP NOT NULL" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReminderEntry.TABLE_NAME);
        onCreate(db);
    }
}
