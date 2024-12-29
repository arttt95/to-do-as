package com.arttt95.todo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarefa(
    val idTarefa: Int,
    val descricao: String,
    val dataCadastro: String,
) : Parcelable