package com.example.logindemo_adani.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.repositories.UpdateProfileRepository
import java.lang.IllegalArgumentException

class UpdateProfileViewModelFactory(private val updateProfileRepository : UpdateProfileRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass : Class<T>) : T
    {
        if (modelClass.isAssignableFrom(UpdateProfileViewModel::class.java))
        {
            return UpdateProfileViewModel(updateProfileRepository) as T
        }
        throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
    }
}