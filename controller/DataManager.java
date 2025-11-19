package uts.controller;

import uts.model.Transaksi;
import uts.model.Pemasukan;
import uts.model.Pengeluaran;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.Comparator;
import java.util.List; 
import java.util.Objects;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

// Import untuk PDF (memerlukan OpenPDF library)
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Phrase;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

// Apache POI imports for Excel export (memerlukan Apache POI library)
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataManager {
    private final List<Transaksi> transaksiList = new ArrayList<>(); 

    public DataManager() {
        // Konstruktor kosong
    }

    // --- 1. Metode CRUD Dasar ---

    public List<Transaksi> getTransaksiList() {
        return transaksiList.stream()
                .sorted(Comparator.comparing(Transaksi::getTanggal).thenComparing(Transaksi::getId))
                .collect(Collectors.toList());
    }

    public Transaksi createTransaksi(String tipe, LocalDate tanggal, String keterangan, double jumlah, String kategori) {
        String id = generateNextIdForDate(tanggal);
        Transaksi t = tipe.equalsIgnoreCase("Pemasukan")
                ? new Pemasukan(id, tanggal, keterangan, jumlah, kategori)
                : new Pengeluaran(id, tanggal, keterangan, jumlah, kategori);
        transaksiList.add(t);
        return t;
    }

    public void updateTransaksi(String oldId, LocalDate tanggal, String keterangan, double jumlah, String kategori) {
        for (int i = 0; i < transaksiList.size(); i++) {
            Transaksi t = transaksiList.get(i);
            if (t.getId().equals(oldId)) {
                Transaksi updated = t.getTipe().equalsIgnoreCase("Pemasukan")
                        ? new Pemasukan(oldId, tanggal, keterangan, jumlah, kategori)
                        : new Pengeluaran(oldId, tanggal, keterangan, jumlah, kategori);
                transaksiList.set(i, updated);
                return;
            }
        }
    }

    public void deleteTransaksi(String id) {
        transaksiList.removeIf(t -> t.getId().equals(id));
    }

    private String generateNextIdForDate(LocalDate tanggal) {
        String datePart = tanggal.toString().replaceAll("-", ""); 
        List<Integer> numbers = transaksiList.stream()
                .map(Transaksi::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.endsWith("-" + datePart))
                .map(id -> {
                    try {
                        String prefix = id.split("-")[0];
                        return Integer.parseInt(prefix);
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .sorted()
                .collect(Collectors.toList());

        int next = 1;
        if (!numbers.isEmpty()) {
            next = numbers.get(numbers.size() - 1) + 1;
        }
        return String.format("%03d-%s", next, datePart);
    }

    // --- 2. Import Method (FIXED & FINAL) ---

    public boolean importFromTxt(File source) {
        transaksiList.clear(); 
        int successCount = 0;
        int lineCount = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String line;
            
            // Mencari Header 'Tipe;...' secara eksplisit untuk melewati baris kosong/BOM di awal
            boolean headerFound = false;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String trimmedLine = line.trim();
                if (trimmedLine.toUpperCase().startsWith("TIPE;")) {
                    headerFound = true;
                    break;
                }
            }
            
            if (!headerFound) {
                System.err.println("Import Gagal: Header 'Tipe;ID;Tanggal;Keterangan;Jumlah;Kategori' tidak ditemukan. Pastikan file tidak kosong dan header ada di baris pertama.");
                transaksiList.clear(); 
                return false;
            }
            
            // Memproses data setelah header ditemukan
            while ((line = br.readLine()) != null) {
                lineCount++;
                String trimmedLine = line.trim();
                
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue; 
                }
                
                String[] parts = trimmedLine.split(";"); 
                
                if (parts.length < 6) {
                    System.err.println("Skip baris " + lineCount + ": Kolom kurang dari 6. Baris: " + trimmedLine);
                    continue; 
                }
                
                try {
                    String tipe = parts[0].trim();
                    String id = parts[1].trim();
                    
                    if (id.isEmpty() || id.equalsIgnoreCase("NULL")) {
                         id = generateNextIdForDate(LocalDate.parse(parts[2].trim()));
                    }
                    
                    LocalDate tanggal = LocalDate.parse(parts[2].trim());
                    String keterangan = parts[3].trim(); 
                    
                    // Mengganti koma dengan titik (desimal format Indonesia ke Java) sebelum parsing
                    double jumlah = Double.parseDouble(parts[4].trim().replace(",", ".")); 
                    
                    String kategori = parts[5].trim(); 
                    
                    Transaksi t = tipe.equalsIgnoreCase("Pemasukan")
                            ? new Pemasukan(id, tanggal, keterangan, jumlah, kategori)
                            : new Pengeluaran(id, tanggal, keterangan, jumlah, kategori);
                    transaksiList.add(t);
                    successCount++;
                    
                } catch (Exception parseEx) {
                    System.err.println("Gagal parse baris " + lineCount + " (" + trimmedLine + "). Error: " + parseEx.getMessage());
                }
            }
            
            if (successCount == 0) {
                 System.err.println("Import Gagal: Tidak ada data transaksi yang berhasil di-parse dari file.");
                 transaksiList.clear(); 
                 return false;
            }
            System.out.println("Import Berhasil: " + successCount + " transaksi dimuat.");
            return true;
        } catch (Exception ex) {
            System.err.println("Import Gagal Total. Error: " + ex.getMessage());
            ex.printStackTrace();
            transaksiList.clear(); 
            return false;
        }
    }

    // --- 3. Export Methods (FINAL & Header Included) ---

    public boolean exportToTxt(File target) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(target))) {
            // Header
            bw.write("Tipe;ID;Tanggal;Keterangan;Jumlah;Kategori");
            bw.newLine();

            for (Transaksi t : getTransaksiList()) {
                // Format data: Tipe;ID;Tanggal;Keterangan;Jumlah;Kategori
                bw.write(t.getTipe() + ";" + t.getId() + ";" + t.getTanggal().toString() + ";" + 
                        t.getKeterangan() + ";" + t.getJumlah() + ";" + t.getKategori());
                bw.newLine();
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean exportToCsv(File target) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(target))) {
            bw.write("Tipe,ID,Tanggal,Keterangan,Jumlah,Kategori");
            bw.newLine();
            for (Transaksi t : getTransaksiList()) {
                String csv = String.format("%s,%s,%s,\"%s\",%.2f,\"%s\"",
                        t.getTipe(), t.getId(), t.getTanggal().toString(),
                        t.getKeterangan().replace("\"", "\"\""), t.getJumlah(), t.getKategori().replace("\"", "\"\"")); 
                bw.write(csv);
                bw.newLine();
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean exportToExcel(File target) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Transaksi");
            
            CellStyle hstyle = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font hf = wb.createFont(); 
            hf.setBold(true);
            hstyle.setFont(hf);
            
            CellStyle currencyStyle = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            currencyStyle.setDataFormat(format.getFormat("Rp #,##0.00"));

            String[] header = {"Tipe", "ID", "Tanggal", "Keterangan", "Kategori", "Jumlah"};
            Row hr = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell c = hr.createCell(i);
                c.setCellValue(header[i]);
                c.setCellStyle(hstyle);
            }

            int r = 1;
            for (Transaksi t : getTransaksiList()) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(t.getTipe());
                row.createCell(1).setCellValue(t.getId());
                row.createCell(2).setCellValue(t.getTanggal().toString());
                row.createCell(3).setCellValue(t.getKeterangan());
                row.createCell(4).setCellValue(t.getKategori());
                
                Cell amountCell = row.createCell(5);
                amountCell.setCellValue(t.getJumlah());
                amountCell.setCellStyle(currencyStyle);
            }

            for (int i = 0; i < header.length; i++) sheet.autoSizeColumn(i);

            try (FileOutputStream fos = new FileOutputStream(target)) {
                wb.write(fos);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean exportToPdf(File target) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(target));
            document.open();
            
            document.add(new Paragraph("Laporan Keuangan Pribadi", FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD, 14, com.lowagie.text.Font.NORMAL, new java.awt.Color(0, 0, 0))));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6); 
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            
            String[] header = {"ID", "Tanggal", "Keterangan", "Kategori", "Tipe", "Jumlah"};
            for (String h : header) {
                table.addCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
            }
            
            DecimalFormat df = new DecimalFormat("#,##0.00");

            for (Transaksi t : getTransaksiList()) {
                table.addCell(t.getId());
                table.addCell(t.getTanggal().toString());
                table.addCell(t.getKeterangan());
                table.addCell(t.getKategori());
                table.addCell(t.getTipe());
                table.addCell(df.format(t.getJumlah()));
            }

            document.add(table);
            document.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}