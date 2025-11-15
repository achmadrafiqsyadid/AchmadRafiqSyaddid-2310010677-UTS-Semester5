package UTS.model;

import java.time.LocalDate;

// Harus 'extends Transaksi'
public class Pemasukan extends Transaksi {

    // CONSTRUCTOR ini HARUS ADA.
    // Ini yang dicari oleh MainFrame Anda
    public Pemasukan(LocalDate tanggal, String keterangan, double jumlah) {
        // 'super' memanggil constructor induk (Transaksi)
        super(tanggal, keterangan, jumlah);
    }

    @Override
    public String getTipe() {
        return "Pemasukan";
    }
}