package uts.model;

import java.time.LocalDate;

public abstract class Transaksi {
    private final String id;
    private final LocalDate tanggal;
    private final String keterangan;
    private final double jumlah;
    private final String kategori;

    public Transaksi(String id, LocalDate tanggal, String keterangan, double jumlah, String kategori) {
        this.id = id;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
        this.kategori = kategori;
    }

    public String getId() { return id; }
    public LocalDate getTanggal() { return tanggal; }
    public String getKeterangan() { return keterangan; }
    public double getJumlah() { return jumlah; }
    public String getKategori() { return kategori; }

    public abstract String getTipe();

    @Override
    public String toString() {
        // format: tipe;id;tanggal;keterangan;jumlah;kategori
        return String.format("%s;%s;%s;%s;%.2f;%s",
                getTipe(), id, tanggal.toString(), keterangan.replace(";", ","), jumlah, kategori.replace(";", ","));
    }
}
