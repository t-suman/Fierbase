package com.example.user.fierbase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class home_activity : AppCompatActivity() {

    private var btn: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)
        btn=findViewById(R.id.change_email_home_page_btn)

        btn?.setOnClickListener {
            startActivity(Intent(this@home_activity,changeEmail::class.java))
        }

    }
    public fun updateProfile(view:View){
        startActivity(Intent(this@home_activity,profile_activity::class.java))
    }
    public fun seeProfile(view:View){
        startActivity(Intent(this@home_activity,user_info::class.java))
    }
    public fun uploadImage(view:View){
        startActivity(Intent(this@home_activity,firebase_storage::class.java))
    }
}
