package com.example.qr_scanner

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.qr_scanner.model.CheckerModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Checker : AppCompatActivity() {

    private lateinit var tvDate : TextView
    private lateinit var btDate : Button

    private lateinit var etId: EditText
    private lateinit var btnLogin: Button

    private lateinit var etStat: EditText
    private lateinit var etName: EditText

    private lateinit var database: FirebaseDatabase

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checker)

        etId = findViewById(R.id.etextID)  //ID text view
        btnLogin = findViewById(R.id.log)

        tvDate = findViewById(R.id.tvDatepic)  //date text view
        btDate = findViewById(R.id.btDatepic)

        etStat = findViewById(R.id.editTextText3)  //station text view
        etName = findViewById(R.id.editTextText2)  //name text view

        database = FirebaseDatabase.getInstance()

        dbRef = FirebaseDatabase.getInstance().getReference("Checkers")

        val myCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLable(myCalender)
        }

        btDate.setOnClickListener{
            DatePickerDialog(this,datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnLogin.setOnClickListener {

            val enteredId = etId.text.toString().trim()

            // Retrieve the reference to the "IDs" node in the Firebase Realtime Database
            val idsRef = database.getReference("IDs")

            // Add a ValueEventListener to fetch the IDs from the database
            idsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ids = snapshot.children.mapNotNull { it.getValue(String::class.java) }

                    if (ids.contains(enteredId)) {
                        // ID found, login successful
                        Toast.makeText(this@Checker, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@Checker, Scanner::class.java)
                        startActivity(intent)
                    } else {
                        // ID not found, login failed
                        Toast.makeText(this@Checker, "Invalid ID", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any database error
                    Toast.makeText(this@Checker, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

            saveChecker()
        }

        saveIds() // Call the method to save IDs

    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDate.setText(sdf.format(myCalender.time))

    }

    private fun saveIds() {
        // Retrieve the reference to the "IDs" node in the Firebase Realtime Database
        val idsRef = database.getReference("IDs")

        // Add the desired IDs to the database
        val ids = listOf("id1", "id2", "id3") // Replace with your list of IDs
        for (id in ids) {
            idsRef.push().setValue(id)
        }
    }

    private fun saveChecker() {

        val station = etStat.text.toString()
        val id = etId.text.toString()
        val name = etName.text.toString()
        val date = tvDate.text.toString()

        if (name.isEmpty() && id.isEmpty() && name.isEmpty() && date.isEmpty()){
            etStat.error = "Please fill all the Rows"
        }

        if (name.isNotEmpty() && id.isNotEmpty() && name.isNotEmpty() && date.isNotEmpty()){
            val checkerId = dbRef.push().key!!

            val checker = CheckerModel(checkerId,station,id,name,date)

            dbRef.child(checkerId).setValue(checker)
                .addOnCompleteListener {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()

                    etStat.text.clear()
                    etId.text.clear()
                    etName.text.clear()
                    tvDate.text = ""

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }        }

    }

}