package com.adsoyad.butcetakip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var etKullaniciAdi: EditText
    lateinit var etSifre: EditText
    lateinit var btnGiris: Button
    lateinit var txtBaslik: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etKullaniciAdi = findViewById(R.id.etKullaniciAdi)
        etSifre = findViewById(R.id.etSifre)
        btnGiris = findViewById(R.id.btnGiris)
        txtBaslik = findViewById(R.id.txtBaslik)

        // SharedPreferences: Basit veri saklama alanı
        val sharedPreferences = getSharedPreferences("KullaniciBilgi", Context.MODE_PRIVATE)

        // Daha önce kayıtlı bir kullanıcı adı var mı kontrol et
        val kayitliKullanici = sharedPreferences.getString("kullaniciAdi", null)
        val kayitliSifre = sharedPreferences.getString("sifre", null)

        if (kayitliKullanici == null) {
            // HİÇ KAYIT YOKSA -> KAYIT MODU
            txtBaslik.text = "İLK KURULUM & KAYIT"
            btnGiris.text = "KAYDET VE GİRİŞ YAP"

            btnGiris.setOnClickListener {
                val yeniKullanici = etKullaniciAdi.text.toString()
                val yeniSifre = etSifre.text.toString()

                if (yeniKullanici.isNotEmpty() && yeniSifre.isNotEmpty()) {
                    // Verileri telefona kaydet
                    val editor = sharedPreferences.edit()
                    editor.putString("kullaniciAdi", yeniKullanici)
                    editor.putString("sifre", yeniSifre)
                    editor.apply()

                    Toast.makeText(this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show()
                    anaSayfayaGit()
                } else {
                    Toast.makeText(this, "Alanlar boş geçilemez", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // KAYIT VARSA -> GİRİŞ MODU
            txtBaslik.text = "TEKRAR HOŞGELDİNİZ"
            btnGiris.text = "GİRİŞ YAP"

            // Kullanıcı kolaylık olsun diye kullanıcı adını otomatik dolduralım
            etKullaniciAdi.setText(kayitliKullanici)

            btnGiris.setOnClickListener {
                val girilenKullanici = etKullaniciAdi.text.toString()
                val girilenSifre = etSifre.text.toString()

                if (girilenKullanici == kayitliKullanici && girilenSifre == kayitliSifre) {
                    anaSayfayaGit()
                } else {
                    Toast.makeText(this, "Hatalı Kullanıcı Adı veya Şifre!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun anaSayfayaGit() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Geri tuşuna basınca tekrar login ekranına dönmesin
    }
}