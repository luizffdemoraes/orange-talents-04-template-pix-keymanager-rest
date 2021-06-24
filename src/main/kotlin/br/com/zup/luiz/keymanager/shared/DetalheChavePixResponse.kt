package br.com.zup.luiz.keymanager.shared

import br.com.zup.luiz.CarregaChavePixResponse
import br.com.zup.luiz.TipoDeConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected //Como não tem val ou var isso é um parametro comum não vira propriedade no Kotlin
class DetalheChavePixResponse(chaveResponse: CarregaChavePixResponse) {

    val pixId = chaveResponse.pixId
    val tipo = chaveResponse.chave.tipo
    val chave = chaveResponse.chave.chave

    //Passando a precisão da data eo fuso Horário relativo a chamada de função como só temos um parametro usamos o "it" ou qualquer outro nome
    val criadaEm = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

    val tipoConta = when (chaveResponse.chave.conta.tipo) {
        TipoDeConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoDeConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "NAO_RECONHECIDA"
    }

    //criamos um map recebendo uma lista ou var args do tipo Pair passando um chave e valor
    val conta = mapOf(Pair("tipo", tipoConta),
        Pair("instituicao", chaveResponse.chave.conta.instituicao),
        Pair("nomeDoTitular", chaveResponse.chave.conta.nomeDoTitular),
        Pair("cpfDoTitular", chaveResponse.chave.conta.cpfDoTitular),
        Pair("agencia", chaveResponse.chave.conta.agencia),
        Pair("numero", chaveResponse.chave.conta.numeroDaConta))
}