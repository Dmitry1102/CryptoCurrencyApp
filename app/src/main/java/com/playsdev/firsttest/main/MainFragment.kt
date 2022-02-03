package com.playsdev.testapp.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.databinding.MainFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    private val viewModel by inject<CoinViewModel>()
    private var listCoins: MutableList<CoinResponce>? = null
    private val coinAdapter = CoinAdapter()



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


        binding?.ivSort?.setOnClickListener {
            makeDialogFragment()
        }


        binding?.rvCurrency?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rvCurrency?.adapter = coinAdapter

       binding?.swipeLayout?.setOnRefreshListener {
           viewModel.getInfo()
           viewLifecycleOwner.lifecycle.coroutineScope.launchWhenResumed {
               viewModel.stateFlow.collect {
                   listCoins = it.toMutableList()
                   coinAdapter.submitList(listCoins)

               }
           }
       }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun makeDialogFragment(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(SORT_HEADER)
        val items = arrayOf(FIRST_OPTION, SECOND_OPTION)
        val checkedItem = 1
        alertDialog.setSingleChoiceItems(items,checkedItem) { _, which ->
            when (which) {
                0 -> sortByPrice(listCoins!!)
                1 -> sortAlphabetically(listCoins!!)
            }
        }
        alertDialog.setNegativeButton(CANCEL) { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.setNegativeButton(OK) { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()


    }

    private fun sortAlphabetically(list: MutableList<CoinResponce>) {
        coinAdapter.submitList(list.sortedBy { it.id })
    }

    private fun sortByPrice(list: MutableList<CoinResponce>) {
        coinAdapter.submitList(list.sortedBy { it.current_price })
    }

    companion object {
        private const val CANCEL = "Cancel"
        private const val OK = "Ok"
        private const val FIRST_OPTION = "By price"
        private const val SECOND_OPTION = "Alphabetically"
        private const val TAG = "SORT_FRAGMENT"
        private const val SORT_HEADER = "SORT"
    }


}