package com.playsdev.testapp.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.playsdev.firsttest.R
import com.playsdev.firsttest.databinding.SortFragmentBinding

class SortDialogFragment: DialogFragment() {

    private var binding: SortFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SortFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rbPrice?.setOnClickListener{
            binding?.rbPrice?.setBackgroundResource(R.drawable.radio_button_checked_20)
            binding?.rbAlphabetic?.setBackgroundResource(R.drawable.radio_button_unchecked_20)
        }

        binding?.rbAlphabetic?.setOnClickListener {
            binding?.rbPrice?.setBackgroundResource(R.drawable.radio_button_unchecked_20)
            binding?.rbAlphabetic?.setBackgroundResource(R.drawable.radio_button_checked_20)
        }

        binding?.tvCancel?.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}