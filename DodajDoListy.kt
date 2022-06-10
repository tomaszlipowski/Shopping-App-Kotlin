package com.example.zakupy


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DodajDoListy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_do_listy)

        val editTextNazwaProduktu = findViewById<EditText>(R.id.editTextNazwaProduktu2)
        val editTextIlosc = findViewById<EditText>(R.id.editTextIlosc2)
        val buttonDodajDoListy = findViewById<Button>(R.id.button20)
        val buttonMenu = findViewById<Button>(R.id.button21)
        val buttonWroc = findViewById<Button>(R.id.button22)
        var nazwa = ""
        var ilosc = ""
        var listaZakupow = intent.getStringExtra("listaZakupow")

        buttonDodajDoListy.setOnClickListener {
            if (editTextNazwaProduktu.text.toString() == "")
                Toast.makeText(applicationContext, "Podaj nazwę produktu!", Toast.LENGTH_SHORT)
                    .show()
            else if (editTextIlosc.text.toString() == "")
                Toast.makeText(applicationContext, "Podaj ilość!", Toast.LENGTH_SHORT).show()
            else {
                nazwa = editTextNazwaProduktu.text.toString()
                nazwa = nazwa[0].uppercaseChar() + nazwa.slice(1 until nazwa.length).lowercase()
                ilosc = editTextIlosc.text.toString()

            }
            val intent = Intent(this, Lista::class.java)
            listaZakupow += "$nazwa: $ilosc\n"
            intent.putExtra("listaZakupow", listaZakupow)
            Toast.makeText(applicationContext, "Dodano produkt do listy", Toast.LENGTH_SHORT)
                .show()
            startActivity(intent)
        }

        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonWroc.setOnClickListener {
            val intent = Intent(this, Lista::class.java)
            intent.putExtra("listaZakupow", listaZakupow)
            startActivity(intent)
        }

    }
}