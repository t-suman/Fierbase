package com.example.user.fierbase

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private var email:EditText?=null
    private var pass:EditText?=null
    private var login:Button?= null
    private  var signup:Button?=null
    private var firebaseAuth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signup=findViewById(R.id.signup_btn)
        email=findViewById(R.id.ip_email)
        pass=findViewById(R.id.ip_pass)
        login=findViewById(R.id.login_btn)
        firebaseAuth=FirebaseAuth.getInstance()

        signup?.setOnClickListener {

            RegisterNewUser()

        }
        login?.setOnClickListener {
            startActivity(Intent(this@MainActivity,Login_activity::class.java))
        }
    }
    private fun RegisterNewUser(){

        var email_text=email?.text.toString().trim()
        var pass_text=pass?.text.toString().trim()

        if(TextUtils.isEmpty(email_text))
            Toast.makeText(applicationContext,"Email field can't be empty",Toast.LENGTH_SHORT).show()
        else if(TextUtils.isEmpty(pass_text))
            Toast.makeText(applicationContext,"Password field can't be empty",Toast.LENGTH_SHORT).show()
        else {
            firebaseAuth?.createUserWithEmailAndPassword(email_text,pass_text)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Account created",Toast.LENGTH_SHORT).show()
                        var user: FirebaseUser =firebaseAuth!!.currentUser!!
                        user.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(p0: Task<Void>) {
                                if(p0.isSuccessful){
                                    Toast.makeText(applicationContext,"please check your email",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity,Login_activity::class.java))
                                }
                                else{
                                    var error=p0.exception?.message
                                    Toast.makeText(applicationContext,"ERROR : "+error,Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                    }
                    else{
                        var error=task.exception?.message
                        Toast.makeText(applicationContext,"ERROR : "+error,Toast.LENGTH_SHORT).show()
                    }

                }

            })


        }
    }




}
