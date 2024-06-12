package com.example.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.story.addstory.AddStoryFragment
import com.example.story.databinding.ActivityMainBinding
import com.example.story.home.HomeFragment
import com.example.story.intro.IntroFragment
import com.example.story.utils.Preferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var navBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = Preferences.initPref(this, "onSignIn")
        val token = sharedPref.getString("token", "")

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    Log.e("Ïtem", "home")
                    navController.navigate(R.id.homeFragment)
//                    swapFragment(HomeFragment())
                    true
                }
                R.id.logout -> {
                    Log.e("Ïtem", "logout")
                    Preferences.logout(this)
                    navController.navigate(R.id.introFragment)
                    true
                }
                R.id.add -> {
                    Log.e("Ïtem", "add")
                    navController.navigate(R.id.addStoryFragment)
//                    swapFragment(AddStoryFragment())
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
            Log.d("NavController", "Navigated to ${destination.label}")
        }

        if (savedInstanceState == null) {
            if (token != "") {
                navController.navigate(R.id.homeFragment)
            } else {
                navController.navigate(R.id.introFragment)
            }
        }


    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }
}