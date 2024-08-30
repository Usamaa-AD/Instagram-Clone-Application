package com.example.instagramapppractice

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramapppractice.LoginSignup.Login
import com.example.instagramapppractice.LoginSignup.ProfileUploadActivity
import com.example.instagramapppractice.LoginSignup.Signup
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        LoadActivity().execute()
    }

    inner class LoadActivity() : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            try {
                Thread.sleep(1500)
            } catch (e: Exception) {
                Thread.interrupted()
            }
            return "Success"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (Firebase.auth.currentUser ==null){
                startActivity(Intent(this@MainActivity, Login::class.java))
            }
            else{
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            }
            finish()
        }
    }
}