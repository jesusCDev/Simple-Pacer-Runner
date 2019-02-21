package com.allvens.simplepacerrunner.settings_manager.Notification_Manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.allvens.simplepacerrunner.MainActivity;
import com.allvens.simplepacerrunner.R;
import com.allvens.simplepacerrunner.data_manager.Prefs_Values;
import com.allvens.simplepacerrunner.settings_manager.SettingsPrefs_Manager;

import java.util.Calendar;

public class Notification_Receiver extends BroadcastReceiver {

    private int MID = 101;
    private String ANDROID_CHANNEL_ID = "com.android.simplepacerrunner";
    private String ANDROID_CHANNEL_NAME = "Simple Pacer Runner";

    @Override
    public void onReceive(Context context, Intent intent) {

        SettingsPrefs_Manager settingsPrefs = new SettingsPrefs_Manager(context);

        /********** Sets up repeat function for broadcaster for set time **********/
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                Notification_Controller notiManager = new Notification_Controller(context, settingsPrefs.get_PrefSetting(Prefs_Values.NOTIFICATION_ON),
                                settingsPrefs.get_NotifiHour(), settingsPrefs.get_NotifiMinute());
                notiManager.create_Notification();
            }
        }

        /********** Runs Only On Days Selected **********/

        Calendar rightNow = Calendar.getInstance();
        int rightNowHour = rightNow.get(Calendar.HOUR);
        int rightNowMin = rightNow.get(Calendar.MINUTE);

        if(settingsPrefs.get_NotificationDayValue((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1)) && (rightNowHour == settingsPrefs.get_NotifiHour()) && (rightNowMin == settingsPrefs.get_NotifiMinute())){
            present_Notification(context);
        }
    }

    private void present_Notification(Context context){
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder mNotifyBuilder = getAndroidChannelNotification(context);
        notificationManager.notify(MID, mNotifyBuilder.build());
    }

    private Notification.Builder getAndroidChannelNotification(Context context) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, MID,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            return new Notification.Builder(context.getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(ANDROID_CHANNEL_NAME)
                    .setContentText("Ready To Start?!")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_drag_handle_grey_24dp)
                    .setAutoCancel(true);
        }else{
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long when = System.currentTimeMillis();

            return new Notification.Builder(
                    context).setSmallIcon(R.drawable.ic_drag_handle_grey_24dp)
                    .setContentTitle(ANDROID_CHANNEL_NAME)
                    .setContentText("Ready To Start?!")
                    .setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        }
    }
}
