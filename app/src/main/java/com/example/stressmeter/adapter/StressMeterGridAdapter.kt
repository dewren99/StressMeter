package com.example.stressmeter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stressmeter.R
import com.example.stressmeter.ui.home.StressMeterViewModel

class StressMeterGridAdapter(private val context: Context, private val imageUrls: List<Int>) :
        ArrayAdapter<Int>(context, 0, imageUrls) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.grid_item_stress_meter, parent, false)
        val imageView: ImageView = view.findViewById(R.id.image_view_item)
        val textView: TextView = view.findViewById(R.id.image_score)

        val imageResourceId = imageUrls[position]
        val imageStressScore = StressMeterViewModel.getStressScore(imageResourceId).toString()
        val desc = "Score: $imageStressScore"
        println(desc)
        imageView.setImageResource(imageResourceId)
        imageView.tag = imageResourceId
        textView.text = desc
        return view
    }
}