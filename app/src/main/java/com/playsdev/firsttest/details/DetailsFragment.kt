package com.playsdev.firsttest.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.DetailsFragmentBinding

class DetailsFragment: Fragment() {

    private var binding: DetailsFragmentBinding? = null
    private var coinArgs: Coin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        coinArgs = DetailsFragmentArgs.fromBundle(args).modelArgs

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvName?.text = coinArgs?.symbol
        binding?.tvCurrency?.text = coinArgs?.current_price.toString()
        binding?.ivIcon?.load(coinArgs?.image)

        binding?.ivBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}