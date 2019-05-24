package studio8.jadwalkuliah.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import studio8.jadwalkuliah.Alarm;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.adapter.AdapterWaktu;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelWaktu;

public class WaktuActivity extends AppCompatActivity{

    private String id_matkul = "";
    private dbHandler db;
    private Alarm alarm;
    private RecyclerView recycleWaktu;
    private LinearLayoutManager linearLayoutManager;
    private AdapterWaktu adapterWaktu;
    private List<ModelWaktu> modalList = new ArrayList<ModelWaktu>();
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu);
        appContext = getApplicationContext();
        Intent get = this.getIntent();
        id_matkul = get.getStringExtra("id");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackb.setAction("Action", null).show();

                Intent goCreateWaktu = new Intent(getApplicationContext(), CreateWaktuActivity.class);
                goCreateWaktu.putExtra("id_matkul", id_matkul);
                startActivity(goCreateWaktu);
            }
        });

        db = new dbHandler(this);
        alarm = new Alarm(this);
//
        adapterWaktu = new AdapterWaktu(this, modalList);
        recycleWaktu= (RecyclerView) findViewById(R.id.rv_waktu);
        linearLayoutManager = new LinearLayoutManager(this);
        recycleWaktu.setLayoutManager(linearLayoutManager);
        recycleWaktu.setAdapter(adapterWaktu);

        List<ModelWaktu> contacts = db.ReadWaktu();
        for (ModelWaktu cn : contacts) {
            ModelWaktu modal = new ModelWaktu();
            if (cn.getId_matkul().equals(id_matkul)) {
                modal.setId(cn.getId());
                modal.setRuang(cn.getRuang());
                modal.setHari(cn.getHari());
                modal.setMulai(cn.getMulai());
                modal.setSelesai(cn.getSelesai());
                modalList.add(modal);
            }
            if (modalList.isEmpty())
                Toast.makeText(WaktuActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
        }
    }
    public void OnLongClick(int i, List<ModelWaktu> list){
        final CharSequence[] dialogItems = {"Edit", "Hapus"};
        final String id = list.get(i).getId();
        final String id_matkul = list.get(i).getId_matkul();
        final String ruang = list.get(i).getRuang();
        final String mulai = list.get(i).getMulai();
        final String selesai = list.get(i).getSelesai();
        final String hari = list.get(i).getHari();
        AlertDialog.Builder builder = new AlertDialog.Builder(WaktuActivity.this);
        builder.setTitle("");
        builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent goUpdel = new Intent(getApplicationContext(), UpdWaktuActivity.class);
                        goUpdel.putExtra("id", id);
                        goUpdel.putExtra("id_matkul", id_matkul);
                        goUpdel.putExtra("ruang", ruang);
                        goUpdel.putExtra("mulai", mulai);
                        goUpdel.putExtra("selesai", selesai);
                        goUpdel.putExtra("hari", hari);
                        startActivity(goUpdel);
                        break;
                    case 1:
                        db.DeleteWaktu(new ModelWaktu(id, id_matkul, ruang, mulai, selesai, hari));
                        Toast.makeText(getApplicationContext(), "Data telah dihapus", Toast.LENGTH_SHORT).show();
                        alarm.sortDeleteAlarm(id, hari);
                        break;
                }
            }
        });

    }
}
