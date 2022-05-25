package com.example.logindemo_adani.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.repositories.UpdateProfileRepository

class UpdateProfileViewModelFactory(private val updateProfileRepository : UpdateProfileRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass : Class<T>) : T
    {
        return UpdateProfileViewModel(updateProfileRepository) as T
    }
}