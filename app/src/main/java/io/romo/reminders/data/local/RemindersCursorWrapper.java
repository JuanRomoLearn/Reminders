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

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

import io.romo.reminders.model.Reminder;

public class RemindersCursorWrapper extends CursorWrapper {

    public RemindersCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Reminder getReminder() {
        int idIndex = getColumnIndex(RemindersContract.ReminderEntry._ID);
        int titleIndex = getColumnIndex(RemindersContract.ReminderEntry.COLUMN_TITLE);
        int descriptionIndex = getColumnIndex(RemindersContract.ReminderEntry.COLUMN_DESCRIPTION);
        int priorityIndex = getColumnIndex(RemindersContract.ReminderEntry.COLUMN_PRIORITY);
        int completedIndex = getColumnIndex(RemindersContract.ReminderEntry.COLUMN_COMPLETED);
        int creationDateIndex = getColumnIndex(RemindersContract.ReminderEntry.COLUMN_CREATION_DATE);

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
