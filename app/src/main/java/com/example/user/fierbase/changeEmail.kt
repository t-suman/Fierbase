package com.example.user.fierbase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class changeEmail : AppCompatActivity() {

    private var old_email:EditText?=null
    private var new_email:EditText?=null
    private var pass:EditText?=null
    private var update:Button?=null
    private var firebase: FirebaseAuth?=null
    private var user:FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        old_email=findViewById(R.id.email_edit_text_change)
        new_email=findViewById(R.id.new_email_edit_text_change)
        pass=findViewById(R.id.user_edit_text_password_change)
        update=findViewById(R.id.change_email_btn)
        firebase=FirebaseAuth.getInstance()
        user=firebase?.currentUser

        update?.setOnClickListener {
            var old_email_text=old_email?.text.toString().trim()
            var new_email_text=new_email?.text.toString().trim()
            var pass_text=pass?.text.toString().trim()

            var userInfo=EmailAuthProvider.getCredential(old_email_text,pass_text)

            user?.reauthenticate(userInfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {
                    if(task.isSuccessful){
                        user!!.updateEmail(new_email_text).addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(p0: Task<Void>) {
                                if(p0.isSuccessful){
                                    Toast.makeText(applicationContext,"Your email has been updated",Toast.LENGTH_SHORT).show()
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
