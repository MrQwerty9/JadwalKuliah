package studio8.jadwalkuliah.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelMatkul;

public class UpdMatkulActivity extends AppCompatActivity {

    private dbHandler db;
    private String get_id, get_matkul, get_kelas, get_dosen, matkul, kelas, dosen;
    private EditText mMatkul, mKelas, mDosen;
    private Button mSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_matkul);

        db = new dbHandler(this);

        Intent i = this.getIntent();
        get_id = i.getStringExtra("id");
        get_matkul = i.getStringExtra("matkul");
        get_kelas = i.getStringExtra("kelas");
        get_dosen= i.getStringExtra("dosen");

        mMatkul = (EditText) findViewById(R.id.et_matkul);
        mKelas = (EditText) findViewById(R.id.et_kelas);
        mDosen = (EditText) findViewById(R.id.et_dosen);
        mSimpan = (Button) findViewById(R.id.btn_simpan);

        mMatkul.setText(get_matkul);
        mKelas.setText(get_kelas);
        mDosen.setText(get_dosen);

        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matkul = mMatkul.getText().toString();
                kelas = mKelas.getText().toString();
                dosen = mDosen.getText().toString();

                if (matkul.equals("")){
                    mMatkul.requestFocus();
                    Toast.makeText(UpdMatkulActivity.this, "Silahkan isi mata kuliah", Toast.LENGTH_SHORT).show();
                }else if (dosen.equals("")){
                    mDosen.requestFocus();
                    Toast.makeText(UpdMatkulActivity.this, "Silahkan isi nama dosen", Toast.LENGTH_SHORT).show();
                }else if (kelas.equals("")){
                    mKelas.requestFocus();
                    Toast.makeText(UpdMatkulActivity.this, "Silahkan isi get_kelas", Toast.LENGTH_SHORT).show();
                }else{
                    db.UpdateMatkul(new ModelMatkul(get_id, matkul, kelas, dosen));
                    Toast.makeText(UpdMatkulActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
