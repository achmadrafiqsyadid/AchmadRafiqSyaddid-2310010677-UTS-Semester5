package uts.model;

import java.time.LocalDate;

public class Pemasukan extends Transaksi {
    public Pemasukan(String id, LocalDate tanggal, String keterangan, double jumlah, String kategori) {
        super(id, tanggal, keterangan, jumlah, kategori);
    }

    @Override
    public String getTipe() {
        return "Pemasukan";
    }
}
