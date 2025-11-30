import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    private String nama;
    private double harga;
    private String kategori; // "makanan" atau "minuman"

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter & Setter
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }

    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }

    @Override
    public String toString() {
        return nama + " - Rp " + String.format("%,.0f", harga);
    }
}

class Pesanan {
    Menu menu;
    int jumlah;

    public Pesanan(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }
}

public class Main {
    private static ArrayList<Menu> daftarMenu = new ArrayList<>();
    private static ArrayList<Pesanan> keranjang = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Data awal menu (minimal 4 makanan + 4 minuman)
        inisialisasiMenu();

        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN SEDERHANA ===");
            System.out.println("1. Pesan Makanan/Minuman (Pelanggan)");
            System.out.println("2. Kelola Menu Restoran (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            String pilihan = sc.nextLine();

            switch (pilihan) {
                case "1" -> menuPelanggan();
                case "2" -> menuPemilik();
                case "3" -> {
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void inisialisasiMenu() {
        // Makanan
        daftarMenu.add(new Menu("Nasi Goreng Spesial", 35000, "makanan"));
        daftarMenu.add(new Menu("Ayam Bakar Madu", 45000, "makanan"));
        daftarMenu.add(new Menu("Mie Goreng Jawa", 30000, "makanan"));
        daftarMenu.add(new Menu("Sate Ayam 10 Tusuk", 40000, "makanan"));

        // Minuman
        daftarMenu.add(new Menu("Es Teh Manis", 8000, "minuman"));
        daftarMenu.add(new Menu("Jus Jeruk", 15000, "minuman"));
        daftarMenu.add(new Menu("Kopi Hitam", 12000, "minuman"));
        daftarMenu.add(new Menu("Milkshake Cokelat", 25000, "minuman"));
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== DAFTAR MENU RESTORAN ===");
        System.out.println("MAKANAN:");
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).getKategori().equals("makanan")) {
                System.out.println((i + 1) + ". " + daftarMenu.get(i));
            }
        }
        System.out.println("\nMINUMAN:");
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).getKategori().equals("minuman")) {
                System.out.println((i + 1) + ". " + daftarMenu.get(i));
            }
        }
    }

    private static void menuPelanggan() {
        keranjang.clear();
        tampilkanMenu();

        while (true) {
            System.out.print("\nMasukkan nomor menu yang ingin dipesan (ketik 'selesai' untuk selesai): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                if (keranjang.isEmpty()) {
                    System.out.println("Keranjang kosong! Silakan pesan terlebih dahulu.");
                    continue;
                }
                cetakStruk();
                break;
            }

            try {
                int nomor = Integer.parseInt(input) - 1;
                if (nomor < 0 || nomor >= daftarMenu.size()) {
                    System.out.println("Nomor menu tidak valid!");
                    continue;
                }

                System.out.print("Jumlah pesanan " + daftarMenu.get(nomor).getNama() + ": ");
                int jumlah = Integer.parseInt(sc.nextLine());

                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0!");
                    continue;
                }

                keranjang.add(new Pesanan(daftarMenu.get(nomor), jumlah));
                System.out.println(daftarMenu.get(nomor).getNama() + " x" + jumlah + " ditambahkan ke keranjang.");

            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Harus angka atau 'selesai'.");
            }
        }
    }
  
    private static void cetakStruk() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              STRUK PEMBAYARAN");
        System.out.println("=".repeat(50));

        double subtotal = 0;
        int totalMinumanGratis = 0;

        for (Pesanan p : keranjang) {
            double hargaItem = p.menu.getHarga() * p.jumlah;
            subtotal += hargaItem;

            // Cek apakah ada minuman yang bisa B1G1
            if (p.menu.getKategori().equals("minuman") && p.jumlah >= 2) {
                int gratis = p.jumlah / 2; // beli 1 gratis 1
                totalMinumanGratis += gratis;
            }

            System.out.printf("%-25s x%2d  Rp %,10.0f\n",
                    p.menu.getNama(), p.jumlah, hargaItem);
        }

        // Hitung ulang subtotal setelah B1G1 (jika ada)
        if (totalMinumanGratis > 0 && subtotal > 50000) {
            double potonganB1G1 = 0;
            for (Pesanan p : keranjang) {
                if (p.menu.getKategori().equals("minuman") && p.jumlah >= 2) {
                    int gratis = p.jumlah / 2;
                    potonganB1G1 += gratis * p.menu.getHarga();
                }
            }
            subtotal -= potonganB1G1;
            System.out.printf("%-25s  -Rp %,10.0f\n", "Beli 1 Gratis 1 (Minuman)", potonganB1G1);
        }

        System.out.println("-".repeat(50));
        System.out.printf("%-30s Rp %,10.0f\n", "Subtotal", subtotal);

        // Diskon 10% jika > 100.000
        double diskon = 0;
        if (subtotal > 100000) {
            diskon = subtotal * 0.10;
            System.out.printf("%-30s -Rp %,10.0f\n", "Diskon 10%", diskon);
        }

        double setelahDiskon = subtotal - diskon;
        double pajak = setelahDiskon * 0.10;
        double biayaPelayanan = 20000;

        System.out.printf("%-30s Rp %,10.0f\n", "Pajak 10%", pajak);
        System.out.printf("%-30s Rp %,10.0f\n", "Biaya Pelayanan", biayaPelayanan);

        double totalAkhir = setelahDiskon + pajak + biayaPelayanan;

        System.out.println("=".repeat(50));
        System.out.printf("%-30s Rp %,10.0f\n", "TOTAL BAYAR", totalAkhir);
        System.out.println("=".repeat(50));
        System.out.println("Terima kasih atas kunjungannya!\n");
    }

    private static void menuPemilik() {
        while (true) {
            System.out.println("\n=== MENU PEMILIK RESTORAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Lihat Daftar Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih: ");
            String pilihan = sc.nextLine();

            switch (pilihan) {
                case "1" -> tambahMenu();
                case "2" -> ubahHarga();
                case "3" -> hapusMenu();
                case "4" -> tampilkanMenu();
                case "5" -> { return; }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void tambahMenu() {
        System.out.println("\n--- Tambah Menu Baru ---");
        System.out.print("Nama menu: ");
        String nama = sc.nextLine();
        System.out.print("Harga: ");
        double harga = Double.parseDouble(sc.nextLine());
        System.out.print("Kategori (makanan/minuman): ");
        String kategori = sc.nextLine().toLowerCase();

        if (!kategori.equals("makanan") && !kategori.equals("minuman")) {
            System.out.println("Kategori hanya boleh 'makanan' atau 'minuman'!");
            return;
        }

        daftarMenu.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan!");
    }

    private static void ubahHarga() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu yang ingin diubah harganya: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= daftarMenu.size()) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.println("Menu: " + daftarMenu.get(index).getNama());
        System.out.print("Harga baru: ");
        double hargaBaru = Double.parseDouble(sc.nextLine());

        System.out.print("Yakin ingin mengubah harga? (Ya/Tidak): ");
        if (sc.nextLine().equalsIgnoreCase("Ya")) {
            daftarMenu.get(index).setHarga(hargaBaru);
            System.out.println("Harga berhasil diubah!");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    private static void hapusMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu yang ingin dihapus: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= daftarMenu.size()) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.println("Menu yang akan dihapus: " + daftarMenu.get(index).getNama());
        System.out.print("Yakin ingin menghapus? (Ya/Tidak): ");
        if (sc.nextLine().equalsIgnoreCase("Ya")) {
            daftarMenu.remove(index);
            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    private static void tampilkanMenuDenganNomor() {
        System.out.println("\n=== DAFTAR MENU (dengan nomor) ===");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.println((i + 1) + ". [" + daftarMenu.get(i).getKategori().toUpperCase() + "] " + daftarMenu.get(i));
        }
    }
}