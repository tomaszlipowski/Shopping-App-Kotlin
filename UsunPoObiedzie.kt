package com.example.zakupy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class UsunPoObiedzie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usunpoobiedzie)

        val dB = Produkty(applicationContext)
        val editTextUsun = findViewById<EditText>(R.id.editTextUsun)
        val textViewOpis = findViewById<TextView>(R.id.textViewOpis)
        val buttonUsun = findViewById<ImageView>(R.id.trash)
        val buttonMenu = findViewById<Button>(R.id.button14)

        "Wpisz nazwę przepisu, aby usunąć produkty po obiedzie.".also { textViewOpis.text = it }

        buttonUsun.setOnClickListener {
            if (editTextUsun.text.toString() != "") {
                var trueOrFalse = ""
                var nazwaPrzepisu = editTextUsun.text.toString()
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
                } else if (trueOrFalse == "false") {
                    Toast.makeText(
                        applicationContext,
                        "Nawet nie było z czego tego zrobić!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val przepis = dB.wezPrzepisPoNazwe(nazwaPrzepisu)
                    for (item in przepis!!.skladniki) {
                        val id = dB.wezIdPoNazwie(item.nazwa)
                        val ilosc = dB.wezIloscPoNazwie(item.nazwa)!!.toInt()
                        dB.edytujProdukt(id!!, ilosc - item.ilosc)
                    }
                    Toast.makeText(
                        applicationContext,
                        "Usunięto produkty po obiedzie.",
                        Toast.LENGTH_SHORT
                    ).show()
                    editTextUsun.text.clear()
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
    }
}