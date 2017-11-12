package io.romo.reminders.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

import io.romo.reminders.model.Reminder;

import static io.romo.reminders.data.RemindersContract.ReminderEntry.*;

public class RemindersCursorWrapper extends CursorWrapper {

    public RemindersCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Reminder getReminder() {
        int idIndex = getColumnIndex(_ID);
        int titleIndex = getColumnIndex(COLUMN_TITLE);
        int descriptionIndex = getColumnIndex(COLUMN_DESCRIPTION);
        int priorityIndex = getColumnIndex(COLUMN_PRIORITY);
        int completedIndex = getColumnIndex(COLUMN_COMPLETED);
        int creationDateIndex = getColumnIndex(COLUMN_CREATION_DATE);

        return new Reminder(
                getInt(idIndex),
                getString(titleIndex),
                getString(descriptionIndex),
                Reminder.Priority.valueOf(getString(priorityIndex)),
                getInt(completedIndex) != 0,
                new Date(getLong(creationDateIndex))
        );
    }
}
