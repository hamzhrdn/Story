package com.example.story.login

import androidx.lifecycle.ViewModel
import com.example.story.database.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.postLogin(email, password)
}