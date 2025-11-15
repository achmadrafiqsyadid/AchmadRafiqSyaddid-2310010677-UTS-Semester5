package UTS.controller;

// --- IMPORT MODEL ---
import UTS.model.Pemasukan;
import UTS.model.Pengeluaran;
import UTS.model.Transaksi;

// --- IMPORT JAVA UTILS ---
import java.time.LocalDate;
import java.util.ArrayList;

// --- IMPORT JAVA I/O (UNTUK TXT, XLS, PDF) ---
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// --- IMPORT JAVA TEXT (UNTUK FORMATTER) ---
import java.text.DecimalFormat; 

// --- IMPORT APACHE POI (EXCEL) ---
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // <-- INI ADALAH PERBAIKANNYA
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

// --- IMPORT OPENPDF / ITEXT 5 (PDF) ---
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class DataManager {
    
    private final ArrayList<Transaksi> listTransaksi;

    public DataManager() {
        listTransaksi = new ArrayList<>();
    }
    
    // ===========================================
    // === METODE DASAR (CRUD) ===
    // ===========================================
    
    public void tambahTransaksi(Transaksi t) {
        this.listTransaksi.add(t);
    }

    public void ubahTransaksi(int index, Transaksi t) {
        if (index >= 0 && index < listTransaksi.size()) {
            this.listTransaksi.set(index, t);
        }
    }

    public void hapusTransaksi(int index) {
        if (index >= 0 && index < listTransaksi.size()) {
            this.listTransaksi.remove(index);
        }
    }
    
    public ArrayList<Transaksi> getListTransaksi() {
        return this.listTransaksi;
    }
    
    // ===========================================
    // === METODE KALKULASI (SUMMARY) ===
    // ===========================================

    public double getTotalPemasukan() {
        double total = 0;
        for (Transaksi t : listTransaksi) {
            if (t instanceof Pemasukan) {
                total += t.getJumlah();
            }
        }
        return total;
    }

    public double getTotalPengeluaran() {
        double total = 0;
        for (Transaksi t : listTransaksi) {
            if (t instanceof Pengeluaran) {
                total += t.getJumlah();
            }
        }
        return total;
    }

    public double getSaldo() {
        return getTotalPemasukan() - getTotalPengeluaran();
    }
    /**
     * Ekspor ke .txt (Biarkan apa adanya, .txt harus berisi data mentah)
     * @param file
     * @throws java.io.IOException
     */
    public void exportData(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Transaksi t : listTransaksi) {
                String tipe = t.getTipe().toUpperCase();
                String tanggal = t.getTanggal().toString();
                String keterangan = t.getKeterangan();
                String jumlah = String.valueOf(t.getJumlah()); // Data mentah
                
                String line = tipe + "," + tanggal + "," + keterangan + "," + jumlah;
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    /**
     * (DIUBAH) Menyimpan data ke file Excel (.xlsx) modern
     * @param file File tujuan.
     * @param formatter
     * @throws java.io.IOException
     */
    public void exportDataToXLSX(File file, java.text.DecimalFormat formatter) throws IOException {
        
        try (Workbook workbook = new XSSFWorkbook() // <-- Menggunakan XSSF (modern)
) {
            Sheet sheet = workbook.createSheet("Data Transaksi");
            String[] headers = {"Tanggal", "Keterangan", "Tipe", "Jumlah"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (Transaksi t : listTransaksi) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(t.getTanggal().toString());
                row.createCell(1).setCellValue(t.getKeterangan());
                row.createCell(2).setCellValue(t.getTipe());
                row.createCell(3).setCellValue(formatter.format(t.getJumlah())); // Format angka
            }
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        }
    }

    /**
     * Ekspor ke .pdf, sekarang menerima Formatter
     * @param file
     * @param formatter
     * @throws java.lang.Exception
     */
    public void exportDataToPDF(File file, DecimalFormat formatter) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        
        document.open();
        document.add(new Paragraph("Laporan Data Transaksi Keuangan"));
        document.add(new Paragraph(" "));
        
        PdfPTable pdfTable = new PdfPTable(4);
        pdfTable.addCell("Tanggal");
        pdfTable.addCell("Keterangan");
        pdfTable.addCell("Tipe");
        pdfTable.addCell("Jumlah");
        
        for (Transaksi t : listTransaksi) {
            pdfTable.addCell(t.getTanggal().toString());
            pdfTable.addCell(t.getKeterangan());
            pdfTable.addCell(t.getTipe());
            pdfTable.addCell(formatter.format(t.getJumlah())); // Format angka
        }
        
        document.add(pdfTable);
        document.close();
    }
    public void importData(File file) throws IOException, Exception {
        ArrayList<Transaksi> importedList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;
                
                String tipe = parts[0];
                LocalDate tanggal = LocalDate.parse(parts[1]);
                String keterangan = parts[2];
                double jumlah = Double.parseDouble(parts[3]);
                
                Transaksi t;
                if (tipe.equals("PEMASUKAN")) {
                    t = new Pemasukan(tanggal, keterangan, jumlah);
                } else {
                    t = new Pengeluaran(tanggal, keterangan, jumlah);
                }
                importedList.add(t);
            }
        }
        
        listTransaksi.clear();
        listTransaksi.addAll(importedList);
    }
}