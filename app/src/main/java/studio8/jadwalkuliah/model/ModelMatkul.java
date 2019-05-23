package studio8.jadwalkuliah.model;

public class ModelMatkul {

    private String id, matkul, kelas, dosen;


    public ModelMatkul() {
    }

    public ModelMatkul(String id, String matkul, String kelas, String dosen) {
        this.id = id;
        this.matkul = matkul;
        this.kelas = kelas;
        this.dosen = dosen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }
}
