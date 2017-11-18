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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import io.romo.reminders.R;
import io.romo.reminders.model.Reminder;
import io.romo.reminders.ui.common.EmptyStateRecyclerView;

public class RemindersFragment extends Fragment {

    @BindView(R.id.reminders) EmptyStateRecyclerView reminders;
    @BindView(R.id.empty_view) View emptyView;
    private RemindersAdapter adapter;

    @BindView(R.id.complete) CheckBox complete;
    @BindView(R.id.title) EditText title;
    @BindView(R.id.priority) ImageView priority;

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

        return view;
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
        // TODO insert reminder into database

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
