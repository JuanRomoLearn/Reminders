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

package io.romo.reminders.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import io.romo.reminders.data.local.RemindersContract;
import io.romo.reminders.data.local.RemindersDbHelper;

public class RemindersProvider extends ContentProvider {

    private static final int REMINDERS = 100;
    private static final int REMINDER_WITH_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        String authority = RemindersContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, RemindersContract.ReminderEntry.TABLE_NAME, REMINDERS);
        matcher.addURI(authority, RemindersContract.ReminderEntry.TABLE_NAME + "/#", REMINDER_WITH_ID);

        return matcher;
    }

    private RemindersDbHelper remindersDbHelper;

    @Override
    public boolean onCreate() {
        remindersDbHelper = new RemindersDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = remindersDbHelper.getReadableDatabase();

        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String _id = uri.getPathSegments().get(1);

                retCursor = db.query(
                        RemindersContract.ReminderEntry.TABLE_NAME,
                        projection,
                        RemindersContract.ReminderEntry._ID + "=?",
                        new String[]{_id},
                        null,
                        null,
                        sortOrder);
                break;
            case REMINDERS:
                retCursor = db.query(
                        RemindersContract.ReminderEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        Uri retUri;

        switch (uriMatcher.match(uri)) {
            case REMINDERS:
                long _id = db.insert(RemindersContract.ReminderEntry.TABLE_NAME, null, values);

                if (_id >= 0) {
                    retUri = RemindersContract.ReminderEntry.buildReminderUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        int remindersDeleted;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String _id = uri.getPathSegments().get(1);

                remindersDeleted = db.delete(
                        RemindersContract.ReminderEntry.TABLE_NAME,
                        RemindersContract.ReminderEntry._ID + "=?",
                        new String[]{_id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (remindersDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return remindersDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        int remindersUpdated;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String _id = uri.getPathSegments().get(1);

                remindersUpdated = db.update(
                        RemindersContract.ReminderEntry.TABLE_NAME,
                        values,
                        RemindersContract.ReminderEntry._ID + "=?",
                        new String[]{_id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (remindersUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return remindersUpdated;
    }
}
