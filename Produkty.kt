package com.example.zakupy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Produkty(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private const val DATABASE_VER = 1
        private const val DATABASE_NAME = "Produkty.db"

        //Table
        private const val TABLE_PRODUKTY = "Produkty"
        private const val COL_ID_PRODUKTU = "Id"
        private const val COL_NAZWA_PRODUKTU = "Nazwa"
        private const val COL_ILOSC_PRODUKTU = "Ilosc"

        //Table
        private const val TABLE_PRZEPISY = "Przepisy"
        private const val COL_ID_PRZEPISU = "Id"
        private const val COL_NAZWA_PRZEPISU = "Nazwa"

        //Table
        private const val TABLE_SKLADNIKI = "Skladniki"
        private const val COL_ID_SKLADNIKU = "Id"
        private const val COL_NAZWA_SKLADNIKU = "Nazwa"
        private const val COL_ILOSC_SKLADNIKU = "Ilosc"
        private const val COL_ID_PRZEPISU2 = "IdPrzepisu"

        //Table
        private const val TABLE_LISTY = "Listy"
        private const val COL_ID_LISTY = "Id"
        private const val COL_OPIS_LISTY = "Opis"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQueryProdukty =
            ("CREATE TABLE $TABLE_PRODUKTY ($COL_ID_PRODUKTU INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAZWA_PRODUKTU TEXT, $COL_ILOSC_PRODUKTU INT)")
        db!!.execSQL(createTableQueryProdukty)
        val createTableQueryPrzepisy =
            ("CREATE TABLE $TABLE_PRZEPISY ($COL_ID_PRZEPISU INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAZWA_PRZEPISU TEXT)")
        db.execSQL(createTableQueryPrzepisy)
        val createTableQuerySkladniki =
            ("CREATE TABLE $TABLE_SKLADNIKI ($COL_ID_SKLADNIKU INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAZWA_SKLADNIKU TEXT, $COL_ILOSC_SKLADNIKU INT, $COL_ID_PRZEPISU2 INTEGER NOT NULL, FOREIGN KEY($COL_ID_PRZEPISU2) REFERENCES $TABLE_PRZEPISY($COL_ID_PRZEPISU))")
        db.execSQL(createTableQuerySkladniki)
        val createTableQueryListy =
            ("CREATE TABLE $TABLE_LISTY ($COL_ID_LISTY INTEGER PRIMARY KEY AUTOINCREMENT, $COL_OPIS_LISTY TEXT)")
        db.execSQL(createTableQueryListy)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUKTY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRZEPISY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SKLADNIKI")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LISTY")
        onCreate(db)
    }

    fun wszystkieProdukty(): List<Produkt> {
        val listaProduktow = ArrayList<Produkt>()
        val selectQuery = "SELECT * FROM $TABLE_PRODUKTY"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val produkt = Produkt()
                produkt.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_PRODUKTU))
                produkt.ilosc = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ILOSC_PRODUKTU))
                produkt.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRODUKTU))

                listaProduktow.add(produkt)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return listaProduktow
    }

    fun wszystkieListy(): List<Listy> {
        val listaList = ArrayList<Listy>()
        val selectQuery = "SELECT * FROM $TABLE_LISTY"
        val db = this.writableDatabase
        Log.d("TAG", "4")
        val cursor = db.rawQuery(selectQuery, null)
        Log.d("TAG", "5")
        if (cursor.moveToFirst()) {
            do {
                val listy = Listy()
                listy.opis = cursor.getString(cursor.getColumnIndexOrThrow(COL_OPIS_LISTY))
                listy.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_LISTY))

                listaList.add(listy)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return listaList
    }

    private fun pobieranieSkladnikow(idPrzepisu: Int): List<Produkt> {
        val listaProduktow = ArrayList<Produkt>()
        val selectQuery = "SELECT * FROM $TABLE_SKLADNIKI WHERE $COL_ID_PRZEPISU2 = $idPrzepisu"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val produkt = Produkt()
                produkt.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_SKLADNIKU))
                produkt.ilosc = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ILOSC_SKLADNIKU))
                produkt.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_SKLADNIKU))
                listaProduktow.add(produkt)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()

        return listaProduktow
    }

    fun wszystkiePrzepisy(): List<Przepis> {
        val listaPrzepisow = ArrayList<Przepis>()
        val selectQuery = "SELECT * FROM $TABLE_PRZEPISY"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val przepis = Przepis()
                przepis.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_PRZEPISU))
                przepis.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRZEPISU))
                przepis.skladniki = pobieranieSkladnikow(przepis.id)

                listaPrzepisow.add(przepis)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return listaPrzepisow
    }

    fun dodajProdukt(produkt: Produkt) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAZWA_PRODUKTU, produkt.nazwa)
        values.put(COL_ILOSC_PRODUKTU, produkt.ilosc)
        db.insert(TABLE_PRODUKTY, null, values)
        db.close()
    }

    fun dodajListe(opis: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_OPIS_LISTY, opis)
        db.insert(TABLE_LISTY, null, values)
        db.close()
    }

    fun dodajPrzepis(przepis: Przepis) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAZWA_PRZEPISU, przepis.nazwa)
        db.insert(TABLE_PRZEPISY, null, values)

        val selectQuery = "select Last_Insert_Rowid() as COL_ID_NWM from $TABLE_PRZEPISY"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val idPrzepisu = cursor.getInt(cursor.getColumnIndexOrThrow("COL_ID_NWM"))
            for (item in przepis.skladniki) {
                dodajSkladnik(item, idPrzepisu)
            }
        }
        cursor.close()
        db.close()
    }

    private fun dodajSkladnik(produkt: Produkt, idPrzepisu: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAZWA_SKLADNIKU, produkt.nazwa)
        values.put(COL_ILOSC_SKLADNIKU, produkt.ilosc)
        values.put(COL_ID_PRZEPISU2, idPrzepisu)
        db.insert(TABLE_SKLADNIKI, null, values)
        db.close()
    }

    fun edytujProdukt(id: Int, nowailosc: Int) {
        val selectQuery =
            "UPDATE $TABLE_PRODUKTY SET $COL_ILOSC_PRODUKTU = '$nowailosc' WHERE $COL_ID_PRODUKTU = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
        }
        cursor.close()
    }

    fun usunProdukt(id: Int) {
        val selectQuery = "DELETE FROM $TABLE_PRODUKTY WHERE $COL_ID_PRODUKTU = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
        }
        cursor.close()
    }

    fun usunPrzepis(id: Int) {
        val selectQuery = "DELETE FROM $TABLE_PRZEPISY WHERE $COL_ID_PRZEPISU = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
        }
        cursor.close()
    }

    fun czyNowyProdukt(nazwa: String): Boolean {
        val selectQuery = "SELECT * FROM $TABLE_PRODUKTY WHERE $COL_NAZWA_PRODUKTU = '$nazwa'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        return false
    }

    fun czyNowyPrzepis(nazwa: String): Boolean {
        val selectQuery = "SELECT * FROM $TABLE_PRZEPISY WHERE $COL_NAZWA_PRZEPISU = '$nazwa'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        return false
    }

    fun wezProduktPoId(id: Int): Produkt? {
        val selectQuery = "SELECT * FROM $TABLE_PRODUKTY WHERE $COL_ID_PRODUKTU = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val produkt = Produkt()
            produkt.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_PRODUKTU))
            produkt.ilosc = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ILOSC_PRODUKTU))
            produkt.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRODUKTU))
            return produkt
        }
        cursor.close()
        return null
    }

    fun wezPrzepisPoId(id: Int): Przepis? {
        val selectQuery = "SELECT * FROM $TABLE_PRZEPISY WHERE $COL_ID_PRZEPISU = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val przepis = Przepis()
            przepis.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_PRZEPISU))
            przepis.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRZEPISU))
            przepis.skladniki = pobieranieSkladnikow(przepis.id)
            return przepis
        }
        cursor.close()
        return null
    }

    fun wezPrzepisPoNazwe(nazwa: String): Przepis? {
        val selectQuery = "SELECT * FROM $TABLE_PRZEPISY WHERE $COL_NAZWA_PRZEPISU = '$nazwa'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val przepis = Przepis()
            przepis.nazwa = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAZWA_PRZEPISU))
            przepis.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRZEPISU))
            przepis.skladniki = pobieranieSkladnikow(przepis.id)
            return przepis
        }
        cursor.close()
        return null
    }

    fun wezIloscPoNazwie(nazwa: String): String? {
        val selectQuery = "SELECT * FROM $TABLE_PRODUKTY WHERE $COL_NAZWA_PRODUKTU = '$nazwa'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val produkt = Produkt()
            produkt.ilosc = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ILOSC_PRODUKTU))
            return produkt.ilosc.toString()
        }
        cursor.close()
        return null
    }

    fun wezIdPoNazwie(nazwa: String): Int? {
        val selectQuery = "SELECT * FROM $TABLE_PRODUKTY WHERE $COL_NAZWA_PRODUKTU = '$nazwa'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val produkt = Produkt()
            produkt.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PRODUKTU))
            return produkt.id
        }
        cursor.close()
        return null
    }

    fun czyMoznaZrobic(): ArrayList<String> {
        val mozliwosci = arrayListOf<String>()
        var ilosc: Int
        for (item in wszystkiePrzepisy()) {
            mozliwosci.add(item.nazwa)
            var iloscSkladnikow = 0
            var czyMoznaZrobic = 0
            for (skladnik in item.skladniki) {
                iloscSkladnikow++
                ilosc = if (wezIloscPoNazwie(skladnik.nazwa) == null) {
                    0
                } else {
                    wezIloscPoNazwie(skladnik.nazwa)!!.toInt()
                }
                if (skladnik.ilosc <= ilosc) {
                    czyMoznaZrobic++
                }
            }
            if (iloscSkladnikow == czyMoznaZrobic) {
                mozliwosci.add("true")
            } else {
                mozliwosci.add("false")
            }
        }
        return mozliwosci
    }

}