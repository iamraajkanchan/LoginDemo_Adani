package com.example.logindemo_adani.repositories

import com.example.logindemo_adani.DBHelper
import com.example.logindemo_adani.model.User

class UpdateProfileRepository(dbHelper : DBHelper)
{
    private val dbHelper : DBHelper = dbHelper
    fun updateUser(user : User)
    {
        dbHelper.updateUserDetail(user)
    }

    fun getUserDetail(userName : String) : User
    {
        return dbHelper.getUserDetail(userName)
    }
}