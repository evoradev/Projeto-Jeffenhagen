package com.example.jeffenhagen.views

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.jeffenhagen.R
import com.example.jeffenhagen.models.Cliente
import com.example.jeffenhagen.dao.ClienteDAO

class ClientesActivity : AppCompatActivity() {

    lateinit var cpf: EditText
    lateinit var nome: EditText
    lateinit var telefone: EditText
    lateinit var novo: Button
    lateinit var editar: Button
    lateinit var deletar: Button
    lateinit var voltar: Button

    fun alertDialog(titulo: String, mensagem: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        val dao: ClienteDAO = ClienteDAO(this)

        cpf = findViewById(R.id.et_cpf)
        nome = findViewById(R.id.et_nome)
        telefone = findViewById(R.id.et_telefone)
        novo = findViewById(R.id.bt_novoCliente)
        editar = findViewById(R.id.bt_editarCliente)
        deletar = findViewById(R.id.bt_deletarCliente)
        voltar = findViewById(R.id.bt_voltar)

        novo.setOnClickListener {
            val cliente = Cliente(cpf.text.toString(), nome.text.toString(), telefone.text.toString(), true)
            if(cliente.cpf.isNotBlank() && dao.findOneByIdCliente(cliente.cpf) == null) {
                dao.insertCliente(cliente)
            }
            else {
                alertDialog("Erro de insercao", "O valor de cpf e nulo ou ja existe")
            }
        }

        editar.setOnClickListener{
            val cliente = Cliente(cpf.text.toString(), nome.text.toString(), telefone.text.toString(), true)
            if(cliente.cpf.isNotBlank() && dao.findOneByIdCliente(cliente.cpf) != null) {
                dao.updateCliente(cliente)
            }
            else {
                alertDialog("Erro de atualizacao", "O valor de cpf e nulo ou nao existe")
            }
        }

        deletar.setOnClickListener {
            val cliente = Cliente(cpf.text.toString(), nome.text.toString(), telefone.text.toString(), true)
            if(cliente.cpf.isNotBlank() && dao.findOneByIdCliente(cliente.cpf) != null) {
                dao.deleteCliente(cpf.text.toString())
            }
            else {
                alertDialog("Erro de delecao", "O valor de cpf e nulo ou nao existe")
            }
        }

        voltar.setOnClickListener {
            this.finish()
        }
    }

}