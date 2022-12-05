package com.example.toko_kerudung.model;

public class Kerudung {
    private String id_kerudung;
    private String nama_kerudung;
    private String ukuran;
    private String harga;

    public String getId_kerudung() {
        return id_kerudung;
    }

    public void setId_kerudung(String id_kerudung) {
        this.id_kerudung = id_kerudung;
    }

    public String getNama_kerudung() {
        return nama_kerudung;
    }

    public void setNama_kerudung(String nama_kerudung) {
        this.nama_kerudung = nama_kerudung;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
