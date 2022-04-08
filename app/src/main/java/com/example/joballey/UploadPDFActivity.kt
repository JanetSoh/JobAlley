package com.example.joballey


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File


class UploadPDFActivity : AppCompatActivity() {

    private lateinit var mStorage: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private var uri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStorage = FirebaseStorage.getInstance().getReference()
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("application/pdf")
        //startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1)
    }


//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//            if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
//                if(data.data != null) {
//                    uploadFile(data.data!!)
//                }
//                else{
//                    Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//    }

    private fun uploadFile(data : Uri) {

        val file = Uri.fromFile(File("path/to/file.pdf"))
        val fileRef = mStorage.child("pdf/${file.lastPathSegment}")

        fileRef.putFile(data).addOnSuccessListener{
                taskSnapshot : UploadTask.TaskSnapshot? ->
                Toast.makeText(applicationContext, taskSnapshot.toString(), Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
            }
            .addOnProgressListener {
                    taskSnapshot : UploadTask.TaskSnapshot? ->
                val progress = (100.0 * (taskSnapshot!!.bytesTransferred))
                Toast.makeText(this, progress.toString(),Toast.LENGTH_LONG).show()
            }
    }



}

