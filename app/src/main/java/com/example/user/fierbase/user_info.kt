package com.example.user.fierbase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class user_info : AppCompatActivity() {

    private var firstName:TextView?=null
    private var lastName:TextView?=null
    private var user_id:TextView?= null
    private var change_email: Button?=null
    private var update_info:Button?=null
    private var firebaseAuth:FirebaseAuth?=null
    private var firebaseDatabase:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        firstName=findViewById<TextView>(R.id.first_name_text_view)
        lastName=findViewById<TextView>(R.id.last_name_text_view)
        user_id=findViewById<TextView>(R.id.user_id_text_view)
        change_email=findViewById(R.id.change_email_btn_user_info_layout)
        update_info=findViewById(R.id.update_info_btn_user_info_layout)

        firebaseAuth=FirebaseAuth.getInstance()
        firebaseDatabase=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        firebaseDatabase?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(task: DataSnapshot) {
                if(task.exists()){
                    var fname=task.child("firstName").value as String
                    var lname=task.child("lastName").value as String
                    var userid=task.child("userName").value as String

                    firstName?.text=fname
                    lastName?.text=lname
                    user_id?.text=userid

                }
            }

        })


        change_email?.setOnClickListener {
            startActivity(Intent(this@user_info,changeEmail::class.java))
        }
        update_info?.setOnClickListener {
            startActivity(Intent(this@user_info,profile_activity::class.java))
        }
    }
}
