package com.arttt95.todo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.arttt95.todo.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val context = context
    private val writer = DatabaseHelper(context).writableDatabase
    private val reader = DatabaseHelper(context).readableDatabase


    override fun salvar(tarefa: Tarefa): Boolean {

//        val descricao =

        val valores = ContentValues()
        valores.put(DatabaseHelper.COLUMN_DESCRICAO, tarefa.descricao)

        try {

            writer.insert(
                DatabaseHelper.NOME_TB_TAREFAS,
                null,
                valores
            )

            Log.i("info_db", "Sucesso: SALVAR tarefa -> TarefaDAO")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro: SALVAR tarefa -> TarefaDAO")
            return false
        }

        return true

    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        TODO("Not yet implemented")
    }

    override fun deletar(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(): List<Tarefa> {
        TODO("Not yet implemented")
    }
}