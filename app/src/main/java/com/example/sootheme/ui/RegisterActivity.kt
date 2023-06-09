package com.example.sootheme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.sootheme.R
import com.example.sootheme.databinding.ActivityRegisterBinding
import com.example.sootheme.model.RegisterViewModel
import com.example.sootheme.network.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val regisViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val regisName = binding.regisUsername
        val regisEmail = binding.regisEmail
        val regisPassword = binding.regisPassword

        binding.registerButton.setOnClickListener {
            val name = regisName.text.toString()
            val email = regisEmail.text.toString()
            val password = regisPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.fill_field)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else if (password.length < 8 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val msg = getString(R.string.password_email_error)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else {
                loadingState(true)
                regisViewModel.userRegister(name, email, password)
                regisViewModel.error.observe(this) { error ->
                    if (error == false) {
                        loadingState(false)
                        val msg = getString(R.string.register_success)
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        loadingState(false)
                        val msg = getString(R.string.register_failed)
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadingState(state: Boolean) {
        binding.loadingIcon.visibility = if (state) View.VISIBLE else View.GONE
    }
}