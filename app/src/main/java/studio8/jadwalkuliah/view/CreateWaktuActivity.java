package studio8.jadwalkuliah.view;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import studio8.jadwalkuliah.Alarm;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.adapter.AdapterWaktu;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelWaktu;

public class CreateWaktuActivity extends AppCompatActivity {

    private dbHandler db;
    private Alarm alarm;
    private Button mSimpan;
    private EditText mRuang, mWaktuMulai, mWaktuSelesai;
    private TextView mTxMulai, mTxSelesai;
    private ToggleButton mMinggu, mSenin, mSelasa, mRabu, mKamis, mJumat, mSabtu;
    private int jam, menit, setHari, setJam, setMenit;
    private String id_matkul, ruang, strHari;
    private String[] arrHari;
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
        setContentView(R.layout.activity_create_waktu);

        Intent getMainWaktu = this.getIntent();
        id_matkul = getMainWaktu.getStringExtra("id_matkul");

        db = new dbHandler(this);
        alarm = new Alarm(this);

        mRuang = (EditText) findViewById(R.id.et_ruang);
        mSimpan = (Button) findViewById(R.id.btn_simpan);
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
//
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

        mMinggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[0] = true;
                else bHari[0] = false;
            }
        });
        mSenin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[1] = true;
                else bHari[1] = false;
            }
        });
        mSelasa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[2] = true;
                else bHari[2] = false;
            }
        });
        mRabu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[3] = true;
                else bHari[3] = false;
            }
        });
        mKamis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[4] = true;
                else bHari[4] = false;
            }
        });
        mJumat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[5] = true;
                else bHari[5] = false;
            }
        });
        mSabtu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bHari[6] = true;
                else bHari[6] = false;
            }
        });

        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bHari[0])
                    listHari.add("minggu");
                if (bHari[1])
                    listHari.add("senin");
                if (bHari[2])
                    listHari.add("selasa");
                if (bHari[3])
                    listHari.add("rabu");
                if (bHari[4])
                    listHari.add("kamis");
                if (bHari[5])
                    listHari.add("jumat");
                if (bHari[6])
                    listHari.add("sabtu");

                arrHari = listHari.toArray(new String[listHari.size()]);
                strHari="";
                for (int i = 0; i< arrHari.length; i++) {
                    strHari = strHari+ arrHari[i];
                    if(i< arrHari.length-1){
                        strHari = strHari+",";
                    }
                }
                ruang = mRuang.getText().toString();
                if (ruang.equals("")){
                    mRuang.requestFocus();
                    Toast.makeText(CreateWaktuActivity.this, "Silahkan isi ruang", Toast.LENGTH_SHORT).show();
                }else if (arrHari == null){
                    Toast.makeText(CreateWaktuActivity.this, "Silahkan isi arrHari", Toast.LENGTH_SHORT).show();
                }else{
                    db.CreateWaktu(new ModelWaktu(null, id_matkul, ruang, dateFormat.format(mulai.getTime()), dateFormat.format(selesai.getTime()), strHari));
                    Toast.makeText(CreateWaktuActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();

                    Intent goMainWaktu = new Intent(getApplicationContext(), WaktuActivity.class);
                    goMainWaktu.putExtra("notifyChanged", true);
                    startActivity(goMainWaktu);
                    alarm.addAlarm(db.getLast(),arrHari, mulai, selesai);
                }
            }
        });
    }

    private void openTimePickerDialog(String waktu) {
        Calendar calendar = Calendar.getInstance();

        if (waktu.equals("mulai"))
            timePickerDialog = new TimePickerDialog(CreateWaktuActivity.this, onTimeSetListener_mulai, calendar.get(calendar.HOUR_OF_DAY),
                    calendar.get(calendar.MINUTE),true);
        else
            timePickerDialog = new TimePickerDialog(CreateWaktuActivity.this, onTimeSetListener_selesai, calendar.get(calendar.HOUR_OF_DAY),
                    calendar.get(calendar.MINUTE),true);

        timePickerDialog.setTitle("Atur waktu");
        timePickerDialog.show();
    }

    OnTimeSetListener onTimeSetListener_mulai = new OnTimeSetListener() {
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

    OnTimeSetListener onTimeSetListener_selesai = new OnTimeSetListener() {
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
