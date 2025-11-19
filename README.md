# 💸 Aplikasi Keuangan Pribadi - UTS PBO 2

Repositori ini berisi *source code* lengkap untuk proyek **Ujian Tengah Semester (UTS)** mata kuliah **Pemrograman Berorientasi Objek 2 (PBO2)**.

Aplikasi ini adalah program desktop sederhana yang berfungsi sebagai alat manajemen keuangan pribadi, dibangun menggunakan Java Swing dan dirancang dengan pondasi konsep PBO (OOP) yang kuat.

**Nama:** Achmad Rafiq Syaddid
**NPM:** 2310010677

---

## 📸 Tampilan Aplikasi & User Experience (UX) Versi 1 
<img width="697" height="605" alt="image" src="https://github.com/user-attachments/assets/f05f9018-2864-464f-aeec-21bec90aa9ae" />

## 📸 Tampilan Aplikasi & User Experience (UX) Versi 2 ( Yang digunakan Sekarang )
<img width="716" height="549" alt="image" src="https://github.com/user-attachments/assets/59652060-7a01-4996-b344-8bf605f10d25" />


## ✨ Fitur Utama & Kepatuhan Kriteria UTS

Aplikasi ini berhasil memenuhi semua kriteria dasar, Tantangan (20 Poin), dan kriteria inovasi dari tugas UTS:

### 1. Fungsionalitas Aplikasi & CRUD (20%)
* **Manajemen Data (CRUD):** Kemampuan untuk **Menambah**, **Mengubah**, dan **Menghapus** data transaksi dengan validasi input yang memastikan integritas data.
* **Logika Data *Robust*:** Menggunakan mekanisme *Unique ID Generation* dan *ID* transaksi tersembunyi pada tabel, memastikan proses Ubah dan Hapus selalu akurat.

### 2. Tantangan: Impor & Ekspor Data (20 Poin)
Fitur ini diimplementasikan untuk memenuhi poin Tantangan 20:
* **Ekspor Data Komprehensif:** Berhasil mengekspor data transaksi ke **4 format** *file* berbeda, yang memudahkan pelaporan dan *backup*:
    * `**.pdf**` (Menggunakan library **OpenPDF** untuk laporan formal).
    * `**.xlsx**` (Menggunakan library **Apache POI** untuk analisis data di Excel).
    * `**.txt**` (Format CSV sederhana dengan delimiter `;`).
    * `**.csv**` (Format standar CSV dengan delimiter `,`).
* **Impor Data:** Berhasil mengimpor data transaksi dari file `**.txt**` atau `**.csv**` dengan logika *parsing* yang mampu menangani *ID* yang hilang dan kesalahan format data.

### 3. Kreativitas & Inovasi (15%)
* **Perhitungan Saldo Berjalan (*Running Balance*):** Tabel utama menampilkan kolom "Saldo" yang menghitung sisa uang setelah setiap transaksi secara real-time dan kronologis.
* **Visualisasi Summary *Real-Time*:** **Summary Panel** menampilkan total "Pemasukan," "Pengeluaran," dan **"Saldo Akhir"** secara *real-time*.
* **Indikator Visual:** Saldo Akhir menggunakan indikator warna (**Merah** untuk defisit/negatif, **Biru/Hijau** untuk positif).
* **Format Angka Profesional:** Semua nilai uang di tabel dan *summary* diformat sebagai mata uang dengan pemisah ribuan dan desimal yang konsisten.

### 4. Desain dan User Experience (UX) (20%)
* **Desain Modern:** Antarmuka pengguna (UI) yang bersih dan modern menggunakan *library* eksternal **FlatLaf** (Dark Mode) untuk estetika yang profesional.
* **Kustomisasi Ikon:** Tombol-tombol fungsional (CRUD, Ekspor, Impor) menggunakan ikon kustom yang meningkatkan kemudahan penggunaan dan navigasi.

### 5. Dokumentasi Kode (10%)
* **Komentar Javadoc:** Setiap *class* dan *method* penting (termasuk *method helper* di `MainFrame.java` dan semua *method* bisnis di `DataManager.java`) didokumentasikan dengan komentar **Javadoc** yang informatif untuk mempermudah pemahaman dan *maintenance* kode.

---

## 🏛️ Struktur Proyek (Penerapan Konsep PBO)

Aplikasi ini dibangun menggunakan pola desain **Model-View-Controller (MVC)** untuk memastikan pemisahan tanggung jawab (*Separation of Concerns*) dan penerapan konsep PBO yang optimal.

| Paket | Class/Tanggung Jawab | Penerapan Konsep PBO |
| :--- | :--- | :--- |
| **`uts.model`** | **`Transaksi`**, `Pemasukan`, `Pengeluaran` | **Inheritance** (`Pemasukan` & `Pengeluaran` *extends* `Transaksi`) dan **Polymorphism** (`getTipe()`). **Encapsulation** pada semua atribut data. |
| **`uts.controller`** | **`DataManager`** | Menangani semua logika bisnis (CRUD, Kalkulasi, Impor/Ekspor). Mengimplementasikan prinsip **Abstraction**, memisahkan logika dari tampilan. |
| **`uts.ui`** | **`MainFrame`** | Menangani semua tampilan GUI (**View**) dan *event handling* (logika tombol), berinteraksi dengan `DataManager`. |
| **`uts.Images`** | Ikon-ikon tombol | Aset visual untuk UX. |

---

## 🚀 Teknologi dan Dependensi

* **Platform:** **Java** (JDK 17 atau lebih tinggi).
* **GUI Toolkit:** Java Swing.
* **IDE:** **NetBeans IDE** (digunakan untuk *form builder*).
* **Library Eksternal (Dependencies):**
    * **FlatLaf:** Untuk *Look and Feel* modern.
    * **JCalendar:** Untuk input tanggal `JDateChooser` yang intuitif.
    * **Apache POI:** Untuk fungsionalitas Ekspor ke `**.xlsx**` (Excel).
    * **iText 5 / OpenPDF:** Untuk fungsionalitas Ekspor ke `**.pdf**`.
