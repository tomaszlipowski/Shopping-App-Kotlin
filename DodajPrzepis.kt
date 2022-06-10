package com.example.zakupy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DodajPrzepis : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodajprzepis)

        val dB = Produkty(applicationContext)
        val buttonSkladnik = findViewById<Button>(R.id.buttonDodajDoPrzepisu)
        val buttonDodajPrzepis = findViewById<Button>(R.id.buttonDodajPrzepis)
        val textViewTwojPrzepis = findViewById<TextView>(R.id.textViewTwojPrzepis)
        val buttonMenu = findViewById<Button>(R.id.buttonMenu2)
        var twojPrzepis = "\n"
        var twojaNazwaPrzepisu = ""
        var nadany = false
        val nazwySkladnikow: MutableList<String> = ArrayList()
        val ilosciSkladnikow: MutableList<Int> = ArrayList()
        val textViewNazwa = findViewById<TextView>(R.id.textViewNazwa2)
        val editTextNazwaPrzepisu = findViewById<EditText>(R.id.editTextNazwaPrzepisu)
        val editTextNazwaProduktu = findViewById<EditText>(R.id.editTextNazwaProduktu)
        val editTextIlosc = findViewById<EditText>(R.id.editTextIlosc)

        buttonSkladnik.setOnClickListener {
            if (editTextNazwaPrzepisu.text.toString() != "") {
                if (editTextNazwaProduktu.text.toString() != "") {
                    if (findViewById<EditText>(R.id.editTextIlosc).text.toString() != "") {
                        var nazwaPrzepisu = editTextNazwaPrzepisu.text.toString().trim()
                        val ilosc = editTextIlosc.text.toString().toInt()
                        nazwaPrzepisu =
                            nazwaPrzepisu[0].uppercaseChar() + nazwaPrzepisu.slice(1 until nazwaPrzepisu.length)
                                .lowercase()
                        var nazwaProduktu = editTextNazwaProduktu.text.toString().trim()
                        nazwaProduktu =
                            nazwaProduktu[0].uppercaseChar() + nazwaProduktu.slice(1 until nazwaProduktu.length)
                                .lowercase()
                        nazwySkladnikow.add(nazwaProduktu)
                        ilosciSkladnikow.add(ilosc)
                        if (twojaNazwaPrzepisu != nazwaPrzepisu && twojaNazwaPrzepisu != "") {
                            nadany = false
                            nazwySkladnikow.clear()
                            ilosciSkladnikow.clear()
                            twojPrzepis = "\n"
                        }
                        if (!nadany) {
                            twojaNazwaPrzepisu = nazwaPrzepisu
                            textViewNazwa.text = "$nazwaPrzepisu:"
                            nadany = true
                        }
                        twojPrzepis += "$nazwaProduktu $ilosc\n"
                        textViewTwojPrzepis.text = twojPrzepis

                    } else {
                        Toast.makeText(applicationContext, "Podaj ilość!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Podaj nazwę składniku!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Podaj nazwę przepisu!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonDodajPrzepis.setOnClickListener {
            if (editTextNazwaPrzepisu.text.toString() != "") {
                val skladniki: MutableList<Produkt> = mutableListOf()
                var nazwaPrzepisu = editTextNazwaPrzepisu.text.toString()
                nazwaPrzepisu =
                    nazwaPrzepisu[0].uppercaseChar() + nazwaPrzepisu.slice(1 until nazwaPrzepisu.length)
                        .lowercase()
                if (!dB.czyNowyPrzepis(nazwaPrzepisu)) {
                    if (editTextNazwaProduktu.text.toString() != "") {

                        if (editTextIlosc.text.toString() != "") {
                            for (i in 0 until nazwySkladnikow.size) {
                                val mojProdukt = Produkt()
                                mojProdukt.nazwa = nazwySkladnikow[i]
                                mojProdukt.ilosc = ilosciSkladnikow[i]
                                skladniki.add(mojProdukt)
                            }

                            dB.dodajPrzepis(Przepis(nazwaPrzepisu, skladniki))
                            Toast.makeText(applicationContext, "Dodano przepis", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, Obiad::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(applicationContext, "Podaj ilość!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Podaj nazwę składniku!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Już znasz ten przepis!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Podaj nazwę przepisu!", Toast.LENGTH_SHORT)
                    .show()
            }


        }

    }
}