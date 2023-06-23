package com.example.qr_scanner.utils

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner.R
import com.example.qr_scanner.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val btlog = findViewById<Button>(R.id.log)


        binding.textView3.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        val animator = ObjectAnimator.ofFloat(btlog, "scaleY", 1f, 0.5f, 1f)
        animator.duration = 1000

        binding.log.setOnClickListener {
            animator.start()

            val name = binding.editTextText.text.toString()
            val pass = binding.editTextTextPassword.text.toString()

            if (name.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, Roles::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }

        }

}

