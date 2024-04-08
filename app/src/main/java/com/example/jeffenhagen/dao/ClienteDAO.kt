package com.example.jeffenhagen.dao
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.jeffenhagen.models.Cliente
import kotlin.IllegalArgumentException

class ClienteDAO(context: Context) {

    var context : Context
    var dbConnection: DatabaseInit

    init{
        this.context = context
        this.dbConnection = DatabaseInit(context)
    }

    fun insertCliente(cliente: Cliente) {
        val db = dbConnection.writableDatabase
        val values = ContentValues().apply {
            put("cpf", cliente.cpf)
            put("nome", cliente.nome)
            put("telefone", cliente.telefone)
        }
        val testInsert = db?.insert("cliente", null, values)
        Log.i("testCliente", "Insert: {${testInsert.toString()}}")
        db.close()
    }

    fun updateCliente(cliente: Cliente) {
        val db = dbConnection.writableDatabase
        val values = ContentValues().apply {
            put("nome", cliente.nome)
            put("telefone", cliente.telefone)
        }

        val condition = "cpf like '%$cliente.cpf%'"
        val testUpdate = db.update("cliente", values, condition, null)
        Log.i("testCliente", "Update: {$testUpdate}")
        db.close()
    }

    fun deleteCliente(cpf: String) {
        val db = dbConnection.writableDatabase
        val values = ContentValues().apply{
            put("ativo", false)
        }

        val condition = "cpf like '%$cpf%'"
        val testUpdate = db.update("cliente", values, condition, null)
        Log.i("testCliente", "Delete: {$testUpdate}")
        db.close()
    }


    fun findAllCliente(): ArrayList<Cliente> {
        val db = dbConnection.readableDatabase
        val clientes = arrayListOf<Cliente>()
        val cursor = db.rawQuery("SELECT * FROM cliente", null)

        with(cursor) {
            while (moveToNext()) {
                try {
                    val cpf = getString(getColumnIndexOrThrow("cpf"))
                    val nome = getString(getColumnIndexOrThrow("nome"))
                    val telefone = getString(getColumnIndexOrThrow("telefone"))
                    val ativoInt = getInt(getColumnIndexOrThrow("ativo"))
                    val ativo = ativoInt != 0

                    if (ativo) {
                        val cliente = Cliente(cpf, nome, telefone, ativo)
                        clientes.add(cliente)

                        Log.i("testCliente", "FindAll: {$cpf, $nome, $telefone, $ativo}")
                    }

                } catch (error: IllegalArgumentException) {
                    Log.e("testCliente", "Column not found in cursor: ${error.message}")
                }
            }
        }
        cursor.close()
        db.close()

        return clientes
    }

    fun findOneByIdCliente(cpf: String): Cliente? {
        val db = dbConnection.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cliente where cpf like '%$cpf%'", null)

        if (cursor.moveToFirst()) {
            val cliente = Cliente(
                cursor.getString(cursor.getColumnIndexOrThrow("cpf")),
                cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                cursor.getString(cursor.getColumnIndexOrThrow("telefone")),
                cursor.getInt(cursor.getColumnIndexOrThrow("ativo")) != 0
            )

            Log.i(
                "testCliente",
                "findOneById: {${cliente.cpf}, ${cliente.nome}, ${cliente.telefone}}"
            )

            cursor.close()
            db.close()

            return cliente
        }

        cursor.close()
        db.close()

        return null
    }

}
