package com.arttt95.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.todo.database.TarefaDAO
import com.arttt95.todo.databinding.ActivityAdicionarTarefaBinding
import com.arttt95.todo.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {

            btnSalvar.setOnClickListener {

                salvar()

            }

        }

    }

    private fun salvar() {

        val descricao = binding.editTarefa.text.toString()

        if(!binding.editTarefa.text.isNullOrEmpty()) {
            val tarefa = Tarefa(-1, descricao, "default")

            val tarefaDAO = TarefaDAO(this)

            if(tarefaDAO.salvar(tarefa)) {
                Toast.makeText(this, "Tarefa inserida com sucesso", Toast.LENGTH_LONG).show()
                finish()
            }

        } else {
            Toast.makeText(this, "Insira uma descrição", Toast.LENGTH_LONG).show()
        }

    }
}