package com.arttt95.todo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.arttt95.todo.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val context = context
    private val writer = DatabaseHelper(context).writableDatabase
    private val reader = DatabaseHelper(context).readableDatabase


    override fun salvar(tarefa: Tarefa): Boolean {

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
            Log.i("info_db", "Err: SALVAR tarefa -> TarefaDAO")
            return false
        }

        return true

    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        TODO("Not yet implemented")
    }

    override fun deletar(idTarefa: Int): Boolean {

        val condicao = "${DatabaseHelper.COLUMN_ID_TAREFA} = ?"
        val args = arrayOf(idTarefa.toString())

        try {

            writer.delete(
                DatabaseHelper.NOME_TB_TAREFAS,
                condicao,
                args
            )
            Log.i("info_db", "Sucesso: DELET -> TarefaDAO")

        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Err: DELET -> TarefaDAO")
            return false
        }

        return true

    }

    override fun listar(): List<Tarefa> {

        val listaTarefas = mutableListOf<Tarefa>()

        val sql =
            "SELECT " +
                "${DatabaseHelper.COLUMN_ID_TAREFA}," + // id_tarefa
                "${DatabaseHelper.COLUMN_DESCRICAO}," + // descricao

                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUMN_DATA_CADASTRO}) " + // data
                "AS ${DatabaseHelper.COLUMN_DATA_CADASTRO} " +

            "FROM " +
                "${DatabaseHelper.NOME_TB_TAREFAS};"

        val cursor = reader.rawQuery(sql, null)

        try {

            if(cursor.moveToFirst()) {

                do {
                    val indiceId = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_TAREFA)
                    val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRICAO)
                    val indiceDataCadastro = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA_CADASTRO)

                    if (indiceId != -1 && indiceDescricao != -1 && indiceDataCadastro != -1) {

                        val idTarefa = cursor.getInt(indiceId)
                        val descricao = cursor.getString(indiceDescricao)
                        val dataCadastro = cursor.getString(indiceDataCadastro)

                        val tarefa = Tarefa(idTarefa, descricao, dataCadastro)

                        listaTarefas.add(tarefa)

                    } else {
                        Log.i("info_db", "Err: inst tarefa LISTAR -> TarefaDAO")
                    }
                } while (cursor.moveToNext())

            }

            Log.i("info_db", "Sucesso: LISTAR tarefa -> TarefaDAO")

        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Err: LISTAR tarefa -> TarefaDAO: ${e.message}")
        } finally {
            cursor.close()
        }

        return listaTarefas

    }
}