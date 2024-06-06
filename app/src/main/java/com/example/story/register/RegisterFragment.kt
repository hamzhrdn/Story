package com.example.story.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.story.R
import com.example.story.databinding.FragmentLoginBinding
import com.example.story.databinding.FragmentRegisterBinding
import com.example.story.login.LoginViewModel
import com.example.story.utils.Result
import com.example.story.utils.ViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener{
            val name = binding.name.toString()
            val email = binding.email.toString()
            val password = binding.password.toString()

            viewModel.register(name, email, password).observe(requireActivity()){result->
                if(result!=null){
                    when(result){
                        is Result.Loading->{
                            showLoading(true)
                        }
                        is Result.Success->{
                            Toast.makeText(requireContext(),result.data.message, Toast.LENGTH_LONG).show()
                            showLoading(false)
                        }
                        is Result.Error->{
                            Toast.makeText(requireContext(),result.error, Toast.LENGTH_LONG).show()
                            showLoading(false)
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