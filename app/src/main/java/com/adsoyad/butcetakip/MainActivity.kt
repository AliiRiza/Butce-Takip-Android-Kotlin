package com.adsoyad.butcetakip // Burası senin paket adınla aynı kalmalı

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView // TextView eklendi
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: IslemAdapter
    lateinit var txtAnaBakiye: TextView
    lateinit var txtAyGelir: TextView
    lateinit var txtAyGider: TextView
    lateinit var btnEkle: Button
    lateinit var dbYardimcisi: VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Görünüm elemanlarını tanımla
        recyclerView = findViewById(R.id.recyclerView)
        txtAnaBakiye = findViewById(R.id.txtAnaBakiye)
        txtAyGelir = findViewById(R.id.txtAyGelir)
        txtAyGider = findViewById(R.id.txtAyGider)
        btnEkle = findViewById(R.id.btnEkle)

        // Veritabanı yardımcısını başlat
        dbYardimcisi = VeritabaniYardimcisi(this)

        // RecyclerView düzenini ayarla
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Ekle butonuna tıklama olayı
        btnEkle.setOnClickListener {
            val intent = Intent(this@MainActivity, IslemEkleActivity::class.java)
            startActivity(intent)
        }

        // --- ÇIKIŞ BUTONU AYARLARI ---
        // XML'de TextView yaptığımız için burada da TextView olarak çekiyoruz
        val btnCikis = findViewById<TextView>(R.id.btnCikis)

        btnCikis.setOnClickListener {
            Toast.makeText(this, "Çıkış yapıldı", Toast.LENGTH_SHORT).show()

            // Login Ekranına Yönlendir
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Bu sayfayı kapat (Geri tuşuyla dönülemesin diye)
            finish()
        }

        // Uygulama ilk açıldığında bakiyeyi ve listeyi getir
        bakiyeyiGuncelle()
    }

    // Başka sayfadan (Ekle sayfasından) geri dönüldüğünde çalışır
    override fun onResume() {
        super.onResume()
        bakiyeyiGuncelle()
    }

    // Bakiyeyi hesaplayan ve listeyi yenileyen fonksiyon
    fun bakiyeyiGuncelle() {
        val liste = dbYardimcisi.tumIslemleriGetir()
        var toplamBakiye = 0.0
        var ayGelir = 0.0
        var ayGider = 0.0

        val buAy = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

        for (islem in liste) {
            // Toplam Bakiye Hesabı
            if (islem.tur == 1) {
                toplamBakiye += islem.tutar
            } else {
                toplamBakiye -= islem.tutar
            }

            // Bu Ay Hesabı
            if (islem.tarih?.startsWith(buAy) == true) {
                if (islem.tur == 1) {
                    ayGelir += islem.tutar
                } else {
                    ayGider += islem.tutar
                }
            }
        }

        // Hesaplanan değerleri ekrana yazdır
        txtAnaBakiye.text = "$toplamBakiye TL"
        txtAyGelir.text = "+$ayGelir TL"
        txtAyGider.text = "-$ayGider TL"

        // Listeyi Adapter'a gönder ve Silme olayını tanımla
        adapter = IslemAdapter(liste) { secilenIslem ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("İşlemi Sil")
            builder.setMessage("${secilenIslem.aciklama} işlemini silmek istiyor musun?")

            builder.setPositiveButton("Evet") { _, _ ->
                dbYardimcisi.islemSil(secilenIslem.id) // Veritabanından sil
                bakiyeyiGuncelle() // Listeyi ve bakiyeyi güncelle
                Toast.makeText(this, "İşlem silindi", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Hayır", null)
            builder.show()
        }

        recyclerView.adapter = adapter
    }
}