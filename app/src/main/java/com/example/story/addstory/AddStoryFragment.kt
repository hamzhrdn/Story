package com.example.story.addstory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.story.MainActivity
import com.example.story.R
import com.example.story.databinding.FragmentAddStoryBinding
import com.example.story.register.RegisterFragmentDirections
import com.example.story.utils.Result
import com.example.story.utils.ViewModelFactory
import com.example.story.utils.getImageUri
import com.example.story.utils.reduceFileImage
import com.example.story.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryFragment : Fragment() {
    private var currentImageUri: Uri? = null

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!

    private val addStoryViewModel: AddStoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_top)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_bottom)

        binding.ivPreview.setImageResource(R.mipmap.base_image_foreground)

        binding.btAddImage.setOnClickListener { view ->
            showPopUpMenu(view)
        }

        binding.btSubmit.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            uploadData(description)
        }
        Log.e("AddStory", "Up")
    }

    private fun showPopUpMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.camera -> {
                    currentImageUri = getImageUri(requireContext())
                    launcherIntentCamera.launch(currentImageUri)
                    true
                }

                R.id.gallery -> {
                    val gallery = Intent.createChooser(intent, "Choose a image")
                    startGallery.launch(gallery)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val startGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                currentImageUri = result.data?.data as Uri
                if (currentImageUri != null) {
                    binding.ivPreview.setImageURI(currentImageUri)
                } else {
                    Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                binding.ivPreview.setImageURI(currentImageUri)
            } else {
                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uploadData(description: String) {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext())

            Log.d("Image File", "showImage: ${imageFile.path}")

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            addStoryViewModel.postStory(multipartBody, requestBody).observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(context, it.data.message, Toast.LENGTH_LONG).show()
                            showLoading(true)
                            val action = AddStoryFragmentDirections.actionAddStoryFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }

                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Error -> {
                            showLoading(true)
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun showLoading(con : Boolean){
        binding.progressbar.isVisible = con
    }
}