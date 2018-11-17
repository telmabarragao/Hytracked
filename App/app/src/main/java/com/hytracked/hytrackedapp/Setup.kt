package com.hytracked.hytrackedapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Setup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }
    /** Called when the user taps the SUBMIT button  */
    fun submitInfo(view: View) {
        val intent = Intent(this, MainMenu::class.java)
        //Guardar informações

        startActivity(intent)
    }
}
