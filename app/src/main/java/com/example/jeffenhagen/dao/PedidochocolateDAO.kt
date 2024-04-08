package com.example.jeffenhagen.dao
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.jeffenhagen.models.Pedidochocolate
import kotlin.IllegalArgumentException


class PedidochocolateDAO(context: Context) {

    var context : Context
    var dbConnection: DatabaseInit

    init{
        this.context = context
        this.dbConnection = DatabaseInit(context)
    }

    fun insertPedidochocolate(pedido: Pedidochocolate){
        val db = dbConnection.writableDatabase
        val values = ContentValues().apply {
            put("idchocolate", pedido.idChocolate)
            put("tipodechocolate", pedido.tipoDeChocolate)
            put("tipodecastanha", pedido.tipoDeCastanha)
            put("porcentagemdeleite", pedido.porcentagemDeLeite)
            put("fkcpf", pedido.fkCpf)
        }

        val testInsert = db?.insert("pedidochocolate", null, values)
        Log.i("testPedido", "Insert: {$testInsert}")
        db.close()
    }

    fun updatePedidochocolate(pedido: Pedidochocolate) {
        val db = dbConnection.writableDatabase
        val values = ContentValues().apply {
            put("tipodechocolate", pedido.tipoDeChocolate)
            put("tipodecastanha", pedido.tipoDeCastanha)
            put("porcentagemdeleite", pedido.porcentagemDeLeite)
        }

        val condition = "idchocolate = ${pedido.idChocolate}"
        val testUpdate = db.update("pedidochocolate", values, condition, null)
        Log.i("testPedido", "Update: {$testUpdate}")
        db.close()
    }

    fun deletePedidochocolate(idChocolate: Int) {
        val db = dbConnection.readableDatabase

        val condition = "idchocolate = $idChocolate"
        val testDelete = db.delete("pedidochocolate", condition, null)
        Log.i("testPedido", "Delete: {$testDelete}")
        db.close()
    }


    fun findAllPedidoChocolate(): ArrayList<Pedidochocolate> {
        val db = dbConnection.readableDatabase
        val pedidos = ArrayList<Pedidochocolate>()
        val cursor = db.rawQuery("SELECT * FROM pedidochocolate", null)

        with(cursor) {
            while (moveToNext()) {
                try {
                    val idChocolate = getInt(getColumnIndexOrThrow("idchocolate"))
                    Log.i("Teste", idChocolate.toString())
                    val tipoDeChocolate = getString(getColumnIndexOrThrow("tipodechocolate"))
                    val tipoDeCastanha = getString(getColumnIndexOrThrow("tipodecastanha"))
                    val porcentagemDeLeite = getFloat(getColumnIndexOrThrow("porcentagemdeleite"))
                    val fkCpf = getString(getColumnIndexOrThrow("fkcpf"))

                    val pedido = Pedidochocolate(idChocolate, tipoDeChocolate, tipoDeCastanha, porcentagemDeLeite, fkCpf)
                    pedidos.add(pedido)
                    Log.i("testPedido", "FindAll: {${pedido.idChocolate}, ${pedido.tipoDeChocolate}, ${pedido.tipoDeCastanha}, ${pedido.porcentagemDeLeite}, ${pedido.fkCpf}}")

                } catch (error: IllegalArgumentException) {
                    Log.i("testPedido", "Column not found in cursor: ${error.message}")
                }
            }
        }
        Log.i("testPedido", pedidos.toString())
        cursor.close()
        db.close()
        return pedidos
    }

    fun findOneByIdPedidochocolate(idchocolate: Int): Pedidochocolate{
        val db = dbConnection.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM pedidochocolate WHERE idchocolate = $idchocolate", null)

        cursor.moveToFirst()

        val pedido = Pedidochocolate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getString(4))

        Log.i("testPedido", "findOneByIdTESTE: {${pedido.idChocolate}, ${pedido.tipoDeChocolate}, ${pedido.tipoDeCastanha}, ${pedido.porcentagemDeLeite}, ${pedido.fkCpf}}")

        cursor.close()
        db.close()

        return pedido
    }


    //verificar
    fun validateFkCpf(fkCpf: String): Boolean {
        val db = dbConnection.readableDatabase
        val cursor = db.rawQuery("SELECT cpf FROM cliente WHERE cpf like '%$fkCpf%'", null)
        var existente = false

        try {

            if (cursor.moveToFirst()) {
                existente = true
            }

        } catch (e: Exception) {
            e.printStackTrace()

        } finally {
            cursor?.close()
            db.close()
        }

        Log.i("testValidate", existente.toString())

        return existente
    }
}