package com.example.tenantmanagementsystemgroupa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityRegisterBinding

/**
 * Registration activity enabling property administrators to create an account.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            handleRegistration()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun handleRegistration() {
        val email = binding.etRegEmail.text?.toString()?.trim() ?: ""
        val password = binding.etRegPassword.text?.toString()?.trim() ?: ""
        val confirmPassword = binding.etConfirmPassword.text?.toString()?.trim() ?: ""

        var hasError = false

        if (email.isEmpty()) {
            binding.tilRegEmail.error = "Required"
            hasError = true
        } else {
            binding.tilRegEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilRegPassword.error = "Required"
            hasError = true
        } else {
            binding.tilRegPassword.error = null
        }

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Required"
            hasError = true
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = "Passwords do not match"
            hasError = true
        } else {
            binding.tilConfirmPassword.error = null
        }

        if (hasError) return

        Toast.makeText(this, "Account created successfully for $email", Toast.LENGTH_SHORT).show()
        finish()
    }
}
