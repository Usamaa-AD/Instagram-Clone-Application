package com.example.instagramapppractice.LoginSignup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramapppractice.HomeActivity
import com.example.instagramapppractice.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast


class Login : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val textView = binding.signupText
        val text = "Don't have an account? Sign up"
        val spannableString = SpannableString(text)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#000000")), 0, 23,0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#f95537")), 23, text.length,0)
        textView.setText(spannableString)
        binding.signupText.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
            finish()
        }
        binding.loginButton.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.pass.editText?.text.toString()
            if (email.isEmpty()||password.isEmpty()){
                FancyToast.makeText(this,"Please fill all credentials",
                    FancyToast.LENGTH_LONG,
                    FancyToast.INFO,false).show()
            }else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    FancyToast.makeText(this,"Login Successful",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,false).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
        }


    }
}