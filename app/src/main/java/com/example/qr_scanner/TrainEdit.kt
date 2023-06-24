package com.example.qr_scanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TrainEdit : AppCompatActivity() {

    private lateinit var trainId: String
    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etType: Spinner
    private lateinit var etLine: Spinner
    private lateinit var etStart: EditText
    private lateinit var etDest: EditText

    private var recordId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_edit)

        trainId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        etName = findViewById(R.id.editTextText4)
        etNumber = findViewById(R.id.editTextText5)
        etType = findViewById(R.id.spinner)
        etLine = findViewById(R.id.spinner2)
        etStart = findViewById(R.id.editTextText8)
        etDest = findViewById(R.id.editTextText9)

        var btedit = findViewById<Button>(R.id.button5)

        var btDelete = findViewById<Button>(R.id.delte)

        val database = FirebaseDatabase.getInstance().reference

        database.child("Trains").orderByKey().limitToLast(1).get()
            .addOnSuccessListener { dataSnapshot ->
                // Store the record ID as a class-level variable
                val lastChild = dataSnapshot.children.last()
                // Store the record ID as a class-level variable
                recordId =
                    lastChild.key                        // Set the values of the EditText fields
                // Get the values of the child nodes and convert them to strings
                val Name = lastChild.child("name").value?.toString()
                val Number = lastChild.child("number").value?.toString()

                val Start = lastChild.child("start").value?.toString()
                val Dest = lastChild.child("dest").value?.toString()


                // Set the values of the TextViews
                etName.setText(Name)
                etNumber.setText(Number)

                etStart.setText(Start)
                etDest.setText(Dest)

                // Set up the submit button onClick listener
                btedit.setOnClickListener {

                    val nam = etName.text.toString()
                    val num = etNumber.text.toString()

                    val stat = etStart.text.toString()
                    val dect = etDest.text.toString()


                    // Update the income record with the new values
                    lastChild.ref.updateChildren(
                        mapOf(
                            "name" to nam,
                            "number" to num,

                            "start" to stat,
                            "dest" to dect,
                        )
                    )
                    // Show a toast message indicating that the record was updated
                    Toast.makeText(
                        this@TrainEdit,
                        "Record updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

            }.addOnFailureListener { exception ->
                // Handle any errors that occur
            }

        btDelete.setOnClickListener {
            // Check if a record ID exists
            if (recordId != null) {
                // Get the reference to the specific train record in the database
                val trainRef = database.child("Trains").child(recordId!!)

                // Remove the train record from the database
                trainRef.removeValue()
                    .addOnSuccessListener {
                        // Show a toast message indicating that the record was deleted
                        Toast.makeText(
                            this@TrainEdit,
                            "Record deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        // Handle any errors that occur
                        Toast.makeText(
                            this@TrainEdit,
                            "Failed to delete record: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

    }
}