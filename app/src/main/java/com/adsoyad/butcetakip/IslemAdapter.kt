package com.adsoyad.butcetakip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IslemAdapter(
    private val islemListesi: ArrayList<IslemModel>,
    private val onIslemClick: (IslemModel) -> Unit
) : RecyclerView.Adapter<IslemAdapter.IslemHolder>() {

    class IslemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAciklama: TextView = itemView.findViewById(R.id.txtRowAciklama)
        val txtTutar: TextView = itemView.findViewById(R.id.txtRowTutar)
        val txtTarih: TextView = itemView.findViewById(R.id.txtRowTarih)
        // Yeni eklediğimiz silme butonu
        val btnSil: ImageView = itemView.findViewById(R.id.btnSil)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IslemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_islem, parent, false)
        return IslemHolder(view)
    }

    override fun onBindViewHolder(holder: IslemHolder, position: Int) {
        val mevcutIslem = islemListesi[position]

        holder.txtAciklama.text = mevcutIslem.aciklama
        holder.txtTutar.text = "${mevcutIslem.tutar} TL"
        holder.txtTarih.text = mevcutIslem.tarih

        // Renk ayarlaması
        if (mevcutIslem.tur == 1) {
            holder.txtTutar.setTextColor(android.graphics.Color.GREEN)
        } else {
            holder.txtTutar.setTextColor(android.graphics.Color.RED)
        }

        // DEĞİŞİKLİK BURADA:
        // Artık tüm satıra (itemView) değil, sadece çöp kutusuna (btnSil) basınca çalışacak.
        holder.btnSil.setOnClickListener {
            onIslemClick(mevcutIslem)
        }
    }

    override fun getItemCount(): Int {
        return islemListesi.size
    }
}