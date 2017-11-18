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

package io.romo.reminders.model;

import android.support.annotation.ColorRes;

import java.util.Date;

import io.romo.reminders.R;

public class Reminder {

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    private Date creationDate;

    public enum Priority {
        NONE(R.color.no_priority),
        LOW(R.color.low_priority),
        MEDIUM(R.color.medium_priority),
        HIGH(R.color.high_priority) {
            @Override
            public Priority next() {
                return values()[0];
            }
        };

        @ColorRes
        private int color;

        Priority(int color) {
            this.color = color;
        }

        @ColorRes
        public int getColor() {
            return color;
        }

        public Priority next() {
            return values()[ordinal() + 1];
        }
    }

    public Reminder(String title, Priority priority, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.completed = completed;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Reminder(int id, String title, String description, Priority priority, boolean completed,
                    Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
