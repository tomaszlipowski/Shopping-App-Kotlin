package com.example.zakupy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DodajProdukt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodajprodukt)

        val dB = Produkty(applicationContext)
        val buttonDodaj = findViewById<Button>(R.id.button4)
        val buttonMenu = findViewById<Button>(R.id.button8)
        val buttonSpizarnia = findViewById<Button>(R.id.button10)
        val editTextNazwa = findViewById<EditText>(R.id.editText)
        val editTextIlosc = findViewById<EditText>(R.id.editText2)

        buttonDodaj.setOnClickListener {
            if (editTextNazwa.text.toString() != "") {
                if (editTextIlosc.text.toString() != "") {
                    var nazwa = editTextNazwa.text.toString().trim()
                    val ilosc = editTextIlosc.text.toString().toInt()
                    nazwa = nazwa[0].uppercaseChar() + nazwa.slice(1 until nazwa.length).lowercase()
                    if (dB.czyNowyProdukt(nazwa)) {
                        Toast.makeText(
                            applicationContext,
                            "Produkt już jest w spiźarni!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        dB.dodajProdukt(Produkt(nazwa, ilosc))
                        editTextNazwa.text.clear()
                        editTextIlosc.text.clear()
                        Toast.makeText(applicationContext, "Dodano produkt", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Podaj ilość!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Podaj nazwę!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonSpizarnia.setOnClickListener {
            val intent = Intent(this, Spizarnia::class.java)
            startActivity(intent)
        }

    }
}