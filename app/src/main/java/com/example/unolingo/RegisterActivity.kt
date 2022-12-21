package com.example.unolingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginButton = findViewById(R.id.register_button)
        emailInput = findViewById(R.id.register_input_email)
        passwordInput = findViewById(R.id.register_input_password)

        auth = Firebase.auth

        loginButton.setOnClickListener {
            registerUser(emailInput.text.toString(), passwordInput.text.toString())
        }
    }

    private fun registerUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter correctly", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 6){
            Toast.makeText(this, "Please enter a stronger password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    // register in is successful
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Registration is successful!", Toast.LENGTH_SHORT).show()
                }
                else{
                    // register in is not successful. Display bar to indicate

                    Toast.makeText(this, "Register failed. ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred while registering: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}