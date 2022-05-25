package com.example.logindemo_adani

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logindemo_adani.databinding.ActivityMainBinding
import com.example.logindemo_adani.fragments.SignUp

class MainActivity : AppCompatActivity()
{
    private lateinit var fragmentSignUp : SignUp
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
        {
            fragmentSignUp = SignUp()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer , fragmentSignUp , null).commit()
        }
    }

    override fun onBackPressed()
    {
        super.onBackPressed()
        this.finish()
        startActivity(Utils.goBackToHome(this))
    }
}