package studio8.jadwalkuliah.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import studio8.jadwalkuliah.model.ModelMatkul;
import studio8.jadwalkuliah.model.ModelWaktu;

public class dbHandler extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "db_coba";
    private static final String DATABASE_NAME = "db_jadwalkuliah";
    private static final String tb_matkul = "tb_matkul";
    private static final String tb_matkul_id = "id_matkul";
    private static final String tb_matkul_matkul = "matkul";
    private static final String tb_matkul_kelas = "kelas";
    private static final String tb_matkul_dosen = "dosen";

    private static final String tb_waktu = "tb_waktu";
    private static final String tb_waktu_id = "id_waktu";
    private static final String tb_waktu_id_matkul = "waktu_id_matkul";
    private static final String tb_waktu_ruang = "ruang";
    private static final String tb_waktu_hari = "hari";
    private static final String tb_waktu_mulai = "waktu_mulai";
    private static final String tb_waktu_selesai = "waktu_selesai";

    private static final String CREATE_TABLE_MATKUL = "CREATE TABLE " + tb_matkul + "("
            + tb_matkul_id + " INTEGER PRIMARY KEY ,"
            + tb_matkul_matkul + " TEXT,"
            + tb_matkul_kelas + " TEXT,"
            + tb_matkul_dosen + " TEXT"
            + ")";

    private static final String CREATE_TABLE_WAKTU = "CREATE TABLE " + tb_waktu + "("
            + tb_waktu_id + " INTEGER PRIMARY KEY ,"
            + tb_waktu_id_matkul + " INTEGER,"
            + tb_waktu_ruang + " TEXT, "
            + tb_waktu_hari + " TEXT,"
            + tb_waktu_mulai + " TEXT, "
            + tb_waktu_selesai + " TEXT "
            + ")";

    public dbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MATKUL);
        sqLiteDatabase.execSQL(CREATE_TABLE_WAKTU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void CreateMatkul(ModelMatkul modelMatkul){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_matkul_id, modelMatkul.getId());
        values.put(tb_matkul_matkul, modelMatkul.getMatkul());
        values.put(tb_matkul_kelas, modelMatkul.getKelas());
        values.put(tb_matkul_dosen, modelMatkul.getDosen());
        sqLiteDatabase.insert(tb_matkul,null,values);
        sqLiteDatabase.close();
    }

    public void CreateWaktu(ModelWaktu modelWaktu){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_waktu_id, modelWaktu.getId());
        values.put(tb_waktu_id_matkul, modelWaktu.getId_matkul());
        values.put(tb_waktu_ruang, modelWaktu.getRuang());
        values.put(tb_waktu_hari, modelWaktu.getHari());
        values.put(tb_waktu_mulai, modelWaktu.getMulai());
        values.put(tb_waktu_selesai, modelWaktu.getSelesai());
        sqLiteDatabase.insert(tb_waktu,null,values);
        sqLiteDatabase.close();
    }

    public List<ModelMatkul> ReadMatkul(){
        List<ModelMatkul> modelList = new ArrayList<ModelMatkul>();
        String selectQuery = "SELECT * FROM " + tb_matkul;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                ModelMatkul md = new ModelMatkul();
                md.setId(cursor.getString(0));
                md.setMatkul(cursor.getString(1));
                md.setKelas(cursor.getString(2));
                md.setDosen(cursor.getString(3));
                modelList.add(md);
//                Log.d("myTag", "do while read");
            } while (cursor.moveToNext());
        }
//        Log.d("myTag", "fungsi MainRead");
        db.close();
        return modelList;
    }

    public List<ModelWaktu> ReadWaktu(){
        List<ModelWaktu> modelList = new ArrayList<ModelWaktu>();
        String selectQuery = "SELECT * FROM " + tb_waktu;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                ModelWaktu md = new ModelWaktu();
                md.setId(cursor.getString(0));
                md.setId_matkul(cursor.getString(1));
                md.setRuang(cursor.getString(2));
                md.setHari(cursor.getString(3));
                md.setMulai(cursor.getString(4));
                md.setSelesai(cursor.getString(5));
                modelList.add(md);
//                Log.d("myTag", "do while read");
            } while (cursor.moveToNext());
        }
//        Log.d("myTag", "fungsi MainRead");
        db.close();
        return modelList;
    }

    public int UpdateMatkul(ModelMatkul md){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_matkul_matkul, md.getMatkul());
        values.put(tb_matkul_kelas, md.getKelas());
        values.put(tb_matkul_dosen, md.getDosen());

//        Log.d("myTag", "update");

        return db.update(tb_matkul, values, tb_matkul_id + " = ?",
                new String[]{String.valueOf(md.getId())});
    }

    public int UpdateWaktu(ModelWaktu md){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_waktu_ruang, md.getRuang());
        values.put(tb_waktu_hari, md.getHari());
        values.put(tb_waktu_mulai, md.getMulai());
        values.put(tb_waktu_selesai, md.getSelesai());

//        Log.d("myTag", "getHariupdate"+md.getHari());

        return db.update(tb_waktu, values, tb_waktu_id + " = ?",
                new String[]{String.valueOf(md.getId())});
    }

    public void DeleteMatkul(ModelMatkul md){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_matkul, tb_matkul_id + " = ?",
                new String[]{String.valueOf(md.getId())});
        db.close();
    }

    public void DeleteWaktu(ModelWaktu md){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_waktu, tb_waktu_id + " = ?",
                new String[]{String.valueOf(md.getId())});
        db.close();
    }

    public String getLast(){
        String id="";
        String selectQuery = "SELECT * FROM " + tb_waktu  + " ORDER BY "+tb_waktu_id+" DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                id = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return id;
    }
}
