package com.playsdev.testapp.settings

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.playsdev.firsttest.databinding.SettingsFragmentBinding
import com.playsdev.testapp.photo.PhotoDialogFragment
import com.playsdev.testapp.photo.PhotoDialogFragment.Companion.BITMAP_IMG
import java.text.SimpleDateFormat
import java.util.*
import android.R
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.w3c.dom.Text


class SettingsFragment : Fragment() {

    private var binding: SettingsFragmentBinding? = null
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            val name = binding?.editTextName?.text?.trim()?.toString()!!
            val surname = binding?.editSurname?.text?.trim()?.toString()!!
            binding?.btnSave?.isEnabled = surname.isNotEmpty() && name.isNotEmpty()
        }
    }
    private val viewModel: PersonDataViewModel by inject()
    private var acceptedBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickDate()
        takePictureFromGallery()
        binding?.editTextName?.addTextChangedListener(textWatcher)
        binding?.editSurname?.addTextChangedListener(textWatcher)

        binding?.btnSave?.setOnClickListener {
            viewModel.addPersonToDatabase(
                binding?.editTextName?.text?.toString()!!,
                binding?.editSurname?.text?.toString()!!,
                binding?.editSurname?.text?.toString()!!,
                acceptedBitmap.toString()
            )
        }
    }


    private fun takePictureFromGallery() {
        arguments?.let {
            val image = it.getString(BITMAP_IMG)
            val bitmap = MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                Uri.parse(image)
            )
            acceptedBitmap = Bitmap.createScaledBitmap(bitmap, 360, 248, false)
            binding?.ivPhotoFrame?.setImageBitmap(acceptedBitmap)
        }
    }

    private fun pickDate() {
        val builder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
        val materialDatePicker = builder.build()

        binding?.ivPhoto?.setOnClickListener {
            val dialog = PhotoDialogFragment()
            dialog.show(childFragmentManager, PHOTO_TAG)
        }

        binding?.editTextDate?.setOnClickListener {
            materialDatePicker.show(childFragmentManager, OPEN_TAG)
            materialDatePicker.addOnPositiveButtonClickListener { selection ->
                binding?.editTextDate?.setText(convertDate(selection))
            }
        }

    }


    private fun convertDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return format.format(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val PHOTO_TAG = "PHOTO_DIALOG_TAG"
        private const val OPEN_TAG = "MAIN_OPEN_TAG"
    }
}