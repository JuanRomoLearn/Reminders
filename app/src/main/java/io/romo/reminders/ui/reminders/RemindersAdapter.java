package io.romo.reminders.ui.reminders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.romo.reminders.R;
import io.romo.reminders.model.Reminder;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.RemindersViewHolder> {

    private List<Reminder> reminderList;

    public RemindersAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public RemindersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reminder, parent, false);
        return new RemindersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RemindersViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.bindReminder(reminder);
    }

    @Override
    public int getItemCount() {
        return reminderList == null ? 0 : reminderList.size();
    }

    class RemindersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.priority_strip) View priorityStrip;
        @BindView(R.id.complete) CheckBox complete;
        @BindView(R.id.title) TextView title;

        private Reminder reminder;

        public RemindersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindReminder(Reminder reminder) {
            this.reminder = reminder;
            priorityStrip.setBackgroundResource(reminder.getPriority().getColor());
            complete.setChecked(reminder.isCompleted());
            title.setText(reminder.getTitle());
        }
    }
}
