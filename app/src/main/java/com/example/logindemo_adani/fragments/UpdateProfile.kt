package com.example.logindemo_adani.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.logindemo_adani.DBHelper
import com.example.logindemo_adani.R
import com.example.logindemo_adani.model.User
import com.example.logindemo_adani.repositories.UpdateProfileRepository
import com.example.logindemo_adani.viewmodel.UpdateProfileViewModel
import com.example.logindemo_adani.viewmodel.UpdateProfileViewModelFactory
import kotlinx.android.synthetic.main.fragment_update_profile.*
import java.util.*

class UpdateProfile : Fragment()
{
    private lateinit var username : String
    private lateinit var dbHelper : DBHelper
    private lateinit var user : User
    private lateinit var updateProfileViewModel : UpdateProfileViewModel

    override fun onAttach(context : Context)
    {
        super.onAttach(context)
        dbHelper = DBHelper(context)
        val repository = UpdateProfileRepository(dbHelper)
        updateProfileViewModel = ViewModelProvider(this , UpdateProfileViewModelFactory(repository)).get(UpdateProfileViewModel::class.java)
    }

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View?
    {
        username = arguments?.getString("username").toString()
        user = updateProfileViewModel.getUserDetails(username)
        return inflater.inflate(R.layout.fragment_update_profile , container , false)
    }

    override fun onStart()
    {
        super.onStart()
        activity?.runOnUiThread {
            edtName.setText(user.name)
            edtDOB.setText(user.dob)
            edtAddress.setText(user.address)
            edtPinCode.setText(user.pin)
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
        btnUpdate.setOnClickListener {
            val isUserUpdated = updateProfileViewModel.updateUser(edtName , edtDOB , edtAddress , edtPinCode.text.toString() , user)
            if (isUserUpdated)
            {
                Toast.makeText(context , "Updated..." , Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack(
                    "UpdateProfile" , FragmentManager.POP_BACK_STACK_INCLUSIVE
                                                  )
            } else
            {
                Toast.makeText(context , "Please try again!!!" , Toast.LENGTH_LONG).show()
            }
        }
    }

}