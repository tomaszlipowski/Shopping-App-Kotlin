package com.example.zakupy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class Obiad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obiad)

        val dB = Produkty(applicationContext)
        val przepisy = arrayListOf<Przepis>()
        val mozliwosci = dB.czyMoznaZrobic()
        val przepislistView = findViewById<ListView>(R.id.przepisList)
        val thread = Thread {
            run {
                for (item in dB.wszystkiePrzepisy()) {
                    przepisy.add(item)
                }
            }
        }
        runOnUiThread {
            val adapter1 = PrzepisAdapter(this, przepisy, mozliwosci)
            przepislistView.adapter = adapter1
        }
        przepislistView.setOnItemClickListener { _, _, position, _ ->
            val wybrany = przepisy[position]
            val detailIntent = WybranyPrzepis.newIntent(this, wybrany)
            startActivity(detailIntent)
        }
        thread.start()


    }
}