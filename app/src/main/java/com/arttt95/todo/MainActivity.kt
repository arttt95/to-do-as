package com.arttt95.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.todo.database.TarefaDAO
import com.arttt95.todo.databinding.ActivityMainBinding
import com.arttt95.todo.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()

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



            with(binding) {
            fabAdicionar.setOnClickListener {

                startActivity(intent)

            }



        }
    }

    override fun onStart() {
        super.onStart()

        val tarefaDAO = TarefaDAO(this)

        listaTarefas = tarefaDAO.listar()

        listaTarefas.forEach { tarefa ->

            Log.i("info_db", "ID: ${tarefa.idTarefa} | Descrição: ${tarefa.descricao} | Data: ${tarefa.dataCadastro}\n")

        }

    }
}