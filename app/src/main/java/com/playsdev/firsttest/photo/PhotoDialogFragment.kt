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
            dispatchTakePictureIntent()
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File = createImageFile()
                photoFile.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.playsdev.testapp.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
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
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.data as Bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream)
                val byteArray = stream.toByteArray()
                val bundle = Bundle()
                bundle.putByteArray(BITMAP_IMG_SECOND,byteArray)
                settingsFragment.arguments = bundle
                settingsFragment.childFragmentManager.beginTransaction().add(R.id.settings_fragment,settingsFragment)
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
        const val BITMAP_IMG_SECOND = "BitmapImageSecond"
    }
}