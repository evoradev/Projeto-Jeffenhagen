package com.example.jeffenhagen.views

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.jeffenhagen.R
import com.example.jeffenhagen.dao.PedidochocolateDAO
import com.example.jeffenhagen.models.Pedidochocolate

class PedidosActivity : AppCompatActivity() {

    lateinit var clienteCpf: EditText
    lateinit var numeroPedido: EditText
    lateinit var tipoChocolate: EditText
    lateinit var tipoCastanha: EditText
    lateinit var porcentagemLeite: EditText
    lateinit var novo: Button
    lateinit var editar: Button
    lateinit var excluir: Button
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
        setContentView(R.layout.activity_pedidos)

        val dao = PedidochocolateDAO(this)

        clienteCpf = findViewById(R.id.et_fkcpf)
        numeroPedido = findViewById(R.id.et_idchocolate)
        tipoChocolate = findViewById(R.id.et_tipodechocolate)
        tipoCastanha = findViewById(R.id.et_tipodecastanha)
        porcentagemLeite = findViewById(R.id.et_porcentagemdeleite)
        novo = findViewById(R.id.bt_novoPedido)
        editar = findViewById(R.id.bt_editarPedido)
        excluir = findViewById(R.id.bt_excluirPedido)
        voltar = findViewById(R.id.bt_voltarPedido)


        novo.setOnClickListener {
            val pedido = Pedidochocolate(numeroPedido.text.toString().toInt(), tipoChocolate.text.toString(), tipoCastanha.text.toString(), porcentagemLeite.text.toString().toFloat(), clienteCpf.text.toString())
            if(dao.validateFkCpf(pedido.fkCpf)){
                dao.insertPedidochocolate(pedido)
                alertDialog("Novo pedido", "Criado !")
            }
            else{
                alertDialog("Erro de insercao", "O cliente nao existe")
             }
        }

        editar.setOnClickListener {
            val pedido = Pedidochocolate(numeroPedido.text.toString().toInt(), tipoChocolate.text.toString(), tipoCastanha.text.toString(), porcentagemLeite.text.toString().toFloat(), clienteCpf.text.toString())
            if(dao.validateFkCpf(pedido.fkCpf)){
                dao.updatePedidochocolate(pedido)
                alertDialog("Editar pedido", "Editado !")
            }
            else{
                alertDialog("Erro de edicao", "O cliente nao existe")
            }
        }

        excluir.setOnClickListener {
            val cpf = clienteCpf.text.toString()
            if(dao.validateFkCpf(cpf)){
                dao.deletePedidochocolate(numeroPedido.text.toString().toInt())
                alertDialog("Excluir pedido", "Excluido !")
            }
            else{
                alertDialog("Erro de delecao", "O cliente nao existe")
            }
        }

        voltar.setOnClickListener {
            this.finish()
        }

    }
}