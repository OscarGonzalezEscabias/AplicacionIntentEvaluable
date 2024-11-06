package com.example.actividadevaluable1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class CallActivity : AppCompatActivity() {

    private lateinit var phoneNumber: String
    private lateinit var callButton: Button
    private lateinit var configButtom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        phoneNumber = intent.getStringExtra("phoneNumber") ?: "123456789"
        callButton = findViewById(R.id.callButton)
        configButtom = findViewById(R.id.configButtom)

        initEvent()

    }

    private fun initEvent() {
        // Configurar el botón para realizar la llamada
        callButton.setOnClickListener {
            checkPermission()
        }

        configButtom.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }
    }

    // Registrar el launcher para la solicitud de permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            call()
        } else {
            Toast.makeText(this, "Permiso de llamada denegado. Actívelo en ajustes.", Toast.LENGTH_SHORT).show()
            goToConfigurationApp()
        }
    }

    private fun checkPermission() {
        // Verifica la versión de la API
        if (Build.VERSION.SDK_INT < 23) {
            // Si la versión es menor a API 23, no se requieren permisos
            call()
        } else {
            // Si la versión es API 23 o superior, verifica y solicita permisos
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                call()
            } else {
                // Solicita el permiso para hacer llamadas
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun call() {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun goToConfigurationApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}
