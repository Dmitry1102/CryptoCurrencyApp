package com.playsdev.testapp.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.databinding.MainFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {


    private var binding: MainFragmentBinding? = null
    private val viewModel by inject<CoinViewModel>()
    private val coinAdapter = CoinAdapter()
    private val coinViewModel by inject<CoinDataBaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivSort?.setOnClickListener { makeDialogFragment() }
        setAdapter()
        lifecycleScope.launch {
            coinViewModel.getCoinList().collect {
                coinAdapter.submitData(it)
            }
        }


        binding?.swipeLayout?.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getCoinToAdapter().distinctUntilChanged().collectLatest {
                    coinAdapter.submitData(it)
                }
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun makeDialogFragment() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(SORT_HEADER)
        val items = arrayOf(FIRST_OPTION, SECOND_OPTION)
        val checkedItem = 1
        alertDialog.setSingleChoiceItems(items, checkedItem) { _, which ->
            when (which) {
                //0 -> sortByPrice(listCoins!!)
                //1 -> sortAlphabetically(listCoins!!)
            }
        }
        alertDialog.setNegativeButton(CANCEL) { dialog, _ -> dialog.cancel() }
        alertDialog.setPositiveButton(OK) { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }



    private fun setAdapter(){
        binding?.rvCurrency?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rvCurrency?.adapter = coinAdapter
    }

    private fun sortAlphabetically(list: PagingData<Coin>) {
        coinAdapter.submitData()
    //coinAdapter.submitData(list.sortedBy { it.id })
    }

    private fun sortByPrice(list: MutableList<Coin>) {
      //  coinAdapter.submitList(list.sortedBy { it.current_price })
    }

    companion object {
        private const val CANCEL = "Cancel"
        private const val OK = "Ok"
        private const val FIRST_OPTION = "By price"
        private const val SECOND_OPTION = "Alphabetically"
        private const val SORT_HEADER = "Sort"
    }
}