package com.example.zakupy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Lista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val dB = Produkty(applicationContext)
        val editTextSms = findViewById<EditText>(R.id.editTextSms)
        val buttonDodajDoListy = findViewById<Button>(R.id.button11)
        val buttonMenu = findViewById<Button>(R.id.button14)
        val buttonLista = findViewById<Button>(R.id.button17)
        val buttonDodajProdukt = findViewById<Button>(R.id.button19)
        val textViewListaZakupow = findViewById<TextView>(R.id.textView3)
        var listaZakupow = intent.getStringExtra("listaZakupow")
        val listy = arrayListOf<Listy>()
        if (listaZakupow == null) {
            listaZakupow = "Lista zakupów: \n"
        }

        textViewListaZakupow.text = listaZakupow

        buttonDodajDoListy.setOnClickListener {
            if (editTextSms.text.toString() != "") {
                var trueOrFalse = ""
                var nazwaPrzepisu = editTextSms.text.toString()
                nazwaPrzepisu =
                    nazwaPrzepisu[0].uppercaseChar() + nazwaPrzepisu.slice(1 until nazwaPrzepisu.length)
                        .lowercase()
                val mozliwosci = dB.czyMoznaZrobic()
                for (j in 0 until mozliwosci.size) {
                    if (mozliwosci[j] == nazwaPrzepisu) {
                        trueOrFalse = mozliwosci[j + 1]
                        break
                    }
                }
                if (trueOrFalse == "") {
                    Toast.makeText(
                        applicationContext,
                        "Taki przepis nie istnieje!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (trueOrFalse == "true") {
                    Toast.makeText(
                        applicationContext,
                        "Masz wszystkie potrzebne składniki!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val przepis = dB.wezPrzepisPoNazwe(nazwaPrzepisu)
                    for (item in przepis!!.skladniki) {
                        val iloscWprzepisie = item.ilosc
                        val iloscWspizarni = dB.wezIloscPoNazwie(item.nazwa)!!.toInt()
                        if (iloscWprzepisie > iloscWspizarni) {
                            listaZakupow += item.nazwa + ": " + (iloscWprzepisie - iloscWspizarni) + "\n"
                        }
                    }
                    textViewListaZakupow.text = listaZakupow
                    editTextSms.text.clear()
                    Toast.makeText(
                        applicationContext,
                        "Dodano brakujące składniki do listy.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(applicationContext, "Podaj nazwę przepisu!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonLista.setOnClickListener {
            if (listaZakupow != "Lista zakupów: \n") {
                //dB.dodajListe(listaZakupow)
                //for (item in dB.wszystkieListy()) {
                  //  Log.d("TAG", "2")
                    //listy.add(item)
                //}
                val builder = AlertDialog.Builder(this@Lista)
                builder.setMessage(listaZakupow)
                    .setCancelable(false)
                    .setNegativeButton("Schowaj") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else Toast.makeText(applicationContext, "Pusta lista!", Toast.LENGTH_SHORT)
                .show()
        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonDodajProdukt.setOnClickListener {

            val intent = Intent(this, DodajDoListy::class.java)
            intent.putExtra("listaZakupow", listaZakupow)
            startActivity(intent)
        }
    }
}