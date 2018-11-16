package org.development.aihd.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.development.aihd.app.AppController;
import org.development.aihd.app.ScheduledReceiver;

import java.util.Calendar;

public class LoadAlarm extends IntentService {
    public LoadAlarm() {
        super(LoadAlarm.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        loadAlarm();
    }


    public void loadAlarm() {

        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        long interval = 1000 * 60 * 480; // 8 hrs in milliseconds
        Intent serviceIntent = new Intent(getApplicationContext(), ScheduledReceiver.class);
        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        PendingIntent servicePendingIntent =
                PendingIntent.getBroadcast(getApplicationContext(),
                        0, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
        // there are other options like setInexactRepeating, check the docs
        assert am != null;
        am.setRepeating(
                AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                cal.getTimeInMillis(),
                interval,
                servicePendingIntent
        );
    }
}

