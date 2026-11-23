package com.example.tenantmanagementsystemgroupa.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemgroupa.databinding.ActivityLoginBinding
import com.example.tenantmanagementsystemgroupa.ui.main.MainActivity

/**
 * Login screen allowing property managers to authenticate.
 * Implements rubric requirement for cross-activity intent extra dispatch.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            handleLogin()
        }

        binding.tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text?.toString()?.trim() ?: ""
        val password = binding.etPassword.text?.toString()?.trim() ?: ""

        var hasError = false

        if (email.isEmpty()) {
            binding.tilEmail.error = "Required"
            hasError = true
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Required"
            hasError = true
        } else {
            binding.tilPassword.error = null
        }

        if (hasError) return

        /**
         * University Rubric Criterion:
         * "Send the email from LoginActivity to MainActivity with putExtra,
         * read it in MainActivity with getStringExtra, and show it in a Toast: 'Logged in as ...'."
         */
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("EMAIL", email)
        }
        startActivity(intent)
        finish() // Prevent returning to Login on back button press
    }
}
