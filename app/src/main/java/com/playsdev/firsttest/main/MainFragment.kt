package com.playsdev.testapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.playsdev.testapp.sort.SortDialogFragment
import com.playsdev.testapp.databinding.MainFragmentBinding

class MainFragment: Fragment() {

     private var binding: MainFragment? = null

     override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
     ): View? {
          binding = MainFragmentBinding.inflate(inflater,container,false)
          return binding?.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)

          binding?.ivSort?.setOnClickListener{
               val dialog = SortDialogFragment()
               dialog.show(childFragmentManager, TAG)
          }

     }

     override fun onDestroy() {
          super.onDestroy()
          binding = null
     }

     companion object{
          private const val TAG = "SORT_FRAGMENT"
     }

}