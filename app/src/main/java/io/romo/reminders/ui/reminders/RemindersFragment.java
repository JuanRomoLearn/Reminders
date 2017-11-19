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

package io.romo.reminders.ui.reminders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.romo.reminders.R;
import io.romo.reminders.data.local.RemindersLocalDataSource;
import io.romo.reminders.model.Reminder;
import io.romo.reminders.ui.common.EmptyViewRecyclerView;

public class RemindersFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Reminder>> {

    private static final int REMINDERS_LOADER = 1;

    @BindView(R.id.reminders) EmptyViewRecyclerView reminders;
    @BindView(R.id.empty_view) View emptyView;
    @BindView(R.id.complete) CheckBox complete;
    @BindView(R.id.title) EditText title;
    @BindView(R.id.priority) ImageView priority;

    private RemindersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        ButterKnife.bind(this, view);

        reminders.setHasFixedSize(true);

        reminders.setEmptyView(emptyView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reminders.setLayoutManager(layoutManager);

        adapter = new RemindersAdapter(null);
        reminders.setAdapter(adapter);

        priority.setTag(R.id.PRIORITY, Reminder.Priority.NONE);

        getActivity().getSupportLoaderManager().initLoader(REMINDERS_LOADER, null, this);

        return view;
    }

    @Override
    public Loader<List<Reminder>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Reminder>>(getActivity()) {

            List<Reminder> reminderList;

            @Override
            protected void onStartLoading() {
                if (reminderList != null) {
                    deliverResult(reminderList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Reminder> loadInBackground() {
                return RemindersLocalDataSource.getReminders(getActivity());
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Reminder>> loader, List<Reminder> data) {
        adapter.setReminderList(data);
        title.setEnabled(true);
    }

    @Override
    public void onLoaderReset(Loader<List<Reminder>> loader) {

    }

    @OnClick(R.id.priority)
    void priorityOnClick() {
        Reminder.Priority currentPriority = (Reminder.Priority) priority.getTag(R.id.PRIORITY);

        Reminder.Priority nextPriority = currentPriority.next();
        int priorityColor = ContextCompat.getColor(getActivity(), nextPriority.getColor());

        priority.setColorFilter(priorityColor);
        priority.setTag(R.id.PRIORITY, nextPriority);
    }

    @OnEditorAction(R.id.title)
    boolean titleOnEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE && validEntry()) {
            submitReminder();
        }

        return false;
    }

    private boolean validEntry() {
        return !title.getText().toString().isEmpty();
    }

    private void submitReminder() {
        Reminder reminder = new Reminder(title.getText().toString(),
                                         (Reminder.Priority) priority.getTag(R.id.PRIORITY),
                                         complete.isChecked());
        RemindersLocalDataSource.addReminder(getActivity(), reminder);

        adapter.addReminder(reminder);

        resetInput();
    }

    private void resetInput() {
        complete.setChecked(false);
        complete.jumpDrawablesToCurrentState();

        title.setText("");

        int noPriorityColor = ContextCompat.getColor(getActivity(), Reminder.Priority.NONE.getColor());
        priority.setColorFilter(noPriorityColor);
        priority.setTag(R.id.PRIORITY, Reminder.Priority.NONE);
    }
}
