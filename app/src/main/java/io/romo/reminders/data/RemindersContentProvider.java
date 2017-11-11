package io.romo.reminders.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import io.romo.reminders.data.RemindersContract.ReminderEntry;

import static io.romo.reminders.data.RemindersContract.AUTHORITY;
import static io.romo.reminders.data.RemindersContract.PATH_REMINDERS;

public class RemindersContentProvider extends ContentProvider {

    private static final int REMINDERS = 100;
    private static final int REMINDER_WITH_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_REMINDERS, REMINDERS);
        uriMatcher.addURI(AUTHORITY, PATH_REMINDERS + "/#", REMINDER_WITH_ID);
    }

    private RemindersDbHelper remindersDbHelper;

    @Override
    public boolean onCreate() {
        remindersDbHelper = new RemindersDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = remindersDbHelper.getReadableDatabase();

        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                retCursor = db.query(ReminderEntry.TABLE_NAME,
                        projection,
                        ReminderEntry._ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
                break;
            case REMINDERS:
                retCursor = db.query(ReminderEntry.TABLE_NAME,
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
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        Uri retUri;

        switch (uriMatcher.match(uri)) {
            case REMINDERS:
                long id = db.insert(ReminderEntry.TABLE_NAME, null, values);

                if (id >= 0) {
                    retUri = ContentUris.withAppendedId(ReminderEntry.CONTENT_URI, id);
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
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        int remindersDeleted;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                remindersDeleted = db.delete(ReminderEntry.TABLE_NAME,
                        ReminderEntry._ID + "=?", new String[]{id});
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
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = remindersDbHelper.getWritableDatabase();

        int remindersUpdated;

        switch (uriMatcher.match(uri)) {
            case REMINDER_WITH_ID:
                String id = uri.getPathSegments().get(1);

                remindersUpdated = db.update(ReminderEntry.TABLE_NAME, values,
                        ReminderEntry._ID + "=?", new String[]{id});
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
