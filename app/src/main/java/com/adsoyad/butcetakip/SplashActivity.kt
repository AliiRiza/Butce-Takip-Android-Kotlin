package com.adsoyad.butcetakip

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Üstteki bildirim çubuğunu gizlemek istersen (Opsiyonel)
        supportActionBar?.hide()

        // 3 Saniye (3000 milisaniye) bekle ve Ana Sayfaya geç
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Geri tuşuna basınca tekrar splash ekranına dönmemesi için activity'i kapatıyoruz
        }, 3000)
    }
}