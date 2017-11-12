package io.romo.reminders.util;

import android.content.ContentValues;

import io.romo.reminders.model.Reminder;

import static io.romo.reminders.data.RemindersContract.ReminderEntry.*;

public class ReminderUtils {

    public static ContentValues getContentValuesFromNewReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, reminder.getTitle());
        values.put(COLUMN_PRIORITY, reminder.getPriority().name());
        values.put(COLUMN_COMPLETED, reminder.isCompleted() ? 1 : 0);
        values.put(COLUMN_CREATION_DATE, reminder.getCreationDate().getTime());
        return values;
    }

    public static ContentValues getContentValuesFromReminder(Reminder reminder) {
        ContentValues values = getContentValuesFromNewReminder(reminder);
        values.put(_ID, reminder.getId());
        return values;
    }
}
