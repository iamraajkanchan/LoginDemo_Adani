package com.example.logindemo_adani

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.logindemo_adani.fragments.Home

class HomeScreen : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        if (savedInstanceState == null)
        {
            val fragmentHome = Home()
            fragmentHome.arguments = intent.getBundleExtra("userBundle")
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerHome , fragmentHome , null).commit()
        }
    }

    override fun onBackPressed()
    {
        super.onBackPressed()
        this.finish()
        startActivity(Utils.goBackToHome(this))
    }
}