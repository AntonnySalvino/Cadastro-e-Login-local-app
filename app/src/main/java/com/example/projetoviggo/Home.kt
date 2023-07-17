package com.example.projetoviggo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetoviggo.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        binding.textview.text = "Seja Bem Vindo(a) $username"

        binding.buttonSair.setOnClickListener {

            val editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            //ou
            // sharedPreferences.edit().putString("username", "").apply()
            finish()
        }
    }
}