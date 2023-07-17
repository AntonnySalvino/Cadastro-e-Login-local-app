package com.example.projetoviggo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projetoviggo.model.UserModel

class DBHelper(context: Context): SQLiteOpenHelper(context, "database.db", null, 1 ) {
    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES('user', 'pass')",
    )
    override fun onCreate(db: SQLiteDatabase?) {
        sql.forEach {
            db?.execSQL(it)
            //O loop forEach itera sobre cada elemento do array sql.
            //Para cada instrução SQL no array, a função execSQL é chamada no objeto db,
            // que representa o banco de dados. A função execSQL é responsável por executar a
            // instrução SQL no banco de dados.
            //O operador ?. é usado para verificar se o objeto db é nulo antes de chamar o método execSQL.
            // Isso evita erros caso o objeto db seja nulo.
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE users")
        onCreate(db)
        // A primeira linha db?.execSQL("DROP TABLE users") executa uma instrução SQL para
        // excluir a tabela "users" do banco de dados. Isso significa que a tabela será removida
         // do banco de dados durante o processo de atualização.

        // A segunda linha onCreate(db) chama a função onCreate que discutimos anteriormente. Essa
        // função é responsável por criar novamente a tabela "users" e configurar a estrutura inicial do
        // banco de dados.
    }
    fun usersInsert(username: String, password: String): Long{
        val db = this.writableDatabase
        //A primeira linha val db = this.writableDatabase obtém uma referência para o objeto SQLiteDatabase de escrita. Isso é feito utilizando o método writableDatabase herdado da classe SQLiteOpenHelper, que retorna um objeto SQLiteDatabase para executar operações de escrita no banco de dados.
        val contentValues = ContentValues()
        //O ContentValues é uma classe fornecida pelo Android que permite armazenar um conjunto de pares chave-valor.
        //Ao criar um objeto ContentValues, você pode usar o método put() para adicionar pares chave-valor. A chave é geralmente uma string que representa o nome da coluna na tabela, e o valor é o valor que você deseja inserir ou atualizar para essa coluna.
        contentValues.put("username", username)
        contentValues.put("password", password)
        val resultado = db.insert("users", null, contentValues)
        return resultado
    }

    fun validateLogin(username: String, password: String): Boolean{
        val db =this.readableDatabase
        val c = db.rawQuery("SELECT * FROM users where username=? and password=?",
            arrayOf(username, password))

        if (c.count==1){
            db.close()
            return true
        }else{
            db.close()
            return false
        }
    }


    // FUNÇÔES NÂO UTILIZADAS

    fun usersUpdate(id: Int, username: String, password: String): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val resultado = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        //  a cláusula WHERE é definida como "id=?" (onde o ponto de interrogação ? é um marcador de posição para um valor), indicando que o critério de filtragem é o ID do registro.
        //Ao utilizar o arrayOf(id.toString()), você está fornecendo um array de valores para substituir o marcador de posição ? na cláusula WHERE.
        db.close()
        return resultado

    }


    fun usersDelete(id: Int): Int{
        val db = this.writableDatabase
        val resultado = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
        return resultado
    }


    fun getUser(username: String, password: String): UserModel {
        val db =this.readableDatabase
        val c = db.rawQuery("SELECT * FROM users where username=? and password=?",
            arrayOf(username, password))
        var userModel = UserModel()
        if (c.count==1){
            c.moveToFirst()
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")
            val idIndex = c.getColumnIndex("id")

            userModel = UserModel(usernameIndex.toString(), passwordIndex.toString(), idIndex)
        }

        db.close()
        return userModel
    }


}