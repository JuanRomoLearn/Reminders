package io.romo.reminders.data;

import android.provider.BaseColumns;

public class RemindersContract {

    public static final class ReminderEntry implements BaseColumns {

        public static final String TABLE_NAME = "reminders";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_CREATION_DATE = "creationDate";
    }
}
