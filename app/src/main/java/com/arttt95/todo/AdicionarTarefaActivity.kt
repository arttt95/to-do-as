package com.arttt95.todo

import android.os.Bundle
import android.util.Log
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

        // Recuperar tarefa enviada de MainActivity
        val bundle = intent.getParcelableExtra<Tarefa>("tarefa")

        var tarefa: Tarefa? = null

        if(bundle != null) {
            tarefa = bundle
            binding.editTarefa.setText(bundle.descricao)
            binding.textAdicionar.text = "Atualizar descrição"
        }

        binding.btnSalvar.setOnClickListener {

            if(binding.editTarefa.text.isNotEmpty()) {

                if(tarefa != null) {
                    editar(tarefa)
                } else {
                    salvar()
                }

            } else {
                Toast.makeText(this, "Insira uma descrição", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun editar(tarefa: Tarefa) {
        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizacao = Tarefa(tarefa.idTarefa, descricao, "default")

        val tarefaDAO = TarefaDAO(this)
//        tarefaDAO.atualizar(tarefaAtualizacao)

        if(tarefaDAO.atualizar(tarefaAtualizacao)) {
            Toast.makeText(
                this,
                "Tarefa atualizada com sucesso",
                Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun salvar() {

        val descricao = binding.editTarefa.text.toString()

        val tarefa = Tarefa(-1, descricao, "default")

        val tarefaDAO = TarefaDAO(this)

        if(tarefaDAO.salvar(tarefa)) {
            Toast.makeText(
                this,
                "Tarefa inserida com sucesso",
                Toast.LENGTH_LONG).show()
            finish()
        }

    }

   /* private fun salvar() {

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

    }*/
}