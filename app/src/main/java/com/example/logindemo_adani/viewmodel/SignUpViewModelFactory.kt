package com.example.logindemo_adani.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.repositories.SignUpRepository

class SignUpViewModelFactory(private val signUpRepository : SignUpRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass : Class<T>) : T
    {
        return SignUpViewModel(signUpRepository) as T
    }
}