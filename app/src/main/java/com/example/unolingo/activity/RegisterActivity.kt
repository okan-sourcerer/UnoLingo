package com.example.unolingo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.unolingo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var registerButton: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var nameInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.register_button)
        emailInput = findViewById(R.id.register_input_email)
        passwordInput = findViewById(R.id.register_input_password)
        nameInput = findViewById(R.id.register_input_name)

        auth = Firebase.auth

        registerButton.setOnClickListener {
            registerUser(emailInput.text.toString(), passwordInput.text.toString(), nameInput.text.toString())
        }
    }

    private fun registerUser(email: String, password: String, name: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()){
            Toast.makeText(this, "Please enter correctly", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 6){
            Toast.makeText(this, "Please enter a stronger password", Toast.LENGTH_SHORT).show()
            return
        }


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    // register is successful
                    val hashMap = hashMapOf("uid" to auth.uid,
                    "name" to name)
                    auth.uid?.let { it1 ->
                        FirebaseFirestore.getInstance().collection("users").document(
                            it1
                        ).set(hashMap).addOnSuccessListener {
                            Log.d(TAG, "registerUser: user is registered with $name")
                        }
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("NEW_REGISTRATION", true)
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

    companion object{
        private const val TAG = "RegisterActivity"
    }
}