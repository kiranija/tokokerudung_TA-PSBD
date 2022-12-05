package com.example.toko_kerudung.model;

public class Transaksi_krdg {
    private String id_transaksi;
    private int jumlah;
    private String tgl_trans;
    private String id_customer;
    private String id_kerudung;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getTgl_trans() {
        return tgl_trans;
    }

    public void setTgl_trans(String tgl_trans) {
        this.tgl_trans = tgl_trans;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_kerudung() {
        return id_kerudung;
    }

    public void setId_kerudung(String id_kerudung) {
        this.id_kerudung = id_kerudung;
    }
}

