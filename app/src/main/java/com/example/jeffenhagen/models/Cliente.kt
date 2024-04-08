package com.example.jeffenhagen.models

class Cliente constructor(cpf: String, nome: String, telefone: String, ativo: Boolean) {
    var cpf: String
    var nome: String
    var telefone: String
    var ativo: Boolean

    init {
        this.cpf = cpf
        this.nome = nome
        this.telefone = telefone
        this.ativo = true
    }

    override fun toString(): String {
        return "Cliente(cpf='$cpf', nome='$nome', telefone='$telefone', ativo=$ativo)"
    }
}