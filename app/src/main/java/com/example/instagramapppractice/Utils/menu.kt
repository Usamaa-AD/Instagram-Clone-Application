package com.example.instagramapppractice.Utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import com.example.instagramapppractice.LoginSignup.Login
import com.example.instagramapppractice.LoginSignup.Signup
import com.example.instagramapppractice.PostsUploadActivities.SavedPosts
import com.example.instagramapppractice.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

fun showMenu(context: Context,view: View){
    val menu = PopupMenu(context,view)
    menu.inflate(R.menu.profile_menu)
    menu.setOnMenuItemClickListener {
        when(it.itemId){
            R.id.saved -> {
               val intent = Intent(context,SavedPosts::class.java)
                context.startActivity(intent)
                true
            }
            R.id.editProfile ->{
                val intent = Intent(context,Signup::class.java)
                intent.putExtra("Edit",1)
                context.startActivity(intent)
                true
            }
            R.id.logout ->{
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle("Delete Account")
                alertDialog.setMessage("Are you sure you want to delete your account?")
                alertDialog.setPositiveButton("Yes"){DialogInterface, which ->
                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).delete().addOnSuccessListener {
                        val user = Firebase.auth.currentUser
                        user?.delete()?.addOnSuccessListener {
                            val intent = Intent(context,Signup::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }

                    }

                }
                alertDialog.setNegativeButton("No"){DialogInterface,which->
                    DialogInterface.dismiss()
                }
                    .create()
                    .show()
                true
            }
            else -> false
        }
    }
    try {
        val menuBar = PopupMenu::class.java.getDeclaredField("Menu Bar")
        menuBar.isAccessible = true
        val menuItems = menuBar.get(menu)
        menuItems.javaClass.getDeclaredMethod("items",Boolean::class.java).invoke(menuItems,true)
    }catch (e:Exception){

    }
    finally {
        menu.show()
    }
}