# 💸 Aplikasi Keuangan Pribadi - UTS PBO 2

Repositori ini berisi *source code* lengkap untuk proyek Ujian Tengah Semester (UTS) mata kuliah Pemrograman Berorientasi Objek 2 (PBO2).

**Nama:** Achmad Rafiq Syaddid
**NPM:** 2310010677

Aplikasi ini adalah program desktop sederhana untuk manajemen keuangan pribadi, yang dibangun menggunakan Java Swing dan menerapkan konsep PBO (OOP).

---

## 📸 Tampilan Aplikasi

<img width="694" height="611" alt="image" src="https://github.com/user-attachments/assets/3bc084d5-ea6e-4123-8d62-5b111e7ce9d3" />


## ✨ Fitur Utama

Aplikasi ini memenuhi semua kriteria dasar dan tantangan dari tugas:

* [cite_start]**Manajemen Data (CRUD):** Kemampuan untuk **Menambah**, **Mengubah**, dan **Menghapus** data transaksi[cite: 17].
* [cite_start]**Desain Modern:** Antarmuka pengguna (UI) yang bersih dan modern menggunakan *library* eksternal **FlatLaf** (Dark Mode)[cite: 18].
* **Kustomisasi Ikon:** Tombol fungsional (CRUD, Ekspor, Impor) menggunakan ikon kustom yang ukurannya disesuaikan.
* **Perhitungan Otomatis:**
    * **Format Angka:** Semua angka di tabel dan *summary* diformat sebagai mata uang (misal: `200.000`).
    * **Saldo Berjalan:** Tabel utama menampilkan kolom "Saldo" yang menghitung sisa uang setelah setiap transaksi.
    * **Summary Panel:** Menampilkan total "Pemasukan," "Pengeluaran," dan "Saldo Akhir" secara *real-time*.
* [cite_start]**Fitur Tantangan (Ekspor):** Berhasil mengekspor data ke 3 format berbeda[cite: 38]:
    * `.pdf` (Menggunakan library iText/OpenPDF)
    * `.xlsx` (Menggunakan library Apache POI)
    * `.txt` (Format CSV sederhana)
* [cite_start]**Fitur Tantangan (Impor):** Berhasil mengimpor data dari file `.txt`[cite: 38].

---

## 🏛️ Struktur Proyek (Penerapan Konsep PBO)

[cite_start]Aplikasi ini dibangun menggunakan pola desain **Model-View-Controller (MVC)** untuk memastikan penerapan konsep PBO yang baik (Abstraction, Encapsulation, Inheritance, Polymorphism)[cite: 15, 8].

* **`UTS.model` (Model):**
    * Berisi kelas data yang menerapkan **Inheritance** (`Transaksi.java` [abstract], `Pemasukan.java` [extends], `Pengeluaran.java` [extends]).
    * Menerapkan **Encapsulation** dengan atribut `private` dan *getter/setter* publik.

* **`UTS.Ui` (View):**
    * [cite_start]Berisi `MainFrame.java` yang menangani semua tampilan GUI[cite: 16].
    * [cite_start]Semua *event handling* (logika tombol) ada di file ini[cite: 8].

* **`UTS.controller` (Controller):**
    * Berisi `DataManager.java` yang bertindak sebagai "otak" aplikasi.
    * Menangani semua logika bisnis (CRUD, kalkulasi total, impor/ekspor data), memisahkan logika dari tampilan (prinsip **Abstraction**).

* **`UTS.Images`:**
    * Menyimpan semua aset gambar yang digunakan untuk ikon tombol.

---

## 🚀 Teknologi yang Digunakan

* [cite_start]**Java** (dengan Java Swing untuk GUI) [cite: 16]
* **NetBeans IDE**
* [cite_start]**Library Eksternal:** [cite: 31]
    * **FlatLaf:** Untuk tema dan *Look and Feel* modern.
    * **JCalendar:** Untuk input tanggal `JDateChooser`.
    * **Apache POI:** Untuk fungsionalitas Ekspor ke `.xlsx` (Excel).
    * **iText 5 (atau OpenPDF):** Untuk fungsionalitas Ekspor ke `.pdf`.
