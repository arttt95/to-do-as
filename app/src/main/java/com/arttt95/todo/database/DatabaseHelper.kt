package com.arttt95.todo.database

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    NOME_DB,
    null,
    VERSAO_DB

    ) {

    companion object {

        const val NOME_DB = "lista_tarefas.db"
        const val VERSAO_DB = 1
        const val NOME_TB_TAREFAS = "tarefas"
        const val COLUMN_ID_TAREFA = "idTarefa"
        const val COLUMN_DESCRICAO = "descricao"
        const val COLUMN_DATA_CADASTRO = "data_cadastro"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql =
            "CREATE TABLE IF NOT EXISTS " +
                "$NOME_TB_TAREFAS(" +
                    "$COLUMN_ID_TAREFA INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_DESCRICAO VARCHAR(70)," +
                    "$COLUMN_DATA_CADASTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso criar table -> DatabaseHelper")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro criar table -> DatabaseHelper")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}