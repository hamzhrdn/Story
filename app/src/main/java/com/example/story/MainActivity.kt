package com.example.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        navBar = findViewById(R.id.bottom_navigation)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.introFragment, R.id.loginFragment2, R.id.registerFragment ->{
                    navBar.visibility = View.GONE
                }else->{
                    navBar.visibility = View.VISIBLE
                }
            }

        }
    }
}