package com.example.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.story.addstory.AddStoryFragment
import com.example.story.home.HomeFragment
import com.example.story.intro.IntroFragment
import com.example.story.utils.Preferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var navBar: BottomNavigationView

    private lateinit var homeFragment: HomeFragment
    private lateinit var introFragment: IntroFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = Preferences.initPref(this, "onSignIn")
        val token = sharedPref.getString("token", "")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.logout -> {
                    Preferences.logout(this)
                    navController.navigate(R.id.introFragment)
                    true
                }
                R.id.add ->{
                    navController.navigate(R.id.addStoryFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }


        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.introFragment, R.id.loginFragment2, R.id.registerFragment ->{
                    navBar.visibility = View.GONE
                }else->{
                    navBar.visibility = View.VISIBLE
                }
            }
        }

        if (savedInstanceState == null) {
            if (token != "") {
                navController.navigate(R.id.homeFragment)
                navBar.visibility = View.VISIBLE
            } else {
                navController.navigate(R.id.introFragment)
                navBar.visibility = View.GONE
            }
        }
    }
}