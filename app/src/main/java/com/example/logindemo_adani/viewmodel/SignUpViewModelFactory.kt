package com.example.logindemo_adani.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.repositories.SignUpRepository
import java.lang.IllegalArgumentException

class SignUpViewModelFactory(private val signUpRepository : SignUpRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass : Class<T>) : T
    {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java))
        {
            return SignUpViewModel(signUpRepository) as T
        }
        throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
    }
}