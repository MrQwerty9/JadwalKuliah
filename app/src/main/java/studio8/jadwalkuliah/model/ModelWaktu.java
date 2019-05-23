package studio8.jadwalkuliah.model;

public class ModelWaktu {
    private String id, id_matkul, ruang, mulai, selesai, hari;

    public ModelWaktu() {
    }

    public ModelWaktu(String id, String id_matkul, String ruang, String mulai, String selesai, String hari) {
        this.id = id;
        this.id_matkul = id_matkul;
        this.ruang = ruang;
        this.mulai = mulai;
        this.selesai = selesai;
        this.hari = hari;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_matkul() {
        return id_matkul;
    }

    public void setId_matkul(String id_matkul) {
        this.id_matkul = id_matkul;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
