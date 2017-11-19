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

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import io.romo.reminders.model.Reminder;
import io.romo.reminders.util.ReminderUtils;

public class RemindersLocalDataSource {

    public static void addReminder(Context context, Reminder reminder) {
        ContentValues values = ReminderUtils.getContentValuesFromNewReminder(reminder);
        Uri resultUri = context.getContentResolver().insert(RemindersContract.ReminderEntry.buildRemindersUri(), values);

        long id = ContentUris.parseId(resultUri);
        reminder.setId(id);
    }

    public static List<Reminder> getReminders(Context context) {
        Cursor cursor = context.getContentResolver().query(
                RemindersContract.ReminderEntry.buildRemindersUri(),
                null,
                null,
                null,
                RemindersContract.ReminderEntry.COLUMN_CREATION_DATE + " ASC");

        RemindersCursorWrapper cursorWrapper = new RemindersCursorWrapper(cursor);

        List<Reminder> reminderList = new ArrayList<>();
        while (cursorWrapper.moveToNext()) {
            reminderList.add(cursorWrapper.getReminder());
        }
        cursorWrapper.close();

        return reminderList;
    }

    public static void updateReminder(Context context, Reminder reminder) {
        ContentValues values = ReminderUtils.getContentValuesFromReminder(reminder);
        Uri uri = RemindersContract.ReminderEntry.buildReminderUriWith(reminder.getId());
        context.getContentResolver().update(
                uri,
                values,
                null,
                null);
    }
}
