package com.example.stressmeter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stressmeter.R
import com.example.stressmeter.adapter.StressMeterGridAdapter
import com.example.stressmeter.databinding.FragmentStressMeterBinding
import com.example.stressmeter.managers.MediaPlayerManager

class StressMeterFragment : Fragment() {

    private var _binding: FragmentStressMeterBinding? = null
    private lateinit var gridView: GridView
    private lateinit var buttonMoreImages: Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var stressMeterViewModel: StressMeterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        stressMeterViewModel =
            ViewModelProvider(requireActivity())[StressMeterViewModel::class.java]

        _binding = FragmentStressMeterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        gridView = binding.gridViewStressMeter
        buttonMoreImages = binding.buttonMoreImages

        initListeners()

        stressMeterViewModel.next(false)
        return root
    }

    private fun initListeners() {
        stressMeterViewModel.imageIdsMutable.observe(viewLifecycleOwner) {
            gridView.adapter = StressMeterGridAdapter(
                requireContext(), it
            )
        }

        gridView.setOnItemClickListener { _, view, _, _ ->
            val resourceId = view.findViewById<ImageView>(R.id.image_view_item).tag as Int
            stressMeterViewModel.setSelectImage(resourceId)
            findNavController().navigate(R.id.action_nav_home_to_imageSelectionConfirmFragment)
        }

        buttonMoreImages.setOnClickListener {
            MediaPlayerManager.stop()
            stressMeterViewModel.next()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}