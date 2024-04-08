package com.example.jeffenhagen.models

class Visualizer constructor(cpf: String, nome: String, telefone: String, idChocolate: Int, tipoDeChocolate: String, tipoDeCastanha: String, porcentagemDeLeite: Float){

    var cpf: String
    var nome: String
    var telefone: String
    var idChocolate: Int
    var tipoDeChocolate: String
    var tipoDeCastanha: String
    var porcentagemDeLeite: Float

    init {
        this.cpf = cpf
        this.nome = nome
        this.telefone = telefone
        this.idChocolate = idChocolate
        this.tipoDeChocolate = tipoDeChocolate
        this.tipoDeCastanha = tipoDeCastanha
        this.porcentagemDeLeite = porcentagemDeLeite
    }

    override fun toString(): String {
        return "Cliente:\n[Cpf='$cpf', Nome='$nome', Telefone='$telefone']\nPedido:\n[Chocolate=$idChocolate, Tipo='$tipoDeChocolate', Castanha='$tipoDeCastanha', Porcentagem de Leite=$porcentagemDeLeite)]"
    }
}
