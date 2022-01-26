package com.playsdev.testapp.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.playsdev.firsttest.R
import com.playsdev.firsttest.databinding.PhotoDialogFragmentBinding
import com.playsdev.testapp.settings.SettingsFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotoDialogFragment : DialogFragment() {

    private var currentPhotoPath: String? = null
    private var binding: PhotoDialogFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotoDialogFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvPickFrom?.setOnClickListener {
            pickImageFromGallery()
        }

        binding?.tvTakePicture?.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun makePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }



    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val settingsFragment = SettingsFragment()

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_CODE) {
                val content = Uri.parse(data?.dataString)
                val imgFile = content.toString()
                val bundle = Bundle()
                bundle.putString(BITMAP_IMG, imgFile)
                findNavController().navigate(R.id.settings_fragment, bundle)
            }
            if (resultCode == Activity.RESULT_OK && requestCode == PhotoDialogFragment.REQUEST_IMAGE_CAPTURE) {
                val takenImage = data?.extras?.get("data") as Bitmap
                val stream = ByteArrayOutputStream()
                takenImage.compress(Bitmap.CompressFormat.PNG, BIT_QUALITY,stream)
                val byteArray = stream.toByteArray()
                val intent = Intent(requireContext(),SettingsFragment::class.java)
                intent.putExtra(BITMAP_IMG_SECOND,byteArray)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 0
        const val IMAGE_PICK_CODE = 1011
        const val BITMAP_IMG = "BitmapImage"
        const val BIT_QUALITY = 100
        const val BITMAP_IMG_SECOND = "BitmapImageSecond"
    }
}