package com.example.qr_scanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner.model.AdminModel
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: EditText
    private lateinit var id: EditText
    private lateinit var uname: EditText
    private lateinit var pass: EditText
    private lateinit var confirmPass: EditText
    private lateinit var btsignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        fname = findViewById(R.id.textView13)
        lname = findViewById(R.id.textView14)
        email = findViewById(R.id.editTextTextEmailAddress)
        id = findViewById(R.id.textView20)
        uname = findViewById(R.id.etuser)
        pass = findViewById(R.id.editTextTextPassword2)
        confirmPass = findViewById(R.id.editTextTextPassword3)
        btsignup = findViewById(R.id.log)

        val dbRef = FirebaseDatabase.getInstance().getReference("Admins")

        btsignup.setOnClickListener(){

            val Fname = fname.text.toString()
            val Lname = lname.text.toString()
            val Email = email.text.toString()
            val Id = id.text.toString()
            val Uname = uname.text.toString()
            val Pass = pass.text.toString()
            val Cpass = confirmPass.text.toString()

            if (Fname.isEmpty() && Lname.isEmpty() && Email.isEmpty() && Id.isEmpty() && Uname.isEmpty() && Pass.isEmpty() && Cpass.isEmpty()){
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val adminId = dbRef.push().key

            val admin = AdminModel(adminId!!, Fname, Lname, Email, Id, Uname, Pass, Cpass)

            dbRef.child(adminId).setValue(admin)
                .addOnSuccessListener {

                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    fname.text.clear()
                    lname.text.clear()
                    email.text.clear()
                    id.text.clear()
                    uname.text.clear()
                    pass.text.clear()
                    confirmPass.text.clear()

                    val intent = Intent(this, Roles::class.java)
                    startActivity(intent)


                }.addOnFailureListener {
                    // Show an error message to the user
                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_LONG).show()
                }

        }
    }

}