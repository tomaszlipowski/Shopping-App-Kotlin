package com.example.zakupy

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.graphics.Color

class PrzepisAdapter(
    context: Context,
    private val dataSource: ArrayList<Przepis>, private val mozliwosci: ArrayList<String>
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

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_item_przepisy, parent, false)

        val nazwaTextView = rowView.findViewById(R.id.textViewNazwa) as TextView
        val bodyTextView = rowView.findViewById(R.id.textViewBody) as TextView
        val przepis = getItem(position) as Przepis
        var i = 0


        while (mozliwosci[i] != przepis.nazwa) {
            i++
        }
        val czyMoznaZrobic = mozliwosci[i + 1]
        if (czyMoznaZrobic == "true") {
            nazwaTextView.setBackgroundColor(Color.GREEN)
            bodyTextView.setBackgroundColor(Color.GREEN)
            bodyTextView.text = "Można zrobić"
        } else {
            nazwaTextView.setBackgroundColor(Color.RED)
            bodyTextView.setBackgroundColor(Color.RED)
            bodyTextView.text = "Nie można zrobić"
        }
        nazwaTextView.text = przepis.nazwa

        return rowView
    }
}