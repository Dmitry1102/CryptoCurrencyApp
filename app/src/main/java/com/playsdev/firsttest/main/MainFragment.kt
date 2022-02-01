package com.playsdev.testapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.databinding.MainFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinViewModel
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import com.playsdev.testapp.sort.SortDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    private val viewModel by inject<CoinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CoinAdapter()

        binding?.ivSort?.setOnClickListener {
            val dialog = SortDialogFragment()
            dialog.show(childFragmentManager, TAG)
        }

        binding?.rvCurrency?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding?.rvCurrency?.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.stateFlow.collect {
                adapter.submitList(it.items)
            }
        }




    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val TAG = "SORT_FRAGMENT"
    }

}