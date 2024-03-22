package com.example.tugasday5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

public class BonActivity extends AppCompatActivity {
    Button btnShare;
    String nama, tipeMember, kodeBarang, namaBarang;
    int hargaBarang, jumlahBarang, totalHarga, diskonMember, diskonHarga, jumlahBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon);

        btnShare = findViewById(R.id.btnShare);

        Intent intent = getIntent();
        if (intent != null) {
            nama = intent.getStringExtra("nama");
            tipeMember = intent.getStringExtra("tipeMember");
            kodeBarang = intent.getStringExtra("kodeBarang");
            namaBarang = getNamaBarang(kodeBarang); // Mendapatkan nama barang berdasarkan kode barang
            hargaBarang = intent.getIntExtra("hargaBarang", 0);
            jumlahBarang = intent.getIntExtra("jumlahBarang", 0);
            totalHarga = intent.getIntExtra("totalHarga", 0);
            diskonHarga = intent.getIntExtra("diskonHarga", 0);
            diskonMember = intent.getIntExtra("diskonMember", 0);
            jumlahBayar = intent.getIntExtra("jumlahBayar", 0);
        }

        // Menampilkan data transaksi pada TextView
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText(getString(R.string.welcome) + ", " + nama);

        TextView tvMember = findViewById(R.id.tvMember);
        tvMember.setText(getString(R.string.membership) + " : " + tipeMember);

        TextView tvKodeBarang = findViewById(R.id.tvKodeBarang);
        tvKodeBarang.setText(getString(R.string.kode_barang) + " : " + kodeBarang);

        TextView tvNamaBarang = findViewById(R.id.tvNamaBarang);
        tvNamaBarang.setText(getString(R.string.nama_barang) + " : " + namaBarang);

        TextView tvHargaBarang = findViewById(R.id.tvHargaBarang);
        tvHargaBarang.setText(getString(R.string.harga_barang) + " : Rp" + formatRupiah(hargaBarang));

        TextView tvJumlahBarang = findViewById(R.id.tvJumlahBarang);
        tvJumlahBarang.setText(getString(R.string.jumlah_barang) + " : " + jumlahBarang);

        TextView tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvTotalHarga.setText(getString(R.string.total_harga) + " : Rp" + formatRupiah(totalHarga));

        TextView tvDiskonHarga = findViewById(R.id.tvDiskonHarga);
        tvDiskonHarga.setText(getString(R.string.diskon_harga) + " : Rp" + formatRupiah(diskonHarga));

        TextView tvDiskonMember = findViewById(R.id.tvDiskonMember);
        tvDiskonMember.setText(getString(R.string.diskon_member) + " : Rp" + formatRupiah(diskonMember));

        TextView tvJumlahBayar = findViewById(R.id.tvJumlahBayar);
        tvJumlahBayar.setText(getString(R.string.jumlah_bayar) + " : Rp" + formatRupiah(jumlahBayar));

        // Memberikan listener untuk tombol share
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = generateBonText();
                String shareSubject = "Nota Pembelian";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Bagikan nota melalui"));
            }
        });
    }

    // Metode untuk mendapatkan nama barang berdasarkan kode barang
    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "SGS":
                return "Samsung Galaxy S";
            case "AV4":
                return "Asus Vivobook 14";
            case "MP3":
                return "Macbook Pro M3";
            default:
                return "Nama Barang Tidak Diketahui";
        }
    }

    // Metode untuk memformat harga menjadi format Rupiah
    private String formatRupiah(int harga) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(harga);
    }

    private String generateBonText() {
        String bonText = "\n " +
                getString(R.string.nama_pelanggan) + " : " + nama + "\n" +
                getString(R.string.tipe_member) + " : " + tipeMember + "\n" +
                getString(R.string.nama_barang) + " : " + namaBarang + "\n" +
                getString(R.string.harga_barang) + " : Rp" + formatRupiah(hargaBarang) + "\n" +
                getString(R.string.jumlah_barang) + " : " + jumlahBarang + "\n" +
                getString(R.string.total_harga) + " : Rp" + formatRupiah(totalHarga) + "\n" +
                getString(R.string.diskon_harga) + " : Rp" + formatRupiah(diskonHarga) + "\n" +
                getString(R.string.diskon_member) + " : Rp" + formatRupiah(diskonMember) + "\n" +
                getString(R.string.jumlah_bayar) + " : Rp" + formatRupiah(jumlahBayar);

        return bonText;
    }
}