package UTS.Ui;

import UTS.controller.DataManager;
import UTS.model.Pemasukan;
import UTS.model.Pengeluaran;
import UTS.model.Transaksi;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFileChooser; // Pastikan import ini ada
import javax.swing.filechooser.FileNameExtensionFilter; // Pastikan import ini ada

/**
 *
 * @author Achmad Rafiq Syaddid
 */
public class MainFrame extends javax.swing.JFrame {

    // ===========================================
    // === DEKLARASI VARIABEL CLASS ===
    // ===========================================
    
    // Variabel ini di-final karena diisi di constructor dan tidak pernah diubah
    private final DataManager dataManager;
    private final java.text.DecimalFormat formatter;
    
    // Variabel ini TIDAK final, karena diisi di constructor
    private javax.swing.JFileChooser fileChooser; // <-- DEKLARASIKAN DI SINI
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    
    // ===========================================
    // === CONSTRUCTOR (SUDAH DIPERBAIKI) ===
    // ===========================================
    
    // GANTI SELURUH CONSTRUCTOR LAMA ANDA DENGAN INI:

public MainFrame() {
    initComponents(); // Baris ini harus pertama

    // 1. Inisialisasi DataManager
    dataManager = new DataManager();
    
    // 2. Inisialisasi Formatter Angka
    java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols(new java.util.Locale("id", "ID"));
    symbols.setGroupingSeparator('.');
    formatter = new java.text.DecimalFormat("#,###", symbols);

    // 3. Inisialisasi File Chooser (INI PERBAIKANNYA)
    fileChooser = new javax.swing.JFileChooser(); // <-- HAPUS "JFileChooser" DI DEPAN
    try {
        int iconWidth = 32;  
        int iconHeight = 24; 
   
        btnTambah.setIcon(getScaledIcon("/UTS/Images/ButtonTambah.jpeg", iconWidth, iconHeight));
        btnUbah.setIcon(getScaledIcon("/UTS/Images/ButtonUbah.png", iconWidth, iconHeight));
        btnHapus.setIcon(getScaledIcon("/UTS/Images/ButtonHapus.png", iconWidth, iconHeight));
        btnBersih.setIcon(getScaledIcon("/UTS/Images/ButtonBersih.jpeg", iconWidth, iconHeight));
        btnExport.setIcon(getScaledIcon("/UTS/Images/ButtonExport.png", iconWidth, iconHeight));
        btnImport.setIcon(getScaledIcon("/UTS/Images/ButtonImport.jpeg", iconWidth, iconHeight));

        // Hapus teks tombol
        btnTambah.setText("");
        btnUbah.setText("");
        btnHapus.setText("");
        btnBersih.setText("");
        btnExport.setText("");
        btnImport.setText(""); 

        // Tambahkan Tooltip
        btnTambah.setToolTipText("Tambah data baru");
        btnUbah.setToolTipText("Ubah data terpilih");
        btnHapus.setToolTipText("Hapus data terpilih");
        btnBersih.setToolTipText("Bersihkan form");
        btnExport.setToolTipText("Ekspor data (PDF, XLSX, TXT)"); 
        btnImport.setToolTipText("Impor data (.txt)");
        
    } catch (Exception e) {
        System.err.println("Error memuat gambar icon: " + e.getMessage());
        e.printStackTrace(); // Tampilkan error di console
    }
    // --- BATAS KODE ICON ---

    // 5. Panggil refreshTabel() (Hanya SATU KALI di akhir)
    refreshTabel();
    
} // <-- Penutup Constructor

    
    // ===========================================
    // === METHOD HELPER (SUDAH BENAR) ===
    // ===========================================
    
    /**
     * Update label Pemasukan, Pengeluaran, dan Saldo.
     */
    private void updateSummary() {
        double totalPemasukan = dataManager.getTotalPemasukan();
        double totalPengeluaran = dataManager.getTotalPengeluaran();
        double saldo = dataManager.getSaldo();
        
        lblTotalPemasukan.setText("Pemasukan: Rp " + formatter.format(totalPemasukan));
        lblTotalPengeluaran.setText("Pengeluaran: Rp " + formatter.format(totalPengeluaran));
        lblSaldo.setText("Saldo Akhir: Rp " + formatter.format(saldo));
    }

    /**
     * Membersihkan semua input di form.
     */
    private void bersihForm() {
        txtKeterangan.setText("");
        txtJumlah.setText("");
        cmbTipe.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        tblTransaksi.clearSelection();
    }

//untuk method getScaledIcon
private javax.swing.ImageIcon getScaledIcon(String path, int width, int height) {
    try {
        java.net.URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            System.err.println("Gagal menemukan gambar: " + path);
            return null; 
        }
        javax.swing.ImageIcon originalIcon = new javax.swing.ImageIcon(imgUrl);
        java.awt.Image originalImage = originalIcon.getImage();
        java.awt.Image scaledImage = originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new javax.swing.ImageIcon(scaledImage);
    } catch (Exception e) {
        System.err.println("Error saat scaling gambar: " + path);
        e.printStackTrace(); // Ini akan menunjukkan error jika gambar tidak ditemukan
        return null;
    }
}

    /**
     * Mengambil data dari DataManager dan menampilkannya di JTable.
     */
    private void refreshTabel() {
        ArrayList<Transaksi> listData = dataManager.getListTransaksi();
        javax.swing.table.DefaultTableModel model = 
                (javax.swing.table.DefaultTableModel) tblTransaksi.getModel();
        
        model.setRowCount(0); // Bersihkan tabel

        double saldoBerjalan = 0;

        for (Transaksi t : listData) {
            Object pemasukan = ""; 
            Object pengeluaran = "";

            if (t.getTipe().equals("Pemasukan")) {
                pemasukan = formatter.format(t.getJumlah());
                saldoBerjalan += t.getJumlah();
            } else {
                pengeluaran = formatter.format(t.getJumlah());
                saldoBerjalan -= t.getJumlah();
            }

            Object[] row = new Object[]{
                t.getTanggal().toString(),
                t.getKeterangan(),
                pemasukan,
                pengeluaran,
                formatter.format(saldoBerjalan) // Kolom Saldo Berjalan
            };
            model.addRow(row);
        }
        
        updateSummary(); // Panggil update summary di akhir
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        txtKeterangan = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        cmbTipe = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblTotalPemasukan = new javax.swing.JLabel();
        lblTotalPengeluaran = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Keuangan Pribadi");

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Tanggal");

        jLabel2.setText("Keterangan");

        jLabel3.setText("Jumlah");

        jLabel4.setText("Tipe");

        cmbTipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pemasukan", "Pengeluaran" }));

        btnTambah.setBackground(new java.awt.Color(204, 204, 255));
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(204, 204, 255));
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(204, 204, 255));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBersih.setBackground(new java.awt.Color(204, 204, 255));
        btnBersih.setText("Bersih");
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tanggal", "Keterangan", "Pemasukan", "Pengeluaran"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(tblTransaksi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbTipe, 0, 380, Short.MAX_VALUE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKeterangan)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnHapus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(btnBersih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 66, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addGap(18, 18, 18)
                        .addComponent(btnUbah)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmbTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBersih))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotalPemasukan.setText("Pemasukan");

        lblTotalPengeluaran.setText("Pengeluaran");

        lblSaldo.setText("Saldo Akhir");

        btnExport.setBackground(new java.awt.Color(255, 255, 153));
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setBackground(new java.awt.Color(255, 255, 153));
        btnImport.setText("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTotalPemasukan)
                        .addGap(124, 124, 124)
                        .addComponent(lblTotalPengeluaran)
                        .addGap(142, 142, 142)
                        .addComponent(lblSaldo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPemasukan)
                    .addComponent(lblSaldo)
                    .addComponent(lblTotalPengeluaran))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExport)
                    .addComponent(btnImport))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    double jumlah; // Deklarasikan di sini
    try {
        String jumlahText = txtJumlah.getText();
        
        jumlah = Double.parseDouble(jumlahText);
        
    } catch (NumberFormatException e) {  
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Input 'Jumlah' harus berupa angka yang valid.\nContoh: 100000 (bukan '1kg' atau 'Rp 100.000')", 
            "Error Input", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return; 
    }
    LocalDate tanggal;
