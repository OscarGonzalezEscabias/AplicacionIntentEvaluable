package com.example.actividadevaluable1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfigActivity : AppCompatActivity() {

    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        phoneEditText = findViewById(R.id.phoneEditText)
        saveButton = findViewById(R.id.saveButton)

        initEvent()
    }

    override fun onResume() {
        super.onResume()
        // Revisar si venimos desde el segundo Activity y el numero guardado en la referencia compartida para ponerlo en phoneEditText
        val phone = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
            .getString("phoneNumber", "")
        findViewById<EditText>(R.id.phoneEditText).setText(phone)
    }

    private fun initEvent() {
        // Guardar el número de teléfono en SharedPreferences
        saveButton.setOnClickListener {
            savePhoneNumber()
        }
    }

    private fun savePhoneNumber() {
        val phone = phoneEditText.text.toString()
        if (phone.isNotEmpty()) {
            val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putString("phoneNumber", phone).apply()

            // Muestra un mensaje y va a MainActivity
            Toast.makeText(this, "Número guardado", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Introduce un número de teléfono", Toast.LENGTH_SHORT).show()
        }
    }
}
