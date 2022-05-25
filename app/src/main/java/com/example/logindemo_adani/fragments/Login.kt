package com.example.logindemo_adani.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.logindemo_adani.*
import kotlinx.android.synthetic.main.fragment_login.*

/* Password - r@jKanchan16 */

class Login : Fragment()
{
    private lateinit var dbHelper : DBHelper

    override fun onAttach(context : Context)
    {
        super.onAttach(context)
        dbHelper = DBHelper(context)
    }

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View?
    {
        return inflater.inflate(R.layout.fragment_login , container , false)
    }

    override fun onResume()
    {
        super.onResume()
        btnLogin.setOnClickListener {
            if (Utils.validateUsername(edtUsername) && Utils.validatePassword(edtPassword))
            {
                if (dbHelper.checkUsernameAndPassword(edtUsername.text.toString() , edtPassword.text.toString()))
                {
                    val bundle = Bundle().apply {
                        putString("username" , edtUsername.text.toString())
                    }
                    Intent(activity , HomeScreen::class.java).apply {
                        putExtra("userBundle" , bundle)
                        startActivity(this)
                    }
                } else
                {
                    Toast.makeText(requireActivity() , "Incorrect Credentials!!!" , Toast.LENGTH_LONG).show()
                }
            }
        }
        tvRedirectSignUp.setOnClickListener {
            val fragmentSignUp = SignUp()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer , fragmentSignUp , null)?.commit()
        }
    }
}