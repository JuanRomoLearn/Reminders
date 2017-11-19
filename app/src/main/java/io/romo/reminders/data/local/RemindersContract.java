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
import android.net.Uri;
import android.provider.BaseColumns;

import io.romo.reminders.BuildConfig;

public class RemindersContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ReminderEntry implements BaseColumns {

        public static final String TABLE_NAME = "reminders";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_CREATION_DATE = "creationDate";

        public static final Uri CONTENT_REMINDER_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildReminderUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_REMINDER_URI, id);
        }

        public static Uri buildRemindersUri() {
            return CONTENT_REMINDER_URI.buildUpon().build();
        }
    }
}
