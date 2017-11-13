package io.romo.reminders.ui.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.romo.reminders.R;
import io.romo.reminders.util.ActivityUtils;

public class RemindersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        RemindersFragment remindersFragment =
                (RemindersFragment) getSupportFragmentManager().findFragmentById(R.id.reminders_container);
        if (remindersFragment == null) {
            remindersFragment = new RemindersFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), remindersFragment, R.id.reminders_container);
        }
    }
}
