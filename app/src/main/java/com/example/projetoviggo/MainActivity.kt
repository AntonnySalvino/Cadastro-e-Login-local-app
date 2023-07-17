package com.example.projetoviggo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projetoviggo.database.DBHelper
import com.example.projetoviggo.databinding.ActivityMainBinding
//POC 1 - cadastro e login local
//Visto que já acompanhou o suficiente do curso de kotlin + android para introdução
// de DB e transições de tela a proposta é a seguinte: Realizar um cadastro de usuário
// local, apenas salvando no DB as informações cadastradas e em seguida utilizar estas
// informações salvas para autorizar um login do usuário. O projeto deve ter acesso à um
// banco de dados da sua escolha e possuir ao menos 3 telas, uma de cadastro (para que o
// usuário cadastre-se), uma de login (para autorizar ou não o usuário à entrar no sistema)
// e uma tela de home que dê boas vindas ao usuário após o login. Caso o usuário não seja
// autorizado o sistema deve informar que a tentativa não foi bem sucedida.



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        //As SharedPreferences são um mecanismo de armazenamento de dados chave-valor no Android. Elas permitem que você armazene pequenos conjuntos de dados de forma persistente e os recupere posteriormente.
        val logged = sharedPreferences.getString("username", "")
        // Essa linha cria uma instância de SharedPreferences usando o contexto da aplicação (application) e o método getSharedPreferences. O primeiro parâmetro é o nome do arquivo de preferências compartilhadas, que neste caso é "login". O segundo parâmetro, Context.MODE_PRIVATE, indica que o arquivo de preferências compartilhadas só será acessível pela própria aplicação.



        binding.buttonLogin.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (password.isNotEmpty() && username.isNotEmpty()){
                if (db.validateLogin(username, password)){

                    Toast.makeText(this, "Login OK!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Home::class.java))

                    val editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.apply()


                }else{
                    Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
                    binding.password.setText("")
                    binding.username.setText("")
                }
            }else{
                Toast.makeText(this, "Insira todos os requisitos", Toast.LENGTH_SHORT).show()
            }


        }
        binding.textSign.setOnClickListener {
            startActivity(Intent(this, TelaCadastro::class.java))
        }




    }
}