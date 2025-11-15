package UTS.model;

import java.time.LocalDate;

// Harus 'extends Transaksi'
public class Pengeluaran extends Transaksi {

    // CONSTRUCTOR ini HARUS ADA.
    // Inilah yang tidak bisa ditemukan oleh Java
    public Pengeluaran(LocalDate tanggal, String keterangan, double jumlah) {
        // 'super' memanggil constructor induk (Transaksi)
        super(tanggal, keterangan, jumlah);
    }

    @Override
    public String getTipe() {
        return "Pengeluaran";
    }
}