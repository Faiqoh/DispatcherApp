package com.example.appdispatcher;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class FirebaseService extends FirebaseMessagingService {

    DataHelper dbHelper;

    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "Refreshed token: " + token);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        dbHelper = new DataHelper(this);

        Map<String, String> data = remoteMessage.getData();
        Log.i("cek", String.valueOf(data));
        String dataPayload = data.get("data");
        Log.i("datapayload", String.valueOf(dataPayload));
        /*
         * Cek jika notif berisi data payload
         * pengiriman data payload dapat dieksekusi secara background atau foreground
         */
        if (remoteMessage.getData().size() > 0) {
            Log.e("TAG", "Message data payload: " + remoteMessage.getData());
            JSONObject jsonParse = new JSONObject(remoteMessage.getData());
//                showNotif(jsonParse.getString("title"), jsonParse.getString("message"));
            Log.i("cek json", String.valueOf(jsonParse));
            /*try {
                SharedPreferences mSetting = this.getSharedPreferences("Setting", MODE_PRIVATE);
                if (jsonParse.getString("id_user").equals(mSetting.getString("ID", "missing"))){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into notif(title, message) values('" +
                            remoteMessage.getNotification().getTitle() + "','" +
                            remoteMessage.getNotification().getBody() + "')");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

        //insert to local db
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into notif(title, message, created_at) values('" +
                remoteMessage.getNotification().getTitle() + "','" +
                remoteMessage.getNotification().getBody() + "', '"+
                getDateTime() + "')");

        /*
         * Cek jika notif berisi data notification payload
         * hanya dieksekusi ketika aplikasi bejalan secara foreground
         * dan dapat push notif melalui UI Firebase console
         */
        if (remoteMessage.getNotification() != null) {
            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showNotif(String title, String message) {

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "NotifApps")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.freelance_logoaja) // icon
                .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
                .setLights(200, 200, 200) // light button
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
                .setOnlyAlertOnce(true) // set alert sound notif
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent); // action notif ketika di tap
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notifBuilder.build());
    }
}
