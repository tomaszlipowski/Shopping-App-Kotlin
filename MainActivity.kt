package com.example.zakupy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonObiad = findViewById<Button>(R.id.button3)
        val buttonDodajPrzepis = findViewById<Button>(R.id.button12)
        val buttonLista = findViewById<Button>(R.id.button13)
        val buttonSpizarnia = findViewById<Button>(R.id.button15)
        val buttonDodajdoSpizarni = findViewById<Button>(R.id.button16)
        val buttonUsunPoObiedzie = findViewById<Button>(R.id.button18)
        //val db = Produkty(applicationContext)

        //this.deleteDatabase("Produkty.db")

        buttonDodajdoSpizarni.setOnClickListener {

            val intent = Intent(this, DodajProdukt::class.java)
            startActivity(intent)
        }

        buttonSpizarnia.setOnClickListener {
            val intent = Intent(this, Spizarnia::class.java)
            startActivity(intent)
        }

        buttonObiad.setOnClickListener {
            val intent = Intent(this, Obiad::class.java)
            startActivity(intent)
        }

        buttonDodajPrzepis.setOnClickListener {
            val intent = Intent(this, DodajPrzepis::class.java)
            startActivity(intent)
        }

        buttonUsunPoObiedzie.setOnClickListener {
            val intent = Intent(this, UsunPoObiedzie::class.java)
            startActivity(intent)
        }

        buttonLista.setOnClickListener {
            val intent = Intent(this, Lista::class.java)
            startActivity(intent)
        }

    }
}

data class Produkt(var nazwa: String = "", var ilosc: Int = 0, var id: Int = 0)
data class Listy(var opis: String = "", var id: Int = 0)
data class Przepis(
    var nazwa: String = "",
    var skladniki: List<Produkt> = emptyList(),
    var id: Int = 0
)