package com.example.logindemo_adani.repositories

import com.example.logindemo_adani.DBHelper
import com.example.logindemo_adani.model.User

class SignUpRepository(private val dbHelper : DBHelper)
{
    fun insertUser(user : User) : Boolean
    {
        return dbHelper.insertData(user)
    }

    fun checkUserExist(userName : String) : Boolean
    {
        return dbHelper.checkUser(userName)
    }
}