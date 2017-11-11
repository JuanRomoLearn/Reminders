package io.romo.reminders.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class RemindersContract {

    public static final String AUTHORITY = "io.romo.reminders";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_REMINDERS = "reminders";

    public static final class ReminderEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REMINDERS).build();

        public static final String TABLE_NAME = "reminders";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_CREATION_DATE = "creationDate";
    }
}
