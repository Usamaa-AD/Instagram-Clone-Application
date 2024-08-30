package com.example.instagramapppractice.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.util.UUID

 fun uploadImage(uri: Uri, imagesFolder: String,context: Context, callback: (String) -> Unit) {
     val progressDialog = ProgressDialog(context)
    Firebase.storage.getReference(imagesFolder).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                progressDialog.dismiss()
                callback(it.toString())
            }
        }.addOnProgressListener {
            val uploaded = (it.bytesTransferred / it.totalByteCount) * 100
            progressDialog.setMessage("Uploading $uploaded%")
            progressDialog.show()
        }
}
