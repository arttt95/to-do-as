package com.arttt95.todo.database

import com.arttt95.todo.model.Tarefa

interface ITarefaDAO {

    fun salvar(tarefa: Tarefa) : Boolean
    fun atualizar(tarefa: Tarefa) : Boolean
    fun deletar(idTarefa: Int) : Boolean
    fun listar() : List<Tarefa>

}