package com.example.passcodebiometriclock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

class AuthActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val correctPin = "1234" // Replace with secure check

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val passcodeInput = findViewById<EditText>(R.id.passcode_input)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        // Biometric setup
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, Executors.newSingleThreadExecutor(),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle error (e.g., show an error message)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Navigate to the next screen on success
                    // For example:
                    navigateToHome()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Handle failed authentication
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login with Biometrics")
            .setNegativeButtonText("Use PIN")
            .build()

        findViewById<ImageView>(R.id.biometric_icon).setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        loginBtn.setOnClickListener {
            if (passcodeInput.text.toString() == correctPin) {
                navigateToHome()
            } else {
                showFailureDialog("Incorrect PIN. Try again.")
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showFailureDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Authentication Failed")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }


}
