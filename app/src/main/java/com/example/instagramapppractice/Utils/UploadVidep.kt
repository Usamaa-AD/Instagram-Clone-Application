package com.example.instagramapppractice.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.TextView
import android.widget.VideoView
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.util.UUID

fun uploadVideo(uri: Uri,folderName:String,context: Context,textView:TextView,videoView:VideoView,callback: (String)->Unit){
    val progressDialog = ProgressDialog(context)
    Firebase.storage.getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener {
        it.storage.downloadUrl.addOnSuccessListener {
            progressDialog.dismiss()
            videoView.isVisible=true
            textView.isVisible=false
            callback(it.toString())
        }
    }.addOnProgressListener {
        val uploaded = (it.bytesTransferred/it.totalByteCount)*100
        progressDialog.setTitle("Uploading $uploaded%")
        progressDialog.show()
    }
}