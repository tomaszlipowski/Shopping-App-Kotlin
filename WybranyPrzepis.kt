package com.example.zakupy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class WybranyPrzepis : AppCompatActivity() {

    companion object {
        private const val EXTRA_ID_PRZEPISU = "id"

        fun newIntent(context: Context, przepis: Przepis): Intent {
            val detailIntent = Intent(context, WybranyPrzepis::class.java)

            detailIntent.putExtra(EXTRA_ID_PRZEPISU, przepis.id)
            return detailIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wybranyprzepis)

        val dB = Produkty(applicationContext)
        val id = intent.extras?.getInt(EXTRA_ID_PRZEPISU)
        val przepis = dB.wezPrzepisPoId(id!!)
        val viewNazwaPrzepisu = findViewById<TextView>(R.id.textViewNazwaPrzepisu)
        val viewBody = findViewById<TextView>(R.id.textViewBody)
        val buttonUsun = findViewById<ImageView>(R.id.imageView2)
        val intent = Intent(this, Obiad::class.java)
        val buttonObiad = findViewById<Button>(R.id.button9)
        val skladniki: List<Produkt> = przepis!!.skladniki
        var body = ""
        var counter = 0
        var ilosc: Int

        viewNazwaPrzepisu.text = przepis.nazwa
        for (item in skladniki) {
            if (dB.wezIloscPoNazwie(item.nazwa) == null) {
                ilosc = 0
            } else {
                ilosc = dB.wezIloscPoNazwie(item.nazwa)!!.toInt()
            }
            body += (++counter).toString() + ". " + item.nazwa + " " + ilosc + " / " + item.ilosc + "\n"
        }
        viewBody.text = body

        buttonUsun.setOnClickListener {
            val builder = AlertDialog.Builder(this@WybranyPrzepis)
            builder.setMessage("Na pewno chcesz usunąć ten przepis?")
                .setCancelable(false)
                .setPositiveButton("Tak") { _, _ ->
                    dB.usunPrzepis(id)
                    Toast.makeText(applicationContext, "Przepis usunięty", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(intent)
                }
                .setNegativeButton("Nie") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        buttonObiad.setOnClickListener {
            startActivity(intent)
        }
    }

}