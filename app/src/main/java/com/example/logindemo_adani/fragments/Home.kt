package com.example.logindemo_adani.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logindemo_adani.R
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment()
{
    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View?
    {
        return inflater.inflate(R.layout.fragment_home , container , false)
    }

    override fun onStart()
    {
        super.onStart()
        btnUpdateProfile.setOnClickListener {
            val fragmentUpdateProfile = UpdateProfile()
            fragmentUpdateProfile.arguments = this.arguments
            activity?.supportFragmentManager?.beginTransaction()?.hide(this)?.add(R.id.fragmentContainerHome , fragmentUpdateProfile , null)?.addToBackStack("UpdateProfile")?.commit()
        }
    }
}