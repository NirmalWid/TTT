package com.example.qr_scanner

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner.model.TrainModel
import com.google.firebase.database.FirebaseDatabase

class TrainReg : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etType: Spinner
    private lateinit var etLine: Spinner
    private lateinit var etStart: EditText
    private lateinit var etDest: EditText
    private lateinit var btNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_reg)

        etName = findViewById(R.id.editTextText4)
        etNumber = findViewById(R.id.editTextText5)
        etType = findViewById(R.id.spinner)
        etLine = findViewById(R.id.spinner2)
        etStart = findViewById(R.id.editTextText8)
        etDest = findViewById(R.id.editTextText9)
        btNext = findViewById(R.id.button5)

        val dbRef = FirebaseDatabase.getInstance().getReference("Trains")

        val types = listOf("Select Type", "  Express","  Normal", "  Inter City" )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etType.adapter = adapter

        val lines = listOf("Select Line", "  Main Line","  Northern Line","  Batticaloa Line", "  Coastal Line")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, lines)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etLine.adapter = adapter2


        btNext.setOnClickListener(){

            val name = etName.text.toString()
            val number = etNumber.text.toString()
            val type = etType.selectedItem.toString()
            val line = etLine.selectedItem.toString()
            val start = etStart.text.toString()
            val dest = etDest.text.toString()

            if (name.isEmpty()){
                Toast.makeText(this, "Please Enter Train Name", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (number.isEmpty()){
                Toast.makeText(this, "Please Enter Train Number", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (etType.selectedItem.toString().isEmpty()) {
                Toast.makeText(this, "Please Select Train Type", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (etLine.selectedItem.toString().isEmpty()) {
                Toast.makeText(this, "Please Select Train Line", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (start.isEmpty()){
                Toast.makeText(this, "Please Enter Train Start", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (dest.isEmpty()){
                Toast.makeText(this, "Please Enter Train Destination", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

                val trainId = dbRef.push().key

                val train = TrainModel(trainId!!, name, number, type, line, start, dest)

                dbRef.child(trainId).setValue(train)
                    .addOnSuccessListener {

                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                        etName.text.clear()
                        etNumber.text.clear()
                        etType.setSelection(0)
                        etLine.setSelection(0)
                        etStart.text.clear()
                        etDest.text.clear()

                        val intent = Intent(this, Roles::class.java)
                        startActivity(intent)


                    }.addOnFailureListener {
                        // Show an error message to the user
                        Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_LONG).show()
                    }

        }
    }

}
