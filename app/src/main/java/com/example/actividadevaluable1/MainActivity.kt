package com.example.actividadevaluable1


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var phoneButton: Button
    private lateinit var urlButton: Button
    private lateinit var alarmButton: Button
    private lateinit var emailButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneButton = findViewById(R.id.phoneButton)
        urlButton = findViewById(R.id.urlButton)
        alarmButton = findViewById(R.id.alarmButton)
        emailButton = findViewById(R.id.emailButton)

        initEvent()
    }

    private fun initEvent() {
        // Botón para abrir la actividad de llamada
        phoneButton.setOnClickListener {
            toCall()
        }

        // Botón para abrir una URL
        urlButton.setOnClickListener {
            toUrl()
        }

        // Botón para crear una alarma
        alarmButton.setOnClickListener {
            createAlarm()
        }

        // Botón para enviar correo
        emailButton.setOnClickListener {
            createEmail()
        }
    }

    private fun toCall() {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val phoneNumber = sharedPref.getString("phoneNumber", null)

        if (phoneNumber != null) {
            // Pasa el número de teléfono a CallActivity
            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("phoneNumber", phoneNumber)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se ha configurado un número de teléfono", Toast.LENGTH_SHORT).show()
            // Lanza ConfigActivity si no hay un número guardado
            val configIntent = Intent(this, ConfigActivity::class.java)
            startActivity(configIntent)
        }
    }

    private fun toUrl() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iesvirgendelcarmen.com/"))
        startActivity(intent)
    }

    private fun createAlarm() {
        try {
            // Crea un Intent para configurar una alarma
            val alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma")
                putExtra(AlarmClock.EXTRA_HOUR, 12)
                putExtra(AlarmClock.EXTRA_MINUTES, 0)
            }

            // Verifica si existe una aplicación que pueda manejar el Intent
            if (alarmIntent.resolveActivity(packageManager) != null) {
                startActivity(alarmIntent)
            } else {
                Toast.makeText(this, "No se encontró una aplicación de reloj compatible", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Muestra un mensaje de error si ocurre una excepción
            Toast.makeText(this, "Error al configurar la alarma", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:ejemplo@correo.com")
            putExtra(Intent.EXTRA_SUBJECT, "Asunto personalizado")
        }
        startActivity(emailIntent)
    }
}
