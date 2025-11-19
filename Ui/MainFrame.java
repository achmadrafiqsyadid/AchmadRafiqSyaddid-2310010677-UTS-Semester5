package uts.ui;

import uts.controller.DataManager;
import uts.model.Transaksi;
import javax.swing.filechooser.FileNameExtensionFilter; 
import java.io.File; 
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.text.DecimalFormat; 
import java.util.Optional;
import java.util.stream.Collectors; 
import javax.swing.table.DefaultTableCellRenderer;
/**Perubahan desain*/
public class MainFrame extends javax.swing.JFrame {

    private DataManager dm;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00"); 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImpor;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.ButtonGroup buttonGroupJenis;
    private javax.swing.JComboBox<String> cbbKategori;
    public com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JPanel jPanelFooter;
    private javax.swing.JPanel jPanelInput;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblKategoriTitle;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JLabel lblSaldoAkhir;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblTotalKeluar;
    private javax.swing.JLabel lblTotalMasuk;
    private javax.swing.JRadioButton rbPemasukan;
    private javax.swing.JRadioButton rbPengeluaran;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKeterangan;
    // End of variables declaration//GEN-END:variables

    private DefaultTableModel tableModel;
    private String selectedId = null; 

    public MainFrame() {
        dm = new DataManager();
        initComponents();
        postInit();
        bindDataToTable();
        calculateTotals();
    }
    /**
         * Metode inisialisasi lanjutan yang dipanggil setelah initComponents().
         * Bertujuan untuk mengkonfigurasi model tabel, mengatur tipe data kolom,
         * dan mengatur kondisi awal komponen GUI.
     */
    private void postInit() {
    // 1. Definisikan Kolom Tabel, masukkan "ID Transaksi" di paling depan
    String[] columnNames = {"ID Transaksi", "Tanggal", "Keterangan", "Kategori", "Pemasukan", "Pengeluaran", "Saldo"};
    
    // 2. Buat model baru (Ini penting untuk menambah kolom ID dan mengatur tipe data)
    tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Pastikan semua sel tidak bisa diedit
        }
        // Atur tipe kolom 4, 5, 6 sebagai Double untuk sorting yang benar
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 4 || columnIndex == 5 || columnIndex == 6) {
                return Double.class;
            }
            return String.class;
        }
    };
    tblTransaksi.setModel(tableModel);

    rbPemasukan.setSelected(true);
    // Tambahkan ini agar tanggal default hari ini
    dateChooser.setDate(new Date()); 
    
    // Nonaktifkan tombol Ubah/Hapus saat form kosong
    btnUbah.setEnabled(false);
    btnHapus.setEnabled(false);
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupJenis = new javax.swing.ButtonGroup();
        jPanelInput = new javax.swing.JPanel();
        lblTanggal = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        lblKeterangan = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        lblJumlah = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        cbbKategori = new javax.swing.JComboBox<>();
        rbPemasukan = new javax.swing.JRadioButton();
        rbPengeluaran = new javax.swing.JRadioButton();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblKategoriTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jPanelFooter = new javax.swing.JPanel();
        lblTotalMasuk = new javax.swing.JLabel();
        lblTotalKeluar = new javax.swing.JLabel();
        lblSaldoAkhir = new javax.swing.JLabel();
        btnEkspor = new javax.swing.JButton();
        btnImpor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan Pribadi");

        jPanelInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblTanggal.setText("Tanggal");

        lblKeterangan.setText("Keterangan");

        lblJumlah.setText("Jumlah (Rp)");

        cbbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Makan & Minum", "Transportasi", "Gaji", "Belanja", "Lain-lain" }));

        buttonGroupJenis.add(rbPemasukan);
        rbPemasukan.setSelected(true);
        rbPemasukan.setText("Pemasukan");

        buttonGroupJenis.add(rbPengeluaran);
        rbPengeluaran.setText("Pengeluaran");

        btnTambah.setBackground(new java.awt.Color(0, 204, 51));
        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(255, 153, 0));
        btnUbah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 51, 51));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        lblKategoriTitle.setText("Kategori");

        javax.swing.GroupLayout jPanelInputLayout = new javax.swing.GroupLayout(jPanelInput);
        jPanelInput.setLayout(jPanelInputLayout);
        jPanelInputLayout.setHorizontalGroup(
            jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTanggal)
                    .addComponent(lblKeterangan)
                    .addComponent(lblJumlah)
                    .addComponent(lblKategoriTitle))
                .addGap(18, 18, 18)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addComponent(cbbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbPemasukan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbPengeluaran)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtKeterangan)
                    .addComponent(txtJumlah)
                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelInputLayout.setVerticalGroup(
            jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTanggal)
                            .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKeterangan)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblJumlah)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKategoriTitle)
                            .addComponent(rbPemasukan)
                            .addComponent(rbPengeluaran)))
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "Keterangan", "Kategori", "Pemasukan", "Pengeluaran", "Saldo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);
        if (tblTransaksi.getColumnModel().getColumnCount() > 0) {
            tblTransaksi.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblTransaksi.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblTransaksi.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblTransaksi.getColumnModel().getColumn(3).setPreferredWidth(90);
            tblTransaksi.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblTransaksi.getColumnModel().getColumn(5).setPreferredWidth(90);
        }

        lblTotalMasuk.setForeground(new java.awt.Color(0, 153, 51));
        lblTotalMasuk.setText("Total Pemasukan: Rp 0");

        lblTotalKeluar.setForeground(new java.awt.Color(204, 0, 0));
        lblTotalKeluar.setText("Total Pengeluaran: Rp 0");

        lblSaldoAkhir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSaldoAkhir.setText("Saldo: Rp 0");

        btnEkspor.setText("Ekspor");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        btnImpor.setText("Impor");
        btnImpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFooterLayout = new javax.swing.GroupLayout(jPanelFooter);
        jPanelFooter.setLayout(jPanelFooterLayout);
        jPanelFooterLayout.setHorizontalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalMasuk)
                    .addComponent(lblTotalKeluar))
                .addGap(57, 57, 57)
                .addComponent(lblSaldoAkhir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addComponent(btnEkspor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImpor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelFooterLayout.setVerticalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFooterLayout.createSequentialGroup()
                        .addGroup(jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalMasuk)
                            .addComponent(btnEkspor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImpor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalKeluar))
                    .addComponent(lblSaldoAkhir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporActionPerformed
        JFileChooser fc = new JFileChooser();
        // Hanya memperbolehkan file TXT atau CSV yang menggunakan semicolon (;)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text/CSV files (Semicolon separated)", "txt", "csv");
        fc.setFileFilter(filter);

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File source = fc.getSelectedFile();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Mengimpor data akan MENGHAPUS semua data yang ada saat ini. Lanjutkan?", 
                    "Konfirmasi Import", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) return;

            // Panggil method import (pastikan DataManager.java sudah diupdate)
            boolean ok = dm.importFromTxt(source); 

            if (ok) {
                bindDataToTable(); 
                calculateTotals(); 
                clearForm();       
                JOptionPane.showMessageDialog(this, "Import data dari " + source.getName() + " sukses.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Import gagal. Pastikan file dalam format TXT/CSV yang benar (dipisahkan semicolon ';') dan data valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    }//GEN-LAST:event_btnImporActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try {
            LocalDate tanggal = dateToLocalDate(dateChooser.getDate());
            String keterangan = txtKeterangan.getText().trim();
            
            // Kode yang Benar:
                if (txtJumlah.getText().trim().isEmpty() || keterangan.isEmpty() || tanggal == null || buttonGroupJenis.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Pastikan Tanggal, Keterangan, Jumlah, dan Tipe sudah diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Mengganti koma dengan titik untuk double parsing (locale-neutral)
            double jumlah = Double.parseDouble(txtJumlah.getText().trim().replace(",", ".")); 
            
            String kategori;
            if (cbbKategori.isEditable() && cbbKategori.getEditor() != null) {
                 kategori = cbbKategori.getEditor().getItem().toString().trim();
            } else {
                 kategori = (String) cbbKategori.getSelectedItem();
            }
            if (kategori == null) kategori = ""; 
            
            String tipe = rbPemasukan.isSelected() ? "Pemasukan" : "Pengeluaran";
            
            dm.createTransaksi(tipe, tanggal, keterangan, jumlah, kategori);
            bindDataToTable();
            clearForm();
            calculateTotals();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka yang valid (gunakan titik '.' sebagai desimal jika diperlukan).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menambah transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if (selectedId == null) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi untuk diubah.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            LocalDate tanggal = dateToLocalDate(dateChooser.getDate());
            String keterangan = txtKeterangan.getText().trim();
            
            if (txtJumlah.getText().trim().isEmpty() || keterangan.isEmpty() || tanggal == null) {
                 JOptionPane.showMessageDialog(this, "Pastikan Tanggal, Keterangan, dan Jumlah sudah diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double jumlah = Double.parseDouble(txtJumlah.getText().trim().replace(",", "."));
            
            String kategori;
            if (cbbKategori.isEditable() && cbbKategori.getEditor() != null) {
                 kategori = cbbKategori.getEditor().getItem().toString().trim();
            } else {
                 kategori = (String) cbbKategori.getSelectedItem();
            }
            if (kategori == null) kategori = ""; 

            dm.updateTransaksi(selectedId, tanggal, keterangan, jumlah, kategori);
            bindDataToTable();
            clearForm();
            calculateTotals();
            selectedId = null;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka yang valid (gunakan titik '.' sebagai desimal jika diperlukan).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (selectedId == null) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin dihapus.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus transaksi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dm.deleteTransaksi(selectedId);
            bindDataToTable();
            calculateTotals();
            clearForm();
            selectedId = null;
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
      clearForm();    
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        int row = tblTransaksi.getSelectedRow();
        if (row < 0) return;
        int modelRow = tblTransaksi.convertRowIndexToModel(row);

        // Ambil ID Transaksi langsung dari kolom pertama (Kolom 0)
        String idTransaksi = tableModel.getValueAt(modelRow, 0).toString();

        Optional<Transaksi> found = dm.getTransaksiList().stream()
                .filter(t -> t.getId().equals(idTransaksi))
                .findFirst();

        if (found.isPresent()) {
            Transaksi t = found.get();
            selectedId = t.getId();

            dateChooser.setDate(localDateToDate(t.getTanggal()));
            txtKeterangan.setText(t.getKeterangan());

            // Tampilkan jumlah mentah (String.valueOf) untuk input
            txtJumlah.setText(String.valueOf(t.getJumlah()));

            if (t.getTipe().equalsIgnoreCase("Pemasukan")) {
                rbPemasukan.setSelected(true);
            } else {
                rbPengeluaran.setSelected(true);
            }

            // Set Kategori
            cbbKategori.setSelectedItem(t.getKategori());
            if (cbbKategori.getSelectedItem() == null && cbbKategori.isEditable()) {
                 cbbKategori.getEditor().setItem(t.getKategori());
            }

            // Aktifkan tombol Ubah/Hapus dan nonaktifkan Tambah
            btnTambah.setEnabled(false); 
            btnUbah.setEnabled(true);
            btnHapus.setEnabled(true);
        }
    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
        String[] options = {"PDF", "XLSX", "TXT", "CSV", "Batal"};
        int choose = JOptionPane.showOptionDialog(this, "Pilih format export", "Export Data",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choose == 4 || choose == JOptionPane.CLOSED_OPTION) return;

        JFileChooser fc = new JFileChooser();
        String ext = options[choose].toLowerCase();
        
        fc.setSelectedFile(new File("Laporan_Keuangan." + ext));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File target = fc.getSelectedFile();
        
        // Menambahkan ekstensi jika user lupa mengetiknya
        String targetPath = target.getAbsolutePath();
        if (!targetPath.toLowerCase().endsWith("." + ext)) {
             target = new File(targetPath + "." + ext);
        }

        boolean ok = false;
        String formatName = options[choose];
        
        // Pastikan DataManager memiliki method export ini (seperti di jawaban sebelumnya)
        if (choose == 0) ok = dm.exportToPdf(target);
        else if (choose == 1) ok = dm.exportToExcel(target);
        else if (choose == 2) ok = dm.exportToTxt(target);
        else if (choose == 3) ok = dm.exportToCsv(target);

        if (ok) JOptionPane.showMessageDialog(this, "Export ke " + formatName + " sukses: " + target.getAbsolutePath(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, "Export ke " + formatName + " gagal. Pastikan semua library (OpenPDF, Apache POI) sudah terpasang.", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnEksporActionPerformed
    /**
     * Memuat (binding) data transaksi dari DataManager ke JTable.
     * Metode ini menghitung saldo berjalan dan menerapkan renderer mata uang.
     * ID Transaksi (Kolom 0) disembunyikan.
     */
    private void bindDataToTable() {
    tableModel.setRowCount(0);
    List<Transaksi> list = dm.getTransaksiList();
    double running = 0.0;

    for (Transaksi t : list) {
        double pemasukan = 0.0;
        double pengeluaran = 0.0;
        
        if ("Pemasukan".equalsIgnoreCase(t.getTipe())) {
            pemasukan = t.getJumlah();
            running += pemasukan;
        } else {
            pengeluaran = t.getJumlah();
            running -= pengeluaran;
        }
        
        // Kolom: ID Transaksi (0) | Tanggal (1) | Keterangan (2) | Kategori (3) | Pemasukan (4) | Pengeluaran (5) | Saldo Berjalan (6)
        tableModel.addRow(new Object[]{
                t.getId(), // ID Transaksi
                t.getTanggal().toString(),
                t.getKeterangan(),
                t.getKategori(),
                pemasukan, // Jumlah mentah (Double)
                pengeluaran, // Jumlah mentah (Double)
                running // Jumlah mentah (Double)
        });
    }
    
    // ** KONFIGURASI TABEL (Harus dilakukan setelah model diisi) **
    
    // 1. Sembunyikan kolom ID Transaksi (Kolom 0)
    if (tblTransaksi.getColumnModel().getColumnCount() > 0) {
        tblTransaksi.getColumnModel().getColumn(0).setMinWidth(0);
        tblTransaksi.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTransaksi.getColumnModel().getColumn(0).setWidth(0);
    }
    
    // 2. Terapkan CurrencyRenderer pada kolom Pemasukan (4), Pengeluaran (5), dan Saldo (6)
    tblTransaksi.getColumnModel().getColumn(4).setCellRenderer(new CurrencyRenderer(currencyFormat));
    tblTransaksi.getColumnModel().getColumn(5).setCellRenderer(new CurrencyRenderer(currencyFormat));
    tblTransaksi.getColumnModel().getColumn(6).setCellRenderer(new CurrencyRenderer(currencyFormat));

    calculateTotals();
}

    private void clearForm() {
        dateChooser.setDate(new Date()); // Set ke tanggal hari ini
        txtKeterangan.setText("");
        txtJumlah.setText("");

        // Clear ComboBox Kategori
        if (cbbKategori.isEditable() && cbbKategori.getEditor() != null) cbbKategori.getEditor().setItem("");
        else cbbKategori.setSelectedIndex(0);

        rbPemasukan.setSelected(true);
        selectedId = null;
        tblTransaksi.clearSelection();

        // Reset status tombol
        btnTambah.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    /**
     * Menghitung total pemasukan, total pengeluaran, dan saldo akhir
     * dari seluruh transaksi, lalu menampilkannya di footer.
     * Juga mengatur warna saldo (Merah jika minus, Biru jika positif).
     */
    private void calculateTotals() {
        double masuk = 0, keluar = 0;
        for (Transaksi t : dm.getTransaksiList()) {
            if ("Pemasukan".equalsIgnoreCase(t.getTipe())) masuk += t.getJumlah();
            else keluar += t.getJumlah();
        }
        
        double saldo = masuk - keluar;
        
        lblTotalMasuk.setText("Total Pemasukan: Rp " + currencyFormat.format(masuk));
        lblTotalKeluar.setText("Total Pengeluaran: Rp " + currencyFormat.format(keluar));
        lblSaldoAkhir.setText("Saldo: Rp " + currencyFormat.format(saldo));
        
        // Beri warna pada Saldo
        if (saldo < 0) lblSaldoAkhir.setForeground(Color.RED);
        else lblSaldoAkhir.setForeground(Color.BLUE); // Atau Color.BLACK/Green
    }
    
    /**
     * Mengubah objek java.util.Date menjadi java.time.LocalDate.
     * @param d Objek Date yang akan dikonversi.
     * @return Objek LocalDate, atau null jika input null.
     */
    private LocalDate dateToLocalDate(Date d) {
        if (d == null) return null; 
        return Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Mengubah objek java.time.LocalDate menjadi java.util.Date.
     * @param ld Objek LocalDate yang akan dikonversi.
     * @return Objek Date, atau null jika input null.
     */
    private Date localDateToDate(LocalDate ld) {
        if (ld == null) return null;
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    /**
 * Custom Cell Renderer untuk memformat nilai Double menjadi mata uang di JTable
 */
    private class CurrencyRenderer extends DefaultTableCellRenderer {
        private final DecimalFormat formatter;

        public CurrencyRenderer(DecimalFormat formatter) {
            this.formatter = formatter;
            setHorizontalAlignment(RIGHT); // Rata kanan
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Double) {
                value = formatter.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}