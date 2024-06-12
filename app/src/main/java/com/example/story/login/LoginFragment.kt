package com.example.story.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.story.MainActivity
import com.example.story.R
import com.example.story.databinding.FragmentLoginBinding
import com.example.story.home.HomeFragment
import com.example.story.utils.Preferences
import com.example.story.utils.Result
import com.example.story.utils.ViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            viewModel.login(email, password).observe(requireActivity()){result->
                if(result!=null){
                    when(result){
                        is Result.Loading->{
                            showLoading(true)
                        }
                        is Result.Success -> {
                            if(result.data.error == true){
                                Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_LONG).show()
                            }else{
                                Preferences.saveToken(result.data.loginResult?.token!!, requireContext())

                                val intent = Intent(requireActivity(), MainActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                            showLoading(false)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(con : Boolean){
        binding.progressbar.isVisible = con
    }
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navBar.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}