package com.example.zakupy

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProduktAdapter(
    context: Context,
    private val dataSource: ArrayList<Produkt>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_item_produkty, parent, false)

        val nazwaTextView = rowView.findViewById(R.id.textView) as TextView
        val iloscTextView = rowView.findViewById(R.id.textView2) as TextView

        val produkt = getItem(position) as Produkt

        nazwaTextView.text = produkt.nazwa
        iloscTextView.text = "${produkt.ilosc} szt."
        return rowView
    }
}