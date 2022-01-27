package com.playsdev.testapp.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.playsdev.firsttest.databinding.SettingsFragmentBinding
import com.playsdev.firsttest.persondatabase.PersonEntity
import com.playsdev.firsttest.repository.Person
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class SettingsFragment : Fragment() {

    private var binding: SettingsFragmentBinding? = null
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val name = binding?.editTextName?.text?.trim()?.toString()!!
            val surname = binding?.editSurname?.text?.trim()?.toString()!!
            binding?.btnSave?.isEnabled = surname.isNotEmpty() && name.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
    private val viewModel by inject<PersonDataViewModel>()
    private var photoSelectResultLauncher: ActivityResultLauncher<Intent>? = null
    private var takePhotoResultLauncher: ActivityResultLauncher<Intent>? = null
    private var photoFile: File? = null
    private var bitmap: Bitmap? = null
    private var personCheck: Person? = null

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

        viewModel.setPerson.onEach {
            personCheck = it
            fillFields(personCheck!!)
            binding?.btnSave?.isEnabled = false
        }.launchIn(lifecycleScope)

        pickDate()
        binding?.editTextName?.addTextChangedListener(textWatcher)
        binding?.editSurname?.addTextChangedListener(textWatcher)

        val items = arrayOf(FIRST_OPTION, SECOND_OPTION)
        binding?.ivPhoto?.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(DIALOG_HEADER)
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> takePhoto()
                        1 -> pickImageFromGallery()
                    }
                }.show()
        }

        photoSelectResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val photoUri = it.data?.data
                    bitmap = Bitmap.createScaledBitmap(
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, photoUri),
                        360,
                        248,
                        false
                    )
                    binding?.ivPhotoFrame?.setImageBitmap(bitmap)
                }
            }
        takePhotoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val uri = photoFile?.toUri()
                    bitmap = Bitmap.createScaledBitmap(
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, uri),
                        360,
                        248,
                        false
                    )
                    binding?.ivPhotoFrame?.setImageBitmap(bitmap)
                }
            }

        binding?.btnSave?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val person = PersonEntity(
                    binding?.editTextName?.text.toString(),
                    binding?.editSurname?.text.toString(),
                    binding?.editTextDate?.text.toString(),
                    bitmap!!
                )
                viewModel.addPersonToDatabase(person)
            }
        }
    }


    private fun pickDate() {
        val builder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
        val materialDatePicker = builder.build()

        binding?.editTextDate?.setOnClickListener {
            materialDatePicker.show(childFragmentManager, OPEN_TAG)
            materialDatePicker.addOnPositiveButtonClickListener { selection ->
                binding?.editTextDate?.setText(convertDate(selection))
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent()
        with(intent) {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        photoSelectResultLauncher?.launch(intent)
    }


    private fun takePhoto() {
        val storage = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        photoFile = File.createTempFile("photo", ".jpeg", storage)
        val fileProvider = FileProvider.getUriForFile(
            requireContext(),
            "com.playsdev.firsttest.fileprovider",
            photoFile!!
        )
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        takePhotoResultLauncher?.launch(intent)
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

    private fun fillFields(person: Person) {
        binding?.ivPhotoFrame?.setImageBitmap(person.image)
        binding?.editTextName?.setText(person.name)
        binding?.editSurname?.setText(person.surname)
        binding?.editTextDate?.setText(person.date)
    }

    companion object {
        private const val DIALOG_HEADER = "Download photo"
        private const val FIRST_OPTION = "Take a picture"
        private const val SECOND_OPTION = "Pick from gallery"
        private const val OPEN_TAG = "MAIN_OPEN_TAG"
    }
}