package com.example.jeffenhagen.dao

import android.content.Context
import android.util.Log
import com.example.jeffenhagen.models.Visualizer


class VisualizerDAO(context: Context) {

    var context: Context
    var dbConnection: DatabaseInit

    init {
        this.context = context
        this.dbConnection = DatabaseInit(context)
    }

    fun getAll(): ArrayList<Visualizer> {
        val db = dbConnection.writableDatabase
        val pedidos = ArrayList<Visualizer>()
        val cursor = db.rawQuery(
            "SELECT * FROM pedidochocolate AS pc " +
                    "INNER JOIN cliente AS cl ON pc.fkcpf = cl.cpf", null
        )

        with(cursor) {
            while (moveToNext()) {
                try {
                    val cpf = getString(getColumnIndexOrThrow("cpf"))
                    val nome = getString(getColumnIndexOrThrow("nome"))
                    val telefone = getString(getColumnIndexOrThrow("telefone"))
                    val idChocolate = getInt(getColumnIndexOrThrow("idchocolate"))
                    val tipoDeChocolate = getString(getColumnIndexOrThrow("tipodechocolate"))
                    val tipoDeCastanha = getString(getColumnIndexOrThrow("tipodecastanha"))
                    val porcentagemDeLeite = getFloat(getColumnIndexOrThrow("porcentagemdeleite"))

                    val pedido = Visualizer(
                        cpf,
                        nome,
                        telefone,
                        idChocolate,
                        tipoDeChocolate,
                        tipoDeCastanha,
                        porcentagemDeLeite
                    )
                    pedidos.add(pedido)

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

    fun getOneByCpf(cpf: String): ArrayList<Visualizer> {
        val db = dbConnection.readableDatabase
        val pedidos = ArrayList<Visualizer>()
        val cursor = db.rawQuery(
            "SELECT * FROM pedidochocolate AS pc " +
                    "INNER JOIN cliente AS cl ON pc.fkcpf = cl.cpf WHERE cl.cpf LIKE '%$cpf%'", null
        )

        with(cursor) {
            while (moveToNext()) {

                val cpf = getString(getColumnIndexOrThrow("cpf"))
                val nome = getString(getColumnIndexOrThrow("nome"))
                val telefone = getString(getColumnIndexOrThrow("telefone"))
                val idChocolate = getInt(getColumnIndexOrThrow("idchocolate"))
                val tipoDeChocolate = getString(getColumnIndexOrThrow("tipodechocolate"))
                val tipoDeCastanha = getString(getColumnIndexOrThrow("tipodecastanha"))
                val porcentagemDeLeite = getFloat(getColumnIndexOrThrow("porcentagemdeleite"))

                val pedido = Visualizer(
                    cpf,
                    nome,
                    telefone,
                    idChocolate,
                    tipoDeChocolate,
                    tipoDeCastanha,
                    porcentagemDeLeite
                )

                Log.i("Teste", pedido.toString())

                pedidos.add(pedido)
            }

            cursor.close()
            db.close()

            return pedidos
        }
    }

    fun getOneById(idChocolate: Int): Visualizer {
        val db = dbConnection.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM pedidochocolate AS pc " +
                    "INNER JOIN cliente AS cl ON pc.idchocolate = $idChocolate", null
        )

        var pedido = Visualizer("", "", "", 0, "", "", 0.0f)

        with(cursor) {
            while (moveToNext()) {
                val cpf = getString(getColumnIndexOrThrow("cpf"))
                val nome = getString(getColumnIndexOrThrow("nome"))
                val telefone = getString(getColumnIndexOrThrow("telefone"))
                val idChocolate = getInt(getColumnIndexOrThrow("idchocolate"))
                val tipoDeChocolate = getString(getColumnIndexOrThrow("tipodechocolate"))
                val tipoDeCastanha = getString(getColumnIndexOrThrow("tipodecastanha"))
                val porcentagemDeLeite = getFloat(getColumnIndexOrThrow("porcentagemdeleite"))

                pedido = Visualizer(
                    cpf,
                    nome,
                    telefone,
                    idChocolate,
                    tipoDeChocolate,
                    tipoDeCastanha,
                    porcentagemDeLeite
                )
            }
        }

        cursor.close()
        db.close()

        return pedido
    }
}

