package com.example.jeffenhagen.models

class Pedidochocolate constructor(idChocolate: Int, tipoDeChocolate: String, tipoDeCastanha: String, porcentagemDeLeite: Float, fkCpf: String){

    var idChocolate: Int
    var tipoDeChocolate: String
    var tipoDeCastanha: String
    var porcentagemDeLeite: Float
    var fkCpf: String

    init{
        this.idChocolate = idChocolate
        this.tipoDeChocolate = tipoDeChocolate
        this.tipoDeCastanha = tipoDeCastanha
        this.porcentagemDeLeite = porcentagemDeLeite
        this.fkCpf = fkCpf
    }

    override fun toString(): String {
        return "Pedidochocolate(idChocolate=$idChocolate, tipoDeChocolate='$tipoDeChocolate', tipoDeCastanha='$tipoDeCastanha', porcentagemDeLeite=$porcentagemDeLeite, fkCpf='$fkCpf')"
    }
}
