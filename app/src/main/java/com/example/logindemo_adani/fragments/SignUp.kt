package com.example.logindemo_adani.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.DBHelper
import com.example.logindemo_adani.HomeScreen
import com.example.logindemo_adani.R
import com.example.logindemo_adani.Utils
import com.example.logindemo_adani.model.User
import com.example.logindemo_adani.repositories.SignUpRepository
import com.example.logindemo_adani.viewmodel.SignUpViewModel
import com.example.logindemo_adani.viewmodel.SignUpViewModelFactory
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.*

/*
Username - iamraajkanchan
Password - r@jKanchan16
*/

class SignUp : Fragment()
{
    private lateinit var dbHelper : DBHelper
    private lateinit var signUpViewModel : SignUpViewModel
    override fun onAttach(context : Context)
    {
        super.onAttach(context)
        dbHelper = DBHelper(context)
        val signUpRepository = SignUpRepository(dbHelper)
        signUpViewModel = ViewModelProvider(this , SignUpViewModelFactory(signUpRepository)).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View?
    {
        return inflater.inflate(R.layout.fragment_sign_up , container , false)
    }

    override fun onStart()
    {
        super.onStart()
        var pin = ""
        val pinCodes = requireActivity().resources.getStringArray(R.array.pin_codes)
        val spinnerAdapter = ArrayAdapter.createFromResource(requireContext() , R.array.pin_codes , android.R.layout.simple_spinner_item)
        spinnerPinCode.adapter = spinnerAdapter
        spinnerPinCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent : AdapterView<*>? , view : View? , position : Int , id : Long)
            {
                pin = if (parent?.getItemAtPosition(position)?.equals(pinCodes[0]) !!)
                {
                    ""
                } else
                {
                    parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent : AdapterView<*>?)
            {
                pin = ""
            }
        }
        btnSignUp.setOnClickListener {
            try
            {
                val notification = signUpViewModel.insertUser(edtName , edtDOB , edtAddress , pin , edtUsername , edtPassword)
                if (notification.equals("User Registered Successfully!!!" , true))
                {
                    Toast.makeText(context , notification , Toast.LENGTH_LONG).show()
                    val bundle = Bundle().apply {
                        putString("username" , edtUsername.text.toString())
                    }
                    Intent(activity , HomeScreen::class.java).apply {
                        putExtra("userBundle" , bundle)
                        startActivity(this)
                    }
                } else
                {
                    Toast.makeText(context , notification , Toast.LENGTH_LONG).show()
                }
            } catch (e : Exception)
            {
                e.printStackTrace()
            }
        }
        tvRedirectLogin.setOnClickListener {
            val fragmentLogin = Login()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer , fragmentLogin , null)?.commit()
        }
        edtDOB.setOnClickListener {
            val calender = Calendar.getInstance()
            val year = calender.get(Calendar.YEAR)
            val month = calender.get(Calendar.MONTH)
            val day = calender.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext() , { _ , yearOfCalendar , monthOfYear , dayOfMonth ->
                edtDOB.setText("$dayOfMonth/${monthOfYear + 1}/$yearOfCalendar")
            } , year , month , day)
            datePickerDialog.show()
        }
    }
}