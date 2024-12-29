package com.arttt95.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arttt95.todo.databinding.ItemTarefaBinding
import com.arttt95.todo.model.Tarefa

class TarefaAdapter(
    val onClickExcluir: (Int) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> (){

    private var listaTarefas: List<Tarefa> = emptyList()

    fun recarregarLista(listaTarefas: List<Tarefa>) {
        this.listaTarefas = listaTarefas
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        private val binding : ItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind(tarefa: Tarefa) {
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCadastro

            binding.btnExcluir.setOnClickListener{
                onClickExcluir(tarefa.idTarefa)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarefaAdapter.TarefaViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemTarefaBinding = ItemTarefaBinding.inflate(
            layoutInflater,
            parent, // Esse parâmetro
            false // Juntamente com esse são responsáveis por utilizar todo o espaço do layout
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TarefaAdapter.TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.bind(tarefa)
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

}