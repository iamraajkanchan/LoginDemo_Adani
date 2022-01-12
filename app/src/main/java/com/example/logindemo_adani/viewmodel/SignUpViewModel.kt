package com.example.logindemo_adani.viewmodel

import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.logindemo_adani.Utils
import com.example.logindemo_adani.model.User
import com.example.logindemo_adani.repositories.SignUpRepository

class SignUpViewModel(private val signUpRepository : SignUpRepository) : ViewModel()
{
    fun insertUser(name : EditText , dob : EditText , address : EditText , pinCode : String , userName : EditText , password : EditText) : String
    {
        if (Utils.validateName(name) && Utils.validateDOB(dob) && Utils.validateAddress(address) && Utils.validatePin(pinCode) && Utils.validateUsername(userName) && Utils.validatePassword(
                password
                                                                                                                                                                                            )
        )
        {
            return if (! checkUserExist(userName.text.toString()))
            {
                val newUser = User(name.text.toString() , dob.text.toString() , address.text.toString() , pinCode , userName.text.toString() , password.text.toString() , 1)
                if (signUpRepository.insertUser(newUser))
                {
                    "User Registered Successfully!!!"
                } else
                {
                    "Please try again!!!"
                }
            } else
            {
                "User already exist!!!"
            }
        } else
        {
            return "Please fill the proper details"
        }
    }

    private fun checkUserExist(userName : String) : Boolean
    {
        return signUpRepository.checkUserExist(userName)
    }
}