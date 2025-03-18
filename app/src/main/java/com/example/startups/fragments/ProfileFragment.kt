package com.example.startups.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.startups.activities.ChangePasswordActivity
import com.example.startups.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val changePassword = view.findViewById<Button>(R.id.changePasswordEnter)
        val editButton = view.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            when (editButton.text){
                "Edit" -> {
                    view.findViewById<EditText>(R.id.fullName).isEnabled = true
                    view.findViewById<EditText>(R.id.email).isEnabled = true
                    view.findViewById<EditText>(R.id.phoneNumber).isEnabled = true
                    view.findViewById<EditText>(R.id.birthday).isEnabled = true
                    view.findViewById<EditText>(R.id.job).isEnabled = true
                    editButton.text = "Save"
                }
                "Save" -> {
                    view.findViewById<EditText>(R.id.fullName).isEnabled = false
                    view.findViewById<EditText>(R.id.email).isEnabled = false
                    view.findViewById<EditText>(R.id.phoneNumber).isEnabled = false
                    view.findViewById<EditText>(R.id.birthday).isEnabled = false
                    view.findViewById<EditText>(R.id.job).isEnabled = false
                    editButton.text = "Edit"
                }
            }
        }
        changePassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}