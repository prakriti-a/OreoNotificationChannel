package com.prakriti.oreonotificationchannel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // instance vars
    private NotificationHandler notificationHandler;
    private EditText edtMovieName;
    // Notification ID
    private static final int MOVIE_NOTIF_ID = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationHandler = new NotificationHandler(this);

        edtMovieName = findViewById(R.id.edtMovieName);

        findViewById(R.id.btnMovieNotif).setOnClickListener(this);
        findViewById(R.id.btnNotifSettings).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMovieNotif:
                if(edtMovieName.getText().toString().equals("")) {
                    edtMovieName.setError("Cannot be blank");
                    return;
                }
                postNotifToDevice(MOVIE_NOTIF_ID, edtMovieName.getText().toString());
                break;
            case R.id.btnNotifSettings:
                // open settings for notif for this app
                openNotifSettingsForChannel(NotificationHandler.MOVIE_CHANNEL_ID); // static ID
                break;
        }
    }

    private void postNotifToDevice(int notificationID, String title) {
        Notification.Builder builder = null;

        if(notificationID == MOVIE_NOTIF_ID) {
            builder = notificationHandler.createAndReturnNotif(title, "Added to Watchlist");
        }
        if(builder != null) {
            notificationHandler.notifyTheUser(MOVIE_NOTIF_ID, builder);
        }
    }

    private void openNotifSettingsForChannel(String channelID) {
        Intent settings = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        settings.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName()); // send package name
        settings.putExtra(Settings.EXTRA_CHANNEL_ID, channelID);
        startActivity(settings);
    }
}