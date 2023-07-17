package com.example.projetoviggo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoviggo.database.DBHelper
import com.example.projetoviggo.databinding.ActivityTelaCadastroBinding

class TelaCadastro : AppCompatActivity() {
    private lateinit var binding: ActivityTelaCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val db = DBHelper(this)
        binding.buttonCadastrar.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmarPassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (password == confirmPassword){
                    val resultado= db.usersInsert(username, password)
                    if (resultado>0){
                        Toast.makeText(this, "Sign Up OK!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else{
                        Toast.makeText(this, "Deu errado", Toast.LENGTH_SHORT).show()
                        binding.password.setText("")
                        binding.confirmarPassword.setText("")
                        binding.username.setText("")
                    }

                }else{
                    Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Insira todos os requisitos", Toast.LENGTH_SHORT).show()
            }
        }

    }
}