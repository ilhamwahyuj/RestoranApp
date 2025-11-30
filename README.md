![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Selesai%20Tugas%20Praktik-brightgreen)
![Version](https://img.shields.io/badge/Version-1.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Made with](https://img.shields.io/badge/Made%20with-%E2%9D%A4-red)

# Aplikasi Restoran Sederhana - Java Console  
Aplikasi manajemen restoran berbasis console (CLI) lengkap dengan fitur pemesanan, diskon, promo B1G1, pajak, biaya layanan, struk otomatis, serta manajemen menu untuk pemilik.

## Fitur Utama

### Pelanggan
- Lihat menu terpisah (Makanan & Minuman)
- Pesan tanpa batas hingga ketik `selesai`
- Validasi input ketat
- Diskon 10% jika total > Rp100.000
- Promo **Beli 1 Gratis 1** semua minuman jika total > Rp50.000
- Pajak 10% + biaya pelayanan Rp20.000
- Cetak struk pembayaran rapi

### Pemilik Restoran
- Tambah menu baru
- Ubah harga menu
- Hapus menu (dengan konfirmasi Ya/Tidak)
- Navigasi menu yang mudah

## Teknologi
- Java SE (JDK 17+)
- OOP (kelas `Menu`, `Pesanan`, `Main`)
- `ArrayList` untuk data dinamis
- Input validation + exception handling

## Struktur Proyek
RestoranApp/</br> 
├── Main.java           ← File utama (semua kode di sini)</br> 
├── README.md         ← Dokumentasi ini</br> 
└── .gitignore         ← (opsional)

## Cara Menjalankan
 1. Clone repo
    git clone https://github.com/ilhamwahyuj/RestoranApp
 2. Masuk folder
    cd RestoranApp
 3. Compile & jalankan
    javac Main.java
    java Main

    
