package studio8.jadwalkuliah;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelMatkul;
import studio8.jadwalkuliah.model.ModelWaktu;

public class Alarm {
    Context mContext;
    dbHandler db;
    public Alarm(Context context) {
        this.mContext = context;
    }

    public void addAlarm(String id, String[] arrHari, Calendar mulai, Calendar selesai){
        int menitBefore = 1;
        long durasi = selesai.getTimeInMillis() - mulai.getTimeInMillis();

        Log.d("myTag", "mulai"+mulai.getTime().toString());
        Log.d("myTag", "selesai"+selesai.getTime().toString());
        Log.d("myTag", "mulai " + String.valueOf(mulai.getTimeInMillis()));
        Log.d("myTag","selesai " + String.valueOf(selesai.getTimeInMillis()));
        Log.d("myTag","durasi " + String.valueOf(durasi));
//        Log.d("myTag","day awal " +String.valueOf(mulai2.get(Calendar.DAY_OF_MONTH)));
//        Log.d("myTag","hour of day "+ String.valueOf(mulai2.get(Calendar.HOUR_OF_DAY)));
//        Log.d("myTag","minute" +String.valueOf(mulai2.get(Calendar.MINUTE)));
        for (int i = 0; i<arrHari.length; i++){
//            mulai.set(Calendar.DAY_OF_MONTH, kalAwal);
            if (arrHari[i].equals("minggu"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            else if (arrHari[i].equals("senin"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            else if (arrHari[i].equals("selasa"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            else if (arrHari[i].equals("rabu"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            else if (arrHari[i].equals("kamis"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            else if (arrHari[i].equals("jumat"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            else if (arrHari[i].equals("sabtu"))
                mulai.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            Log.d("myTag", "mulai "+String.valueOf(mulai.get(Calendar.DAY_OF_WEEK)));
//            Log.d("myTag", "getInstance "+String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));

            if (mulai.get(Calendar.DAY_OF_WEEK) < (Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
//                Log.d("myTag ", "if true" );
                mulai.add(Calendar.DAY_OF_MONTH, 7);
                mulai.add(Calendar.MINUTE, menitBefore*-1);
                setAlarm(mulai, id+String.valueOf(i), id, durasi);
                mulai.add(Calendar.MINUTE, menitBefore);
                mulai.add(Calendar.DAY_OF_MONTH, -7);
            }

            else if (mulai.before(Calendar.getInstance())){
//                Log.d("myTag ", "else if true" );
                mulai.add(Calendar.DAY_OF_MONTH, 7);
                mulai.add(Calendar.MINUTE, menitBefore*-1);
                setAlarm(mulai, id+String.valueOf(i), id, durasi);
                mulai.add(Calendar.MINUTE, menitBefore);
                mulai.add(Calendar.DAY_OF_MONTH, -7);
            } else {
                mulai.add(Calendar.MINUTE, menitBefore*-1);
                setAlarm(mulai, id + String.valueOf(i), id, durasi);
                mulai.add(Calendar.MINUTE, menitBefore);
            }
//            Log.d("myTag","mulai " +String.valueOf(mulai.getTime()));
//            Log.d("myTag","selesai" +String.valueOf(mulai.getTime()));
//            Log.d("myTag","minute" +String.valueOf(mulai.get(Calendar.MINUTE)));

        }
    }

    private Intent setIntent(String idData, String idAlarm, long durasi){
        db = new dbHandler(mContext);
        String idMatkul = null, ruang = null, mulai = null, selesai = null, matkul = null, kelas = null, dosen = null;
        List<ModelWaktu> contacts = db.ReadWaktu();
        for (ModelWaktu cn : contacts) {
            if (cn.getId().equals(idData)) {
                idMatkul = (cn.getId_matkul());
                ruang = cn.getRuang();
                mulai = cn.getMulai();
                selesai = cn.getSelesai();
            }
        }
        List<ModelMatkul> contacts2 = db.ReadMatkul();
        for (ModelMatkul cn : contacts2) {
            if (cn.getId().equals(idMatkul)) {
                matkul = (cn.getMatkul());
                kelas = (cn.getKelas());
                dosen = (cn.getDosen());
            }
        }
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("id", idAlarm);
        intent.putExtra("matkul", matkul);
        intent.putExtra("kelas", kelas);
        intent.putExtra("dosen", dosen);
        intent.putExtra("ruang", ruang);
        intent.putExtra("mulai", mulai);
        intent.putExtra("selesai", selesai);
        intent.putExtra("durasi", durasi);
        return intent;
    }

    private void setAlarm(Calendar calSet, String idAlarm, String idData, long durasi) {

//        Log.d("myTag","day "+ String.valueOf(calSet.get(Calendar.DAY_OF_MONTH)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext,Integer.valueOf(idAlarm), setIntent(idData, idAlarm, durasi), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000 , pendingIntent);

    }

    public void sortDeleteAlarm(String id, String hari){
        String[] arrHari = hari.split(",");
        for (int i = 0; i<arrHari.length; i++){
            deleteAlarm(id+String.valueOf(i));
        }
    }

    private void deleteAlarm(String id){
        Intent intent;
        intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                Integer.valueOf(id), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