try {
    java.util.Date utilDate = jDateChooser1.getDate();
    if (utilDate == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Tanggal tidak boleh kosong.", "Error Input", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
    tanggal = utilDate.toInstant()
                       .atZone(java.time.ZoneId.systemDefault())
                       .toLocalDate();
} catch (HeadlessException e) {
    javax.swing.JOptionPane.showMessageDialog(this, "Format tanggal tidak valid.", "Error Input", javax.swing.JOptionPane.ERROR_MESSAGE);
    return;
}
String keterangan = txtKeterangan.getText();
    String tipe = cmbTipe.getSelectedItem().toString();
    Transaksi t = null;
    if (tipe.equals("Pemasukan")) {
        t = new Pemasukan(tanggal, keterangan, jumlah);
    } else {
        t = new Pengeluaran(tanggal, keterangan, jumlah);
    }
    dataManager.tambahTransaksi(t);
    refreshTabel();
    bersihForm();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
    int selectedRow = tblTransaksi.getSelectedRow();
    if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Pilih data di tabel yang ingin diubah.",
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    double jumlah; // Deklarasikan di sini

    // --- MULAI BLOK VALIDASI (SAMA SEPERTI TAMBAH) ---
    try {
        String jumlahText = txtJumlah.getText();
        jumlah = Double.parseDouble(jumlahText);
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Input 'Jumlah' harus berupa angka yang valid.",
            "Error Input",
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
    // --- SELESAI BLOK VALIDASI ---

    // 2. Ambil data dari form (Kita akan perbaiki tanggal di langkah berikutnya)
    LocalDate tanggal;
    try {
        // 1. Ambil java.util.Date dari JDateChooser
        java.util.Date utilDate = jDateChooser1.getDate();
        if (utilDate == null) {
            // 2. Tampilkan error jika tanggal kosong
            javax.swing.JOptionPane.showMessageDialog(this, "Tanggal tidak boleh kosong.", "Error Input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 3. Konversi ke LocalDate
        tanggal = utilDate.toInstant()
                           .atZone(java.time.ZoneId.systemDefault())
                           .toLocalDate();
    } catch (HeadlessException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Format tanggal tidak valid.", "Error Input", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
    String keterangan = txtKeterangan.getText();
    String tipe = cmbTipe.getSelectedItem().toString();

    // 3. Buat objek Model
    Transaksi t = null;
    if (tipe.equals("Pemasukan")) {
        t = new Pemasukan(tanggal, keterangan, jumlah);
    } else {
        t = new Pengeluaran(tanggal, keterangan, jumlah);
    }

    // 4. Panggil method ubahTransaksi (Ini sudah benar)
    dataManager.ubahTransaksi(selectedRow, t);

    // 5. Segarkan tabel dan bersihkan form
    refreshTabel();
    bersihForm();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
  bersihForm();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    int selectedRow = tblTransaksi.getSelectedRow();
     if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Pilih data di tabel yang ingin dihapus.", 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
     int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this, 
        "Apakah Anda yakin ingin menghapus data ini?", 
        "Konfirmasi Hapus", 
        javax.swing.JOptionPane.YES_NO_OPTION);
    
    if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {       
        dataManager.hapusTransaksi(selectedRow); 
        refreshTabel();
        bersihForm();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
    int selectedRow = tblTransaksi.getSelectedRow();
    
    if (selectedRow != -1) {
        // JANGAN AMBIL DATA DARI TABEL (karena sudah diformat "50.000")
        // AMBIL DATA ASLI DARI CONTROLLER
        Transaksi t = dataManager.getListTransaksi().get(selectedRow);

        // Set data ke form
        txtKeterangan.setText(t.getKeterangan());
        
        // Ambil jumlah ASLI (double), bukan string "50.000"
        txtJumlah.setText(String.valueOf(t.getJumlah())); 
        
        // Set tipe ComboBox
        cmbTipe.setSelectedItem(t.getTipe());

        // Set tanggal ke JDateChooser
        try {
            String tanggalStr = t.getTanggal().toString();
            java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
            jDateChooser1.setDate(date);
        } catch (java.text.ParseException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal parse tanggal dari tabel", e);
        }
    }

    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
    JFileChooser chooser = new JFileChooser();
    
    // Siapkan semua filter
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
            "Text file (*.txt)", "txt");
    
    // --- INI PERUBAHANNYA ---
    FileNameExtensionFilter xlsxFilter = new FileNameExtensionFilter( // Ganti nama variabel
            "Modern Excel file (*.xlsx)", "xlsx"); // Ganti ke .xlsx
    // --- BATAS PERUBAHAN ---
    
    FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter(
            "PDF file (*.pdf)", "pdf");
    
    // Tambahkan filter
    chooser.addChoosableFileFilter(txtFilter);
    chooser.addChoosableFileFilter(xlsxFilter); // Tambahkan filter .xlsx
    chooser.addChoosableFileFilter(pdfFilter);
    
    // Set filter default ke .xlsx
    chooser.setFileFilter(xlsxFilter); // Ganti default ke .xlsx
    
    int userSelection = chooser.showSaveDialog(this);

    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File fileToSave = chooser.getSelectedFile();
        
        FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) chooser.getFileFilter();
        String selectedExtension = selectedFilter.getExtensions()[0];
        
        // Pastikan ekstensi benar
        if (!fileToSave.getPath().endsWith("." + selectedExtension)) {
            fileToSave = new java.io.File(fileToSave.getPath() + "." + selectedExtension);
        }
        
        try {
            // Panggil method yang sesuai
            switch (selectedExtension) {
                case "txt" -> dataManager.exportData(fileToSave);
                // --- INI PERUBAHANNYA ---
                case "xlsx" -> dataManager.exportDataToXLSX(fileToSave, this.formatter);
                // --- BATAS PERUBAHAN ---
                // Panggil method XLSX baru
                case "pdf" -> dataManager.exportDataToPDF(fileToSave, this.formatter);
                default -> {
                }
            }
            
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Data berhasil diekspor ke format ." + selectedExtension,
                    "Ekspor Berhasil",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal mengekspor file: " + e.getMessage(),
                    "Error Ekspor",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
    JFileChooser chooser = new JFileChooser();
    
    // 2. Buat filter HANYA UNTUK .txt
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
            "Text file (*.txt)", "txt");
    
    chooser.addChoosableFileFilter(txtFilter);
    chooser.setFileFilter(txtFilter); // Set .txt sebagai default
    
    // 3. Tampilkan dialog "Open"
    int userSelection = chooser.showOpenDialog(this);
    
    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File fileToOpen = chooser.getSelectedFile();
        
        try {
            // 4. Panggil method import .txt Anda di DataManager
            dataManager.importData(fileToOpen);
            
            // 5. SANGAT PENTING: Refresh tabel setelah data baru masuk
            refreshTabel(); 
            
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Data berhasil diimpor dari:\n" + fileToOpen.getAbsolutePath(), 
                "Impor Berhasil", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Gagal membaca file: " + e.getMessage() + "\nPastikan format file .txt sudah benar.", 
                "Error Impor", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            // Tampilkan error di console
        }
    }

    }//GEN-LAST:event_btnImportActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbTipe;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTotalPemasukan;
    private javax.swing.JLabel lblTotalPengeluaran;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKeterangan;
    // End of variables declaration//GEN-END:variables
}