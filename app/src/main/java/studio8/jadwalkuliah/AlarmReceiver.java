package studio8.jadwalkuliah;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer player;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // TODO Auto-generated method stub
        final Long durasi = intent.getLongExtra("durasi", 0);

        Toast.makeText(context, "Alarm aktif!", Toast.LENGTH_LONG).show();
        Log.d("mytag", "alarmreceiver aktif");
        notifikasi(context, intent, "Sesaat Lagi..", R.layout.notif_layout_sesaatlagi);
        // membuat komponen notifikasi
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // createNotification(SmsMessage.createFromPdu((byte[])smsExtra[0]), context);
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
                nMgr.cancel(Integer.parseInt(intent.getStringExtra("id")));
                notifikasi(context, intent, "Sekarang", R.layout.notif_layout_sekarang);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // createNotification(SmsMessage.createFromPdu((byte[])smsExtra[0]), context);
                        String ns = Context.NOTIFICATION_SERVICE;
                        NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
                        nMgr.cancel(Integer.parseInt(intent.getStringExtra("id")));
                        Log.d("myTag", "durasi " + intent.getStringExtra("id") + " " + durasi);
//                        Log.d("myTag", "selesai " + intent.getStringExtra("mulai"));
//                        Log.d("myTag", "selesai " + intent.getStringExtra("selesai"));
                        Log.d("myTag", "close " + Calendar.getInstance().getTime());
                    }
                }, durasi);
            }
        }, 1*60*1000);

    }

    void notifikasi(final Context context, final Intent intent, String kapan, int layout){
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews contentView = new RemoteViews(context.getPackageName(), layout);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.tv_matkul, intent.getStringExtra("matkul"));
        contentView.setTextViewText(R.id.tv_mulai, intent.getStringExtra("mulai"));
        contentView.setTextViewText(R.id.tv_selesai, intent.getStringExtra("selesai"));
        contentView.setTextViewText(R.id.tv_ruang, intent.getStringExtra("ruang"));
        contentView.setTextViewText(R.id.tv_kelas, intent.getStringExtra("kelas"));
        contentView.setTextViewText(R.id.tv_kapan, kapan);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification;
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setOngoing(true)
//                .setCustomContentView(R.layout.notif_layout_sesaatlagi)
                .setContentIntent(pendingIntent)
//                .setContentTitle(intent.getStringExtra("matkul"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                .setContentText(intent.getStringExtra("jam"))
                .setContent(contentView)
                .build();

//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(intent.getStringExtra("id")), notification);
    }
}
