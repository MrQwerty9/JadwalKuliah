package studio8.jadwalkuliah.view;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import studio8.jadwalkuliah.Alarm;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.adapter.AdapterWaktu;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelWaktu;

public class UpdWaktuActivity extends AppCompatActivity {

    private dbHandler db;
    private Alarm alarm;
    private Button mUpdate;
    private EditText mRuang, mWaktuMulai, mWaktuSelesai;
    private TextView mTxMulai, mTxSelesai;
    private ToggleButton mMinggu, mSenin, mSelasa, mRabu, mKamis, mJumat, mSabtu;
    private String ruang, strHari, get_id, get_mulai, get_selesai, get_ruang, get_hari, get_id_matkul;
    private String[] arrHari, arr_getHari;
    private AdapterWaktu adapterWaktu;
    final boolean[] bHari = new boolean[7];
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Calendar calNow = Calendar.getInstance();
    Calendar mulai = (Calendar) calNow.clone();
    Calendar selesai = (Calendar) calNow.clone();
    List<String> listHari = new ArrayList<String>();
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_waktu);

        db = new dbHandler(this);
        alarm = new Alarm(this);

        mRuang = (EditText) findViewById(R.id.et_ruang);
        mUpdate = (Button) findViewById(R.id.btn_simpan);
        mWaktuMulai = (EditText) findViewById(R.id.et_waktu_mulai);
        mWaktuSelesai = (EditText) findViewById(R.id.et_waktu_selesai);
        mMinggu = (ToggleButton) findViewById(R.id.tb_minggu);
        mSenin = (ToggleButton) findViewById(R.id.tb_senin);
        mSelasa = (ToggleButton) findViewById(R.id.tb_selasa);
        mRabu = (ToggleButton) findViewById(R.id.tb_rabu);
        mKamis = (ToggleButton) findViewById(R.id.tb_kamis);
        mJumat = (ToggleButton) findViewById(R.id.tb_jumat);
        mSabtu = (ToggleButton) findViewById(R.id.tb_sabtu);
        mTxMulai = (TextView) findViewById(R.id.tv_mulai);
        mTxSelesai = (TextView) findViewById(R.id.tv_selesai);

        Intent getMainWaktu = this.getIntent();
        get_id = getMainWaktu.getStringExtra("id");
        get_id_matkul = getMainWaktu.getStringExtra("id_matkul");
        get_ruang = getMainWaktu.getStringExtra("ruang");
        get_hari = getMainWaktu.getStringExtra("hari");
        get_mulai = getMainWaktu.getStringExtra("mulai");
        get_selesai = getMainWaktu.getStringExtra("selesai");

        mRuang.setText(get_ruang);
        mWaktuMulai.setText(get_mulai);
        mWaktuSelesai.setText(get_selesai);
        arr_getHari = get_hari.split(",");
        for (int i = 0; i < arr_getHari.length; i++) {
            if (arr_getHari[i].equals("minggu"))
                mMinggu.setChecked(true);
            else if (arr_getHari[i].equals("senin"))
                mSenin.setChecked(true);
            else if (arr_getHari[i].equals("selasa"))
                mSelasa.setChecked(true);
            else if (arr_getHari[i].equals("rabu"))
                mRabu.setChecked(true);
            else if (arr_getHari[i].equals("kamis"))
                mKamis.setChecked(true);
            else if (arr_getHari[i].equals("jumat"))
                mJumat.setChecked(true);
            else if (arr_getHari[i].equals("sabtu"))
                mSabtu.setChecked(true);
        }
//        Log.d("myTag","gethari"+get_hari);
        mWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerDialog("mulai");
            }
        });
        mWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerDialog("selesai");
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMinggu.isChecked())
                    listHari.add("minggu");
                if (mSenin.isChecked())
                    listHari.add("senin");
                if (mSelasa.isChecked())
                    listHari.add("selasa");
                if (mRabu.isChecked())
                    listHari.add("rabu");
                if (mKamis.isChecked())
                    listHari.add("kamis");
                if (mJumat.isChecked())
                    listHari.add("jumat");
                if (mSabtu.isChecked())
                    listHari.add("sabtu");
                arrHari = listHari.toArray(new String[listHari.size()]);
                strHari = "";
                for (int i = 0; i < arrHari.length; i++) {
                    strHari = strHari + arrHari[i];
                    if (i < arrHari.length - 1) {
                        strHari = strHari + ",";
                    }
                }
                ruang = mRuang.getText().toString();
                if (ruang.equals("")) {
                    mRuang.requestFocus();
                    Toast.makeText(UpdWaktuActivity.this, "Silahkan isi ruang", Toast.LENGTH_SHORT).show();
                } else if (arrHari == null) {
                    Toast.makeText(UpdWaktuActivity.this, "Silahkan isi Hari", Toast.LENGTH_SHORT).show();
                } else {
                    db.UpdateWaktu(new ModelWaktu(get_id, get_id_matkul, ruang, dateFormat.format(mulai.getTime()), dateFormat.format(selesai.getTime()), strHari));
                    alarm.sortDeleteAlarm(get_id, strHari);
                    alarm.addAlarm(get_id, arrHari, mulai, selesai);
                    Toast.makeText(UpdWaktuActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                    Intent goMainWaktu = new Intent(getApplicationContext(), WaktuActivity.class);
                    goMainWaktu.putExtra("notifyChanged", true);
                    startActivity(goMainWaktu);
                }
            }
        });
    }

    private void openTimePickerDialog(String waktu) {
        Calendar calendar = Calendar.getInstance();

        if (waktu.equals("mulai"))
            timePickerDialog = new TimePickerDialog(UpdWaktuActivity.this, onTimeSetListener_mulai, calendar.get(calendar.HOUR_OF_DAY),
                    calendar.get(calendar.MINUTE), true);
        else
            timePickerDialog = new TimePickerDialog(UpdWaktuActivity.this, onTimeSetListener_selesai, calendar.get(calendar.HOUR_OF_DAY),
                    calendar.get(calendar.MINUTE), true);

        timePickerDialog.setTitle("Atur waktu");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener_mulai = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

            mulai.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mulai.set(Calendar.MINUTE, minute);
            mulai.set(Calendar.SECOND, 0);
            mulai.set(Calendar.MILLISECOND, 0);
            mWaktuMulai.setText(dateFormat.format(mulai.getTime()));
            mTxMulai.setVisibility(View.VISIBLE);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener_selesai = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            selesai.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selesai.set(Calendar.MINUTE, minute);
            selesai.set(Calendar.SECOND, 0);
            selesai.set(Calendar.MILLISECOND, 0);
            mWaktuSelesai.setText(dateFormat.format(selesai.getTime()));
            mTxSelesai.setVisibility(View.VISIBLE);
        }
    };
}
