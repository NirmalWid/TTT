package com.example.qr_scanner

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login() : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btlog = findViewById<Button>(R.id.log)

        val etUname = findViewById<EditText>(R.id.editTextText)
        val tv = findViewById<TextView>(R.id.textView3)

        database = FirebaseDatabase.getInstance()

        tv.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        val animator = ObjectAnimator.ofFloat(btlog, "scaleY", 1f, 0.5f, 1f)
        animator.duration = 1000

        btlog.setOnClickListener {
            animator.start()

            val enteredUname = etUname.text.toString().trim()

            val idsRef = database.getReference("Unames")

            idsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val unames = snapshot.children.mapNotNull { it.getValue(String::class.java) }

                    if (unames.contains(enteredUname)) {
                        Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@Login, Roles::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Login, "Invalid ID", Toast.LENGTH_SHORT).show()
                    }


                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle any database error
                    Toast.makeText(this@Login, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        saveData()

    }

    private fun saveData() {

        val idsRef = database.getReference("Unames")
        // Add the desired IDs to the database
        val unames = listOf("nirmal", "kalana", "thisun") // Replace with your list of IDs

        for (name in unames) {
            idsRef.push().setValue(name)
        }

    }
}


