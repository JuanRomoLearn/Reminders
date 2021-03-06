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

package io.romo.reminders.util;

import android.content.ContentValues;

import io.romo.reminders.data.local.RemindersContract;
import io.romo.reminders.model.Reminder;

public class ReminderUtils {

    public static ContentValues getContentValuesFromNewReminder(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(RemindersContract.ReminderEntry.COLUMN_TITLE, reminder.getTitle());
        values.put(RemindersContract.ReminderEntry.COLUMN_PRIORITY, reminder.getPriority().name());
        values.put(RemindersContract.ReminderEntry.COLUMN_COMPLETED, reminder.isCompleted() ? 1 : 0);
        values.put(RemindersContract.ReminderEntry.COLUMN_CREATION_DATE, reminder.getCreationDate().getTime());
        return values;
    }

    public static ContentValues getContentValuesFromReminder(Reminder reminder) {
        ContentValues values = getContentValuesFromNewReminder(reminder);
        values.put(RemindersContract.ReminderEntry._ID, reminder.getId());
        return values;
    }
}
