package com.example.zakupy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class Spizarnia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spizarnia)

        val dB = Produkty(applicationContext)
        val produkty = arrayListOf<Produkt>()
        val produktListView = findViewById<ListView>(R.id.produktList)
        val thread = Thread {
            run {
                for (item in dB.wszystkieProdukty()) {
                    produkty.add(item)
                }
            }
        }
        runOnUiThread {
            val adapter = ProduktAdapter(this, produkty)
            produktListView.adapter = adapter
        }
        produktListView.setOnItemClickListener { _, _, position, _ ->
            val wybrany = produkty[position]
            val detailIntent = WybranyProdukt.newIntent(this, wybrany)
            startActivity(detailIntent)
        }
        thread.start()
    }
}