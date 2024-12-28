package com.arttt95.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arttt95.todo.adapter.TarefaAdapter
import com.arttt95.todo.database.TarefaDAO
import com.arttt95.todo.databinding.ActivityMainBinding
import com.arttt95.todo.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, AdicionarTarefaActivity::class.java)

        tarefaAdapter = TarefaAdapter()

        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)



        with(binding) {

            fabAdicionar.setOnClickListener {

                startActivity(intent)

            }



        }
    }



    override fun onStart() {
        super.onStart()

        atualizarListaTarefas()

    }

    private fun atualizarListaTarefas() {

        val tarefaDAO = TarefaDAO(this)

        listaTarefas = tarefaDAO.listar()

        tarefaAdapter?.recarregarLista(listaTarefas)

        listaTarefas.forEach { tarefa ->

            Log.i("info_db", "ID: ${tarefa.idTarefa} | Descrição: ${tarefa.descricao} | Data: ${tarefa.dataCadastro}\n")

        }

    }

}