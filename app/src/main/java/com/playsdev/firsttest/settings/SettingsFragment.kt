package com.playsdev.testapp.settings

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.playsdev.firsttest.databinding.SettingsFragmentBinding
import com.playsdev.testapp.photo.PhotoDialogFragment
import com.playsdev.testapp.photo.PhotoDialogFragment.Companion.BITMAP_IMG
import com.playsdev.testapp.photo.PhotoDialogFragment.Companion.BITMAP_IMG_SECOND

class SettingsFragment : Fragment() {

    private var binding: SettingsFragmentBinding? = null

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

        binding?.ivPhoto?.setOnClickListener {
            val dialog = PhotoDialogFragment()
            dialog.show(childFragmentManager, PHOTO_TAG)
        }




        arguments?.let {
                val image = it.getString(BITMAP_IMG)
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    Uri.parse(image)
                )
                val acceptedBitmap = Bitmap.createScaledBitmap(bitmap, 360, 248, false)
               binding?.ivPhotoFrame?.setImageBitmap(acceptedBitmap)

            if (it.containsKey(BITMAP_IMG_SECOND)) {

        }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val PHOTO_TAG = "PHOTO_DIALOG_TAG"
    }
}