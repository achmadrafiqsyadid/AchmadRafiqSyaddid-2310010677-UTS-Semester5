package UTS.model;

import java.time.LocalDate;

// Ini harus 'abstract'
public abstract class Transaksi {

    // Atribut harus 'private' (Encapsulation)
    private LocalDate tanggal;
    private String keterangan;
    private double jumlah;

    // Ini adalah CONSTRUCTOR
    public Transaksi(LocalDate tanggal, String keterangan, double jumlah) {
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }

    // --- Getter dan Setter ---
    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }
    
    // Method abstract untuk Polymorphism
    public abstract String getTipe();
}