package com.example.story.register

import androidx.lifecycle.ViewModel
import com.example.story.database.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.postRegister(name, email, password)
}