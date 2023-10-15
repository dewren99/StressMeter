package com.example.stressmeter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stressmeter.R
import com.example.stressmeter.adapter.StressMeterGridAdapter
import com.example.stressmeter.databinding.FragmentStressMeterBinding

class StressMeterFragment : Fragment() {

    private var _binding: FragmentStressMeterBinding? = null
    private lateinit var gridView: GridView
    private lateinit var buttonMoreImages: Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val stressMeterViewModel = ViewModelProvider(this)[StressMeterViewModel::class.java]

        _binding = FragmentStressMeterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        gridView = binding.gridViewStressMeter
        buttonMoreImages = binding.buttonMoreImages

        stressMeterViewModel.imageIdsMutable.observe(viewLifecycleOwner) {
            gridView.adapter = StressMeterGridAdapter(
                requireContext(),
                it
            )
        }

        buttonMoreImages.setOnClickListener {
            stressMeterViewModel.next()
        }

        stressMeterViewModel.next()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}