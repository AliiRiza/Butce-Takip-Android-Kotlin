package com.adsoyad.butcetakip

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeritabaniYardimcisi(context: Context) : SQLiteOpenHelper(context, "butcedb", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE islemler (id INTEGER PRIMARY KEY AUTOINCREMENT, aciklama TEXT, tutar DOUBLE, tur INTEGER, tarih TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS islemler")
        onCreate(db)
    }

    // Ekleme Fonksiyonu (Düzeltildi)
    fun islemEkle(aciklama: String, tutar: Double, tur: Int, tarih: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("aciklama", aciklama)
        values.put("tutar", tutar)
        values.put("tur", tur)
        values.put("tarih", tarih)
        db.insert("islemler", null, values)
        db.close()
    }

    // Silme Fonksiyonu
    fun islemSil(id: Int) {
        val db = this.writableDatabase
        db.delete("islemler", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Listeleme Fonksiyonu (MainActivity'deki 'liste' hatasını çözer)
    fun tumIslemleriGetir(): ArrayList<IslemModel> {
        val liste = ArrayList<IslemModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM islemler", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val aciklama = cursor.getString(1)
            val tutar = cursor.getDouble(2)
            val tur = cursor.getInt(3)
            val tarih = cursor.getString(4)

            val islem = IslemModel(id, aciklama, tutar, tur, tarih)
            liste.add(islem)
        }
        cursor.close()
        db.close()
        return liste
    }
}