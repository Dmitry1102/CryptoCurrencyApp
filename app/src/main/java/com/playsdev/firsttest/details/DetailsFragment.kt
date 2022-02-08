package com.playsdev.firsttest.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import coil.load
import com.playsdev.firsttest.databinding.DetailsFragmentBinding
import com.playsdev.firsttest.databinding.MainFragmentBinding

class DetailsFragment: Fragment() {

    private var binding: DetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        binding?.ivIcon?.load(bundle?.getString("image"))
        binding?.tvName?.text = bundle?.getString("symbol")
        binding?.ivBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding?.tvCurrency?.text = bundle?.getString("current_price")

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}