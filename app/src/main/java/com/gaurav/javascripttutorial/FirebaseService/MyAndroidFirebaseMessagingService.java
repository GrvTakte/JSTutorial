package com.gaurav.javascripttutorial.FirebaseService;

/**
 * Created by gaurav on 13/10/17.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gaurav.javascripttutorial.MainActivity;
import com.gaurav.javascripttutorial.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyAndroidFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification
        createNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("title"));
    }

    private void createNotification(String messageBody, String title) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSmallIcon(R.mipmap.logo);

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setLights(Color.GRAY,3000,3000);
        builder.setSound(uri);
        builder.setContentTitle("JS Tutorial");
        builder.setContentText(messageBody);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setSubText("Tap to open notification");

        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }
}