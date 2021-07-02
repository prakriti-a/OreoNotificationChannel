package com.prakriti.oreonotificationchannel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationHandler extends ContextWrapper {
    // extends from Context - used to get info reg another part of the program

    private NotificationManager notificationManager;
    // Channel ID & Name
    public static final String MOVIE_CHANNEL_ID = "WatchMovieChannelID";
    public static final String MOVIE_CHANNEL_NAME = "WatchMovieChannelName";


    public NotificationHandler(Context base) {
        super(base);
        createNotificationChannel(); // must call this
    }


    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(MOVIE_CHANNEL_ID, MOVIE_CHANNEL_NAME, notificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true); // display lights on supported devices
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); // appear on lock screen
        notificationChannel.setShowBadge(true); // appear as app icon badges in a launcher

        // if the above settings are changed by user on their device - then above specifics are overridden by user's selection
        getNotificationManager().createNotificationChannel(notificationChannel);
    }

    public Notification.Builder createAndReturnNotif(String title, String body) {
        return new Notification.Builder(getApplicationContext(), MOVIE_CHANNEL_ID) // context & ID
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.watch_movie) // diff between vector & image asset?
                .setAutoCancel(true); // dismiss when user touches
    }

    public void notifyTheUser(int notifID, Notification.Builder builder) {
        getNotificationManager().notify(notifID, builder.build()); // build() returns Notification object
    }
}
