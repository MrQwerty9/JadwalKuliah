package studio8.jadwalkuliah.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import studio8.jadwalkuliah.Alarm;
import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.adapter.AdapterFragment;
import studio8.jadwalkuliah.adapter.AdapterWaktu;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.fragment.HariiniFragment;
import studio8.jadwalkuliah.fragment.JadwalFragment;
import studio8.jadwalkuliah.model.ModelMatkul;
import studio8.jadwalkuliah.model.ModelWaktu;

public class MainActivity extends AppCompatActivity{

    private AdapterWaktu adapterWaktu;
    private dbHandler db;
    private Alarm alarm;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private List<ModelWaktu> modalListWaktu = new ArrayList<ModelWaktu>();
    private ViewPager mViewPager;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private int[] tabIcons = {
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//        }
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent goCreate = new Intent(getApplicationContext(), CreateMatkulActivity.class);
                startActivity(goCreate);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        AdapterFragment adapter = new AdapterFragment(getSupportFragmentManager());
        adapter.addFragment(new JadwalFragment(), "ONE");
        adapter.addFragment(new HariiniFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bulkDelete(String id_matkul){
        db = new dbHandler(this);
        alarm = new Alarm(this);
//
        adapterWaktu = new AdapterWaktu(this, modalListWaktu);

        List<ModelWaktu> contacts = db.ReadWaktu();
        for (ModelWaktu cn : contacts) {
            if (cn.getId_matkul().equals(id_matkul)) {
                db.DeleteWaktu(new ModelWaktu(cn.getId(), id_matkul, cn.getRuang(), cn.getMulai(), cn.getSelesai(), cn.getHari()));
                alarm.sortDeleteAlarm(cn.getId(), cn.getHari());
            }
//            Log.d("myTag", "get id matkul " + cn.getId_matkul());
//            Log.d("myTag", "id_matkul " + id_matkul);
        }
    }

    public void OnClick(int i, List<ModelMatkul> list){
        final String id = list.get(i).getId();
        final String matkul = list.get(i).getMatkul();
        final String dosen = list.get(i).getDosen();

        Intent goWaktu = new Intent(getApplicationContext(), WaktuActivity.class);
        goWaktu.putExtra("id", id);
        goWaktu.putExtra("matkul", matkul);
        goWaktu.putExtra("dosen", dosen);
        startActivity(goWaktu);
    }

    public void OnLongClick(int i, List<ModelMatkul> list){
        final CharSequence[] dialogItems = {"Edit Kelas", "Hapus Kelas"};

        final String id = list.get(i).getId();
        final String matkul = list.get(i).getMatkul();
        final String kelas = list.get(i).getKelas();
        final String dosen = list.get(i).getDosen();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(matkul);
        builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent goUpdel = new Intent(getApplicationContext(), UpdMatkulActivity.class);
                        goUpdel.putExtra("id", id);
                        goUpdel.putExtra("matkul", matkul);
                        goUpdel.putExtra("kelas", kelas);
                        goUpdel.putExtra("dosen", dosen);
                        startActivity(goUpdel);
                        break;
                    case 1:
                        bulkDelete(id);
                        db.DeleteMatkul(new ModelMatkul(id, matkul, kelas, dosen));
                        Toast.makeText(getApplicationContext(), "Data telah dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }
}
