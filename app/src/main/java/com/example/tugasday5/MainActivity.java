package com.example.tugasday5;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etNama, etKodeBarang, etJumlahBarang;
    RadioGroup rgMember;
    Button btnProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.etNama);
        etKodeBarang = findViewById(R.id.etKodeBarang);
        etJumlahBarang = findViewById(R.id.etJumlahBarang);
        rgMember = findViewById(R.id.rgMember);
        btnProses = findViewById(R.id.btnProses);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String kodeBarang = etKodeBarang.getText().toString();
                String jumlahBarangStr = etJumlahBarang.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    Toast.makeText(MainActivity.this, "Masukkan nama pelanggan", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahBarangStr)) {
                    Toast.makeText(MainActivity.this, "Masukkan jumlah barang terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }

                int jumlahBarang = Integer.parseInt(jumlahBarangStr);
                // Memproses diskon
                int selectedId = rgMember.getCheckedRadioButtonId();
                double diskonMember = 0;
                int hargaBarang = 0;
                String namaBarang = "";


                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    String tipeMember = radioButton.getText().toString();
                    switch (tipeMember) {
                        case "Gold":
                            diskonMember = 0.1; // Diskon 10% untuk member gold
                            break;
                        case "Silver":
                            diskonMember = 0.05; // Diskon 5% untuk member silver
                            break;
                        case "Normal":
                            diskonMember = 0.02; // Diskon 2% untuk member biasa
                            break;
                    }
                } else {
                    // Jika tidak ada RadioButton yang dipilih, berikan nilai default (tanpa diskon)
                    diskonMember = 0;
                }

                // Memproses harga barang berdasarkan kode barang dan mendapatkan nama barang
                switch (kodeBarang) {
                    case "SGS":
                        hargaBarang = 12999999; // Harga Samsung Galaxy S
                        namaBarang = "Samsung Galaxy S";
                        break;
                    case "AV4":
                        hargaBarang = 9150999; // Harga Asus Vivobook 14
                        namaBarang = "Asus Vivobook 14";
                        break;
                    case "MP3":
                        hargaBarang = 28999999; // Harga Macbook Pro M3
                        namaBarang = "Macbook Pro M3";
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
                        return;
                }

                // Menghitung total harga sebelum diskon
                int totalHarga = hargaBarang * jumlahBarang;

                // Menghitung diskon harga member
                int diskonHargaMember = (int) (totalHarga * diskonMember);

                // Memeriksa apakah total harga melebihi 10.000.000 dan menghitung potongan harga jika iya
                int potonganHarga = 0;
                if (totalHarga > 10000000) {
                    potonganHarga = 100000; // Potongan harga sebesar 100.000 jika total harga melebihi 10.000.000
                }

                // Menghitung jumlah bayar setelah diskon
                int jumlahBayar = totalHarga - diskonHargaMember - potonganHarga;

                // Mengirim data ke halaman bon/nota
                Intent intent = new Intent(MainActivity.this, BonActivity.class);
                intent.putExtra("nama", nama);
                intent.putExtra("tipeMember", ((RadioButton) findViewById(rgMember.getCheckedRadioButtonId())).getText().toString());
                intent.putExtra("kodeBarang", kodeBarang);
                intent.putExtra("namaBarang", namaBarang); // Menambahkan nama barang ke intent
                intent.putExtra("hargaBarang", hargaBarang);
                intent.putExtra("jumlahBarang", jumlahBarang);
                intent.putExtra("totalHarga", totalHarga);
                intent.putExtra("diskonHarga", potonganHarga);
                intent.putExtra("diskonMember", diskonHargaMember);
                intent.putExtra("jumlahBayar", jumlahBayar);
                startActivity(intent);
            }
        });
    }
}