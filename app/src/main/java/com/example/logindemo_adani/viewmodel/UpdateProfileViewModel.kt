package com.example.logindemo_adani.viewmodel

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logindemo_adani.Utils
import com.example.logindemo_adani.model.User
import com.example.logindemo_adani.repositories.UpdateProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val updateProfileRepository : UpdateProfileRepository) : ViewModel()
{

    fun updateUser(name : EditText , dob : EditText , address : EditText , pinCode : String , user : User) : Boolean
    {
        return if (Utils.validateName(name) && Utils.validateDOB(dob) && Utils.validateAddress(address) && Utils.validatePin(pinCode))
        {
            viewModelScope.launch(Dispatchers.IO) {
                val updatingUser = User(name.text.toString() , dob.text.toString() , address.text.toString() , pinCode , user.username , user.password , user.isLoggedIn)
                updateProfileRepository.updateUser(updatingUser)
            }
            true
        } else
        {
            false
        }
    }

    fun getUserDetails(userName : String) : User
    {
        return updateProfileRepository.getUserDetail(userName)
    }

}