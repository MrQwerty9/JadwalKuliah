package studio8.jadwalkuliah.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import studio8.jadwalkuliah.R;
import studio8.jadwalkuliah.data.dbHandler;
import studio8.jadwalkuliah.model.ModelMatkul;

public class CreateMatkulActivity extends AppCompatActivity {

    private dbHandler db;
    private EditText mMatkul, mKelas, mDosen;
    private Button mSimpan;
    private String matkul, kelas, dosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_matkul);

        db = new dbHandler(this);

        mMatkul = (EditText) findViewById(R.id.et_matkul);
        mKelas = (EditText) findViewById(R.id.et_kelas);
        mDosen = (EditText) findViewById(R.id.et_dosen);
        mSimpan = (Button) findViewById(R.id.btn_simpan);

        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matkul = mMatkul.getText().toString();
                kelas = mKelas.getText().toString();
                dosen = mDosen.getText().toString();

                if (matkul.equals("")){
                    mMatkul.requestFocus();
                    Toast.makeText(CreateMatkulActivity.this, "Silahkan isi mata kuliah", Toast.LENGTH_SHORT).show();
                }else if (dosen.equals("")){
                    mDosen.requestFocus();
                    Toast.makeText(CreateMatkulActivity.this, "Silahkan isi nama dosen", Toast.LENGTH_SHORT).show();
                }else if (kelas.equals("")){
                    mKelas.requestFocus();
                    Toast.makeText(CreateMatkulActivity.this, "Silahkan isi kelas", Toast.LENGTH_SHORT).show();
                }else{
                    mMatkul.setText("");
                    mKelas.setText("");
                    mDosen.setText("");

                    db.CreateMatkul(new ModelMatkul(null, matkul, kelas, dosen));
                    Toast.makeText(CreateMatkulActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
