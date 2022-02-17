package com.playsdev.testapp.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.playsdev.firsttest.BaseFragment
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.adapter.OnClickListener
import com.playsdev.firsttest.databinding.MainFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val coinItemListener = OnClickListener { coin, icon, name, cost ->
        val direction: NavDirections =
            MainFragmentDirections.actionMainFragmentToDetailsFragment(coin)
        val extras = FragmentNavigatorExtras(
            icon to coin.image,
            name to coin.symbol,
            cost to coin.current_price.toString()
        )
        findNavController().navigate(direction, extras)
    }

    private val viewModel: CoinViewModel by viewModel()
    private val coinViewModel: CoinDataBaseViewModel by viewModel()
    private var coinAdapter = CoinAdapter(coinItemListener)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): MainFragmentBinding? = MainFragmentBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivSort.setOnClickListener { makeDialogFragment() }
        setAdapter()
        lifecycleScope.launch {
            coinViewModel.getCoinList().collect {
                coinAdapter.submitData(it)
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getCoinToAdapter().distinctUntilChanged().collectLatest {
                    coinAdapter.submitData(it)
                }
            }
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun makeDialogFragment() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(SORT_HEADER)
        val items = arrayOf(FIRST_OPTION, SECOND_OPTION)
        val checkedItem = 1
        alertDialog.setSingleChoiceItems(items, checkedItem) { _, which ->
            when (which) {
                0 -> sortByPrice()
                1 -> sortAlphabetically()
            }
        }
        alertDialog.setNegativeButton(CANCEL) { dialog, _ -> dialog.cancel() }
        alertDialog.setPositiveButton(OK) { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }

    private fun setAdapter() {
        binding.rvCurrency.adapter = coinAdapter
    }

    private fun sortAlphabetically() {
        lifecycleScope.launch {
            viewModel.getAlphabeticToAdapter().collect {
                coinAdapter.submitData(it)
            }
        }
    }

    private fun sortByPrice() {
        lifecycleScope.launch {
            viewModel.getPriceToAdapter().collect {
                coinAdapter.submitData(it)
            }
        }
    }

    companion object {
        private const val CANCEL = "Cancel"
        private const val OK = "Ok"
        private const val FIRST_OPTION = "By price"
        private const val SECOND_OPTION = "Alphabetically"
        private const val SORT_HEADER = "Sort"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvCurrency.adapter = null
    }




}
