package com.arttt95.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) }
        )

        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)

        binding.fabAdicionar.setOnClickListener {

            startActivity(intent)

        }

    }

    private fun editar(tarefa: Tarefa) {

        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)

    }

    private fun confirmarExclusao(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Consfirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim"){ _ ,_ ->

            val tarefaDAO = TarefaDAO(this)

            if(tarefaDAO.deletar(id)) {
                atualizarListaTarefas()
                Toast.makeText(this, "Sucesso ao remover tarefa", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro ao remover tarefa", Toast.LENGTH_LONG).show()
            }

        }

        alertBuilder.setNegativeButton("Não"){ _ ,_ ->}

        alertBuilder.create().show()

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