package com.playsdev.testapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.playsdev.firsttest.databinding.MainFragmentBinding
import com.playsdev.testapp.sort.SortDialogFragment

class MainFragment: Fragment() {

     private var binding: MainFragmentBinding? = null

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