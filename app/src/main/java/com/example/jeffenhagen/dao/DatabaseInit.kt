package com.example.jeffenhagen.dao
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseInit(context: Context) : SQLiteOpenHelper(context, "dbjeffenhagen", null, 1) {

/*
    init{
        context.deleteDatabase("dbjeffenhagen")
    }
*/

    companion object {

        //CLIENTE
        private const val TABLE_CLIENTE = "cliente"
        private const val COLUMN_CPF = "cpf"
        private const val COLUMN_NAME = "nome"
        private const val COLUMN_TELEFONE = "telefone"

        //PEDIDOCHOCOLATE
        private const val TABLE_PEDIDOCHOCOLATE = "pedidochocolate"
        private const val COLUMN_IDCHOCOLATE = "idchocolate"
        private const val COLUMN_TIPODECHOCOLATE = "tipodechocolate"
        private const val COLUMN_TIPODECASTANHA = "tipodecastanha"
        private const val COLUMN_PORCENTAGEMDELEITE = "porcentagemdeleite"
        private const val COLUMN_FKCPF = "fkcpf"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val queryCreateTableCliente = "CREATE TABLE $TABLE_CLIENTE (" +
                "$COLUMN_CPF TEXT NOT NULL PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_TELEFONE TEXT)"
        db.execSQL(queryCreateTableCliente)

        val queryCreateTablePedidochocolate = "CREATE TABLE $TABLE_PEDIDOCHOCOLATE (" +
                "$COLUMN_IDCHOCOLATE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$COLUMN_TIPODECHOCOLATE TEXT,"+
                "$COLUMN_TIPODECASTANHA TEXT," +
                "$COLUMN_PORCENTAGEMDELEITE REAL," +
                "$COLUMN_FKCPF TEXT NOT NULL," +
                "FOREIGN KEY(${COLUMN_FKCPF}) REFERENCES ${TABLE_CLIENTE}(${COLUMN_CPF}))"

        db.execSQL(queryCreateTablePedidochocolate)
    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        val queryDropTableCliente = "DROP TABLE IF EXISTS $TABLE_CLIENTE"
        db.execSQL(queryDropTableCliente)
        onCreate(db)

        val queryDropTablePedidochocolate = "DROP TABLE IF EXISTS $TABLE_PEDIDOCHOCOLATE"
        db.execSQL(queryDropTablePedidochocolate)
        onCreate(db)
    }
}
