package com.example.story.intro

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.story.R
import com.example.story.databinding.FragmentIntroBinding
import com.example.story.utils.Preferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class IntroFragment : Fragment() {

    private var _binding : FragmentIntroBinding? = null
    private lateinit var navigationView : BottomNavigationView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = Preferences.initPref(requireContext(),"Login")
        val token = sharedPreferences.getString("token", "")

        binding.registerButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_introFragment_to_registerFragment)
        )
        binding.loginButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_introFragment_to_loginFragment2)
        }

        if(token!=""){
            view.findNavController().navigate(R.id.action_introFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}