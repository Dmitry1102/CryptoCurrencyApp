package com.playsdev.testapp.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.playsdev.firsttest.databinding.SettingsFragmentBinding
import com.playsdev.firsttest.persondatabase.Person
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
            binding?.btnSave?.isEnabled = inputCheck(name, surname)
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
        pickDate()
        binding?.editTextName?.addTextChangedListener(textWatcher)
        binding?.editSurname?.addTextChangedListener(textWatcher)
        binding?.editTextDate?.addTextChangedListener(textWatcher)

        binding?.btnSave?.setOnClickListener {
            addToPersonDataBase()
        }

       // setFromDataBase()

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
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
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
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
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


    }

    private fun addToPersonDataBase() {
        val name = binding?.editTextName?.text.toString()
        val surname = binding?.editSurname?.text.toString()
        val date = binding?.editTextDate?.text.toString()
        val person = Person(name = name, surname = surname, date = date, image = bitmap!!)
        Log.e("AAA", "$person")

        try {
            if (inputCheck(name, surname)) {
                viewModel.addPerson(person)
                Toast.makeText(context, PERSON_ADD, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "exception found $e", Toast.LENGTH_LONG).show()
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

    private fun setFromDataBase() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch{
            viewModel.setPerson.collect {
                personCheck = it
                Log.d("FFF", "$it")
                fillFields(personCheck!!)
                binding?.editTextName?.isEnabled = false
                binding?.editTextDate?.isEnabled = false
                binding?.editSurname?.isEnabled = false
                binding?.btnSave?.isEnabled = false
                binding?.ivPhoto?.isEnabled = false
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
        binding?.editTextName?.setText(person.name)
        binding?.editSurname?.setText(person.surname)
        binding?.editTextDate?.setText(person.date)
        binding?.ivPhotoFrame?.setImageBitmap(person.image)
    }

    private fun inputCheck(name: String, surname: String): Boolean {
        return surname.isNotEmpty() && name.isNotEmpty()
    }

    companion object {
        private const val PERSON_ADD = "Person successfully added!"
        private const val DIALOG_HEADER = "Download photo"
        private const val FIRST_OPTION = "Take a picture"
        private const val SECOND_OPTION = "Pick from gallery"
        private const val OPEN_TAG = "MAIN_OPEN_TAG"
    }
}