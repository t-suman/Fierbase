package com.example.user.fierbase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.net.URI
import java.util.*

class firebase_storage : AppCompatActivity() {

    private var upload_btn: Button?=null
    private var image_view:ImageView?=null
    private var imageUri: Uri?=null
    private var storage:FirebaseStorage?=null
    private var imageRef:StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_storage)

        upload_btn=findViewById(R.id.upload_image_btn)
        image_view=findViewById(R.id.image_view)
        storage= FirebaseStorage.getInstance()
        imageRef=storage?.reference?.child("Images")

        image_view?.setOnClickListener {
            PickImageFromGallery()
        }

        upload_btn?.setOnClickListener {
            AddImageToFirebase()
        }
    }

    private fun PickImageFromGallery(){
        var gallery= Intent()
        gallery.type="image/*"
        gallery.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(gallery,111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==111 && resultCode==Activity.RESULT_OK && data!=null){

            imageUri=data.data
            image_view?.setImageURI(imageUri)
            /*try {
                val image=MediaStore.Images.Media.getBitmap(contentResolver,imageUri)
                image_view?.setImageBitmap(image)
            }
            catch (error: IOException){
                Toast.makeText(applicationContext,"ERROR : "+error,Toast.LENGTH_SHORT).show()
            }*/

        }
    }

    private fun AddImageToFirebase(){
        if(imageUri!=null){

            var ref=imageRef?.child(UUID.randomUUID().toString())
            ref?.putFile(imageUri!!)?.addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot>{
                override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Image successfully uploaded",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        var error=task.exception
                        Toast.makeText(applicationContext,"ERROR : "+error,Toast.LENGTH_SHORT).show()
                    }
                }


            })

        }
        else{
            Toast.makeText(applicationContext,"ERROR : null",Toast.LENGTH_SHORT).show()
        }

    }
}
