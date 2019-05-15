package com.example.user.fierbase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class profile_activity : AppCompatActivity() {

    private var user_fname:EditText?=null
    private var user_lname:EditText?=null
    private var user_id:EditText?=null
    private var update_btn:Button?=null
    private var firebaseAuth:FirebaseAuth?=null
    private var user:FirebaseUser?=null
    private var firebaseDatabase:DatabaseReference?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_activity)

        user_fname=findViewById(R.id.user_first_name)
        user_lname=findViewById(R.id.user_last_name)
        user_id=findViewById(R.id.user_userid)
        update_btn=findViewById(R.id.userinfo_update_btn)
        firebaseAuth=FirebaseAuth.getInstance()
        user=firebaseAuth?.currentUser
        firebaseDatabase=FirebaseDatabase.getInstance().reference.child("Users").child(user!!.uid)

        update_btn?.setOnClickListener {
            saveUserInfo()
        }

    }
    private fun saveUserInfo(){

        var first_name=user_fname?.text.toString().trim()
        var last_name=user_lname?.text.toString().trim()
        var userid=user_id?.text.toString().trim()

        if(TextUtils.isEmpty(first_name)){
            Toast.makeText(applicationContext,"First Name is required",Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(last_name)){
            Toast.makeText(applicationContext,"Last Name is required",Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(userid)){
            Toast.makeText(applicationContext,"UserID is required",Toast.LENGTH_SHORT).show()
        }
        else{

            var userinfo=HashMap<String,Any>()
            userinfo.put("firstName",first_name)
            userinfo.put("lastName",last_name)
            userinfo.put("userName",userid)

            firebaseDatabase?.updateChildren(userinfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(p0: Task<Void>) {
                    if(p0.isSuccessful){
                        Toast.makeText(applicationContext,"Your Information has been updated",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        var error=p0.exception?.message
                        Toast.makeText(applicationContext,"ERROR : "+error,Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }

    }
}
