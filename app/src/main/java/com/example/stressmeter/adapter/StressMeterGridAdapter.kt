package com.example.stressmeter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.stressmeter.R

class StressMeterGridAdapter(private val context: Context, private val imageUrls: List<Int>) :
        ArrayAdapter<Int>(context, 0, imageUrls) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.grid_item_stress_meter, parent, false)
        val imageView: ImageView = view.findViewById(R.id.image_view_item)
        imageView.setImageResource(imageUrls[position])
        imageView.tag = imageUrls[position]
        return view
    }
}