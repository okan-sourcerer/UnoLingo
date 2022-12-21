package com.example.unolingo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    private lateinit var registerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button)
        emailInput = findViewById(R.id.login_input_email)
        passwordInput = findViewById(R.id.login_input_password)
        registerText = findViewById(R.id.login_register_text)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        loginButton.setOnClickListener {
            performSignIn(emailInput.text.toString(), passwordInput.text.toString())
        }

        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performSignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Login is successful!", Toast.LENGTH_SHORT).show()
                }
                else{
                    // Login in is not successful.

                    Toast.makeText(this, "Login failed. ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred while Login: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object{
        private const val TAG = "LoginActivity"
    }
}