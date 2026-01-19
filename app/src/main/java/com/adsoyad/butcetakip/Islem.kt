package com.adsoyad.butcetakip

// Bu sınıf, bir harcama veya gelirin özelliklerini tutar.
class Islem(
    var id: Int = 0,          // Veritabanındaki sıra numarası
    var aciklama: String,     // "Market", "Kira" vb.
    var tutar: Double,        // 50.0, 100.0 vb.
    var tur: Int,             // 0: Gider, 1: Gelir
    var tarih: String // YENİ: Tarih bilgisi (Format: yyyy-MM-dd)
)