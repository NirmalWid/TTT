package com.example.qr_scanner.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.qr_scanner.databinding.ActivitySignUpBinding
import com.example.qr_scanner.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.log.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {

        val fname = binding.textView13.text.toString()
        val lname = binding.textView14.text.toString()
        val email = binding.editTextTextEmailAddress.text.toString()
        val id = binding.textView20.text.toString()
        val uname = binding.etuser.text.toString()
        val pass = binding.editTextTextPassword2.text.toString()
        val confirmPass = binding.editTextTextPassword3.text.toString()

        print("Data from user$fname$lname$email$id$uname$pass$confirmPass")

        if (fname.isNotEmpty() && lname.isNotEmpty() && email.isNotEmpty() && id.isNotEmpty() && uname.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {

            if (pass == confirmPass) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Sign In" , Toast.LENGTH_SHORT).show()
                        storageData()

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun storageData() {
        val data = UserModel(
            userid = firebaseAuth.currentUser!!.uid,
            Fname = binding.textView13.text.toString(),
            Lname = binding.textView14.text.toString(),
            Email = binding.editTextTextEmailAddress.text.toString(),
            Id = binding.textView20.text.toString(),
            Uname = binding.etuser.text.toString(),
            Pass = binding.editTextTextPassword2.text.toString(),
            Cpass = binding.editTextTextPassword3.text.toString(),
        )

        val uId = firebaseAuth.currentUser!!.uid
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val userData = mapOf(
            "userId" to data.userid,
            "Fname" to data.Fname,
            "Lname" to data.Lname,
            "Email" to data.Email,
            "Id" to data.Id,
            "Uname" to data.Uname,
            "Pass" to data.Pass,
            "Cpass" to data.Cpass,
        )

        database.child("Users").child(uId).setValue(userData)
            .addOnSuccessListener {
                startActivity(Intent(this, Roles::class.java))
                finish()
                Toast.makeText(this, "User Sign In Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

}