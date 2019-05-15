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

class password_reset : AppCompatActivity() {

    private var user_email: EditText?=null
    private var button_reset: Button?=null
    private var firebase: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        user_email=findViewById(R.id.email_reset_page)
        button_reset=findViewById(R.id.reset_btn)
        firebase=FirebaseAuth.getInstance()

        button_reset?.setOnClickListener{
            ResetPassword()
        }
    }
    private fun ResetPassword(){
        var email=user_email?.text.toString().trim()
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext,"email field can't be empty",Toast.LENGTH_SHORT).show()
        }
        else
        {
            firebase?.sendPasswordResetEmail(email)?.addOnCompleteListener(object :OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {
                    if(task.isSuccessful)
                    {
                        Toast.makeText(applicationContext,"password changing link is sent to your gmail, Please check",Toast.LENGTH_SHORT).show()
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
