package com.playsdev.testapp.settings

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.playsdev.firsttest.BaseFragment
import com.playsdev.firsttest.R
import com.playsdev.firsttest.databinding.SettingsFragmentBinding
import com.playsdev.firsttest.persondatabase.Person
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val name = binding.editTextName.text?.trim()?.toString()!!
            val surname = binding.editSurname.text?.trim()?.toString()!!
            binding.btnSave.isEnabled = inputCheck(name, surname)
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
    private val viewModel: PersonDataViewModel by viewModel()
    private var photoSelectResultLauncher: ActivityResultLauncher<Intent>? = null
    private var takePhotoResultLauncher: ActivityResultLauncher<Intent>? = null
    private var photoFile: File? = null
    private var bitmap: Bitmap? = null
    private var personCheck: Person? = null

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): SettingsFragmentBinding = SettingsFragmentBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickDate()
        binding.editTextName.addTextChangedListener(textWatcher)
        binding.editSurname.addTextChangedListener(textWatcher)
        binding.editTextDate.addTextChangedListener(textWatcher)

        binding.btnSave.setOnClickListener {
            addToDataBase()

        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setPerson.collectLatest {
                    personCheck = it
                    if (personCheck != null) {
                        fillFields(personCheck!!)
                        binding.editTextName.isEnabled = false
                        binding.editTextDate.isEnabled = false
                        binding.editSurname.isEnabled = false
                        binding.btnSave.isEnabled = false
                        binding.ivPhoto.isEnabled = false
                    }
                }
            }
        }

            val items = arrayOf(FIRST_OPTION, SECOND_OPTION)
            binding.ivPhoto.setOnClickListener {
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
                        binding.ivPhotoFrame.setImageBitmap(bitmap)
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
                        binding.ivPhotoFrame.setImageBitmap(bitmap)
                    }
                }
        }

        private fun addToDataBase() {
            val name = binding.editTextName.text.toString()
            val surname = binding.editSurname.text.toString()
            val date = binding.editTextDate.text.toString()
            val person = Person(name = name, surname = surname, date = date, image = bitmap!!)

            try {
                if (inputCheck(name, surname)) {
                    viewModel.addPerson(person)
                    Snackbar.make(binding.settingsFragment, PERSON_ADD, Snackbar.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Snackbar.make(binding.settingsFragment, "exception found $e", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


        private fun pickDate() {
            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
            val materialDatePicker = builder.build()
            binding.editTextDate.setOnClickListener {
                materialDatePicker.show(childFragmentManager, OPEN_TAG)
                materialDatePicker.addOnPositiveButtonClickListener { selection ->
                    binding.editTextDate.setText(convertDate(selection))
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

        private fun fillFields(person: Person) {
            binding.editTextName.setText(person.name)
            binding.editSurname.setText(person.surname)
            binding.editTextDate.setText(person.date)
            binding.ivPhotoFrame.setImageBitmap(person.image)
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