package com.adsoyad.butcetakip

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IslemEkleActivity : AppCompatActivity() {

    lateinit var etAciklama: EditText
    lateinit var etTutar: EditText
    lateinit var rgTur: RadioGroup
    lateinit var btnKaydet: Button
    lateinit var dbYardimcisi: VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_islem_ekle)

        etAciklama = findViewById(R.id.etAciklama)
        etTutar = findViewById(R.id.etTutar)
        rgTur = findViewById(R.id.rgTur)
        btnKaydet = findViewById(R.id.btnKaydet)
        dbYardimcisi = VeritabaniYardimcisi(this)

        btnKaydet.setOnClickListener {
            val aciklama = etAciklama.text.toString()
            val tutarYazi = etTutar.text.toString()

            if (aciklama.isEmpty() || tutarYazi.isEmpty()) {
                Toast.makeText(this, "Lütfen alanları doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // HATA ÇÖZÜMÜ BURADA: String'i Double'a çeviriyoruz
            val tutar = tutarYazi.toDoubleOrNull() ?: 0.0

            // Radyo butonundan seçimi al (1: Gelir, 2: Gider)
            val secilenId = rgTur.checkedRadioButtonId
            val tur = if (secilenId == R.id.rbGelir) 1 else 2

            // Tarihi otomatik al
            val bugun = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Veritabanına gönder
            dbYardimcisi.islemEkle(aciklama, tutar, tur, bugun)

            finish() // Sayfayı kapat
        }
    }
}