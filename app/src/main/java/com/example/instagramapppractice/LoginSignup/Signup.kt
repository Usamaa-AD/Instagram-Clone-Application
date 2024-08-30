package com.example.instagramapppractice.LoginSignup

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramapppractice.HomeActivity
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shashank.sony.fancytoastlib.FancyToast

class Signup : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var user: User

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        if (intent.hasExtra("Edit")) {
            if (intent.getIntExtra("Edit", 0) == 1) {
                binding.signupButton.setText("Save Changes")
                binding.fbBtn.visibility = View.GONE
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    binding.username.editText?.setText(user?.name)
                    binding.email.editText?.setText(user?.email)
                    binding.pass.editText?.setText(user?.pass)
                    binding.textView6.visibility = View.GONE
                }
            }
        }

        binding.signupButton.setOnClickListener {
            if (intent.hasExtra("Edit")) {
                if (intent.getIntExtra("Edit", 0) == 1) {
                    val updatedName = binding.username.editText?.text.toString()
                    val updatedEmail = binding.email.editText?.text.toString()
                    val updatedPass = binding.pass.editText?.text.toString()
                    if (updatedEmail == "" || updatedPass == "" || updatedName == "") {
                        FancyToast.makeText(
                            this,
                            "Please fill all credentials",
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO,
                            false
                        ).show()
                    }
                else {
                        //can use currentUser.update approach
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).update("name",updatedName)
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).update("email",updatedEmail)
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).update("pass",updatedPass)
                                        FancyToast.makeText(
                                            this,
                                            "Changes saved successfully",
                                            FancyToast.LENGTH_LONG,
                                            FancyToast.SUCCESS,
                                            false
                                        ).show()
                                        val intent = Intent(this,ProfileUploadActivity::class.java)
                                        intent.putExtra("Update",1)
                                        startActivity(intent)
                                        finish()
                                    }


                    }


            } else {
                val email = binding.email.editText?.text.toString()
                val username = binding.username.editText?.text.toString()
                val password = binding.pass.editText?.text.toString()
                if (email == "" || username == "" || password == "") {
                    FancyToast.makeText(
                        this,
                        "Please fill all credentials",
                        FancyToast.LENGTH_LONG,
                        FancyToast.INFO,
                        false
                    ).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            user = User(username, password, email)
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    FancyToast.makeText(
                                        this,
                                        "Account created successfully",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        false
                                    ).show()
                                    startActivity(Intent(this, ProfileUploadActivity::class.java))
                                    finish()
                                }

                        }
                }
            }

        }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}