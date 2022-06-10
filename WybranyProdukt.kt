package com.example.zakupy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class WybranyProdukt : AppCompatActivity() {

    companion object {
        private const val EXTRA_ID_PRODUKTU = "id"

        fun newIntent(context: Context, produkt: Produkt): Intent {
            val detailIntent = Intent(context, WybranyProdukt::class.java)

            detailIntent.putExtra(EXTRA_ID_PRODUKTU, produkt.id)
            return detailIntent
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wybranyprodukt)

        val dB = Produkty(applicationContext)
        val id = intent.extras?.getInt(EXTRA_ID_PRODUKTU)
        val produkt = dB.wezProduktPoId(id!!)
        val viewNazwa = findViewById<TextView>(R.id.textView5)
        val editView = findViewById<EditText>(R.id.editTextNumberSigned)
        val buttonZmienIlosc = findViewById<Button>(R.id.button5)
        val buttonUsun = findViewById<ImageView>(R.id.imageView)
        val intent = Intent(this, Spizarnia::class.java)
        val viewOpis = findViewById<TextView>(R.id.textView6)
        val buttonSpizarnia = findViewById<Button>(R.id.button9)

        viewNazwa.text = produkt!!.nazwa
        viewOpis.text = "Aktualna ilość produktu w spiżarni:"
        editView.setText(produkt.ilosc.toString())


        buttonZmienIlosc.setOnClickListener {
            val nowa = editView.text.toString().toInt()
            dB.edytujProdukt(id, nowa)
            if (nowa != produkt.ilosc) {
                Toast.makeText(applicationContext, "Produkt zaktualizowany", Toast.LENGTH_SHORT)
                    .show()
            }
            startActivity(intent)
        }
        buttonUsun.setOnClickListener {
            val builder = AlertDialog.Builder(this@WybranyProdukt)
            builder.setMessage("Na pewno chcesz usunąć ten produkt ze spiżarni?")
                .setCancelable(false)
                .setPositiveButton("Tak") { _, _ ->
                    dB.usunProdukt(id)
                    Toast.makeText(applicationContext, "Produkt usunięty", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(intent)
                }
                .setNegativeButton("Nie") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        buttonSpizarnia.setOnClickListener {
            startActivity(intent)
        }
    }

}