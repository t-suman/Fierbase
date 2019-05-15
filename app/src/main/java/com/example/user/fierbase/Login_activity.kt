package com.example.user.fierbase

import android.content.Intent
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

class Login_activity : AppCompatActivity() {

    private var email:EditText?=null
    private var pass:EditText?=null
    private var login:Button?=null
    private var signup: Button?=null
    private var firebaseAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)

        email=findViewById(R.id.ip_email_login)
        pass=findViewById(R.id.ip_pass_login)
        login=findViewById(R.id.login_btn_login)
        signup=findViewById(R.id.signup_btn_login)
        firebaseAuth=FirebaseAuth.getInstance()

        login?.setOnClickListener{
            LoginUser()
        }
    }
    private fun LoginUser(){
        var email_text=email?.text.toString().trim()
        var pass_text=pass?.text.toString().trim()

        if(TextUtils.isEmpty(email_text))
            Toast.makeText(applicationContext,"Email field can't be empty", Toast.LENGTH_SHORT).show()
        else if(TextUtils.isEmpty(pass_text))
            Toast.makeText(applicationContext,"Password field can't be empty", Toast.LENGTH_SHORT).show()
        else {
            firebaseAuth?.signInWithEmailAndPassword(email_text,pass_text)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"You are logged-in successfully",Toast.LENGTH_SHORT).show()
                        var user:FirebaseUser=firebaseAuth?.currentUser!!
                        if(user.isEmailVerified) {
                            startActivity(Intent(this@Login_activity, home_activity::class.java))
                        }
                        else{
                            Toast.makeText(applicationContext,"This account is not verified, Kindly check your email",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        var error=task.exception?.message
                        Toast.makeText(applicationContext,"Error : "+error,Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    public fun Reset(view:View){
        startActivity(Intent(this@Login_activity,password_reset::class.java))
    }
}
