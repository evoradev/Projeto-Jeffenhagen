package com.example.jeffenhagen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.jeffenhagen.views.ClientesActivity
import com.example.jeffenhagen.views.PedidosActivity
import com.example.jeffenhagen.models.Visualizer
import com.example.jeffenhagen.dao.VisualizerDAO

import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var clientes: Button
    lateinit var pedidos: Button
    lateinit var buscar: Button
    lateinit var backup: Button
    lateinit var buscarBackup: Button
    lateinit var buscarPorCpf: EditText
    lateinit var buscarPorNumero: EditText
    lateinit var listView: ListView
    lateinit var pedidosAdapter: ArrayAdapter<Visualizer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dao = VisualizerDAO(this)
        val queryFindAll = dao.getAll()

        buscarPorCpf = findViewById(R.id.et_cpfCli)
        buscarPorNumero = findViewById(R.id.et_numPedido)
        clientes = findViewById(R.id.bt_clientes)
        pedidos = findViewById(R.id.bt_pedidos)
        buscar = findViewById(R.id.bt_buscarPedido)
        backup = findViewById(R.id.bt_backup)
        buscarBackup = findViewById(R.id.bt_returnBackup)
        listView = findViewById(R.id.lv_finder)

        pedidosAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, queryFindAll)
        listView.adapter = pedidosAdapter

        clientes.setOnClickListener {
            val intent = Intent(this, ClientesActivity::class.java)
            startActivity(intent)
        }

        pedidos.setOnClickListener {
            val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)
        }

        buscar.setOnClickListener {
            if (buscarPorCpf.text.isNotEmpty() && buscarPorNumero.text.isEmpty()) {
                val queryGetOneByCpf = dao.getOneByCpf(buscarPorCpf.text.toString())
                pedidosAdapter.clear()
                pedidosAdapter.addAll(queryGetOneByCpf)
                pedidosAdapter.notifyDataSetChanged()
            }
            if (buscarPorNumero.text.isNotEmpty()) {
                val queryGetOneById = dao.getOneById(buscarPorNumero.text.toString().toInt())
                pedidosAdapter.clear()
                pedidosAdapter.addAll(queryGetOneById)
                pedidosAdapter.notifyDataSetChanged()
            }
            if (buscarPorCpf.text.isNotEmpty() && buscarPorNumero.text.isNotEmpty()) {
                val queryGetOneByCpf = dao.getOneByCpf(buscarPorCpf.text.toString())
                pedidosAdapter.clear()
                pedidosAdapter.addAll(queryGetOneByCpf)
                pedidosAdapter.notifyDataSetChanged()
                pedidosAdapter.notifyDataSetChanged()
            }
        }


        //backup.isEnabled = isExternalStorageAvailable && !isExternalStorageReadOnly

        backup.setOnClickListener {
            val backupData = dao.getAll()
            val backupFolder = File(this.filesDir, "backup")
            if (!backupFolder.exists()) {
                backupFolder.mkdirs()
            }

            val backupFile = File(backupFolder, "latest_backup.txt")
            backupFile.writeText(backupData.joinToString(separator = "\n"))
        }

        buscarBackup.setOnClickListener {
                val backupFolder = File(this.filesDir, "backup")
                val backupFile = File(backupFolder, "latest_backup.txt")

                if (backupFile.exists()) {
                    val backupData = backupFile.readText()
                    Log.i("DatabaseBackup", backupData)
                } else {
                    Log.i("DatabaseBackup", "No backup file found.")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val dao = VisualizerDAO(this)
        val queryFindAll = dao.getAll()
        pedidosAdapter.clear()
        pedidosAdapter.addAll(queryFindAll)
        pedidosAdapter.notifyDataSetChanged()
    }

    //verificar
    /*
    val isExternalStorageReadOnly : Boolean get() {
        var readOnlyStorage = false
        val externalStorage = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED_READ_ONLY == externalStorage){
            readOnlyStorage = true
        }
        return readOnlyStorage
    }

    //verificar
    val isExternalStorageAvailable : Boolean get() {
        var storageAvailable = false
        val externalStorage = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == externalStorage) {
            storageAvailable = true
        }
        return storageAvailable
    }

     */
}

