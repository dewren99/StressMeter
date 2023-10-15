package com.example.stressmeter.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stressmeter.R

class ImageSelectionConfirmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_selection_confirm, container, false)

        val stressMeterViewModel =
            ViewModelProvider(requireActivity())[StressMeterViewModel::class.java]

        val imageView: ImageView = view.findViewById(R.id.image_view_selected)
        stressMeterViewModel.selectedImageMutable.observe(viewLifecycleOwner) {
            imageView.setImageResource(it)
        }

        view.findViewById<Button>(R.id.button_stress_image_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_imageSelectionConfirmFragment_to_nav_home)
        }
        return view
    }
}