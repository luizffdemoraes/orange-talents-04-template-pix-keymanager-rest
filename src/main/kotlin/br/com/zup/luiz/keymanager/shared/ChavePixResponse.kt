package br.com.zup.luiz.keymanager.shared

import br.com.zup.luiz.ListaChavesPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ChavePixResponse(chavePix: ListaChavesPixResponse.ChavePix) {

    //Aqui é mapeado cada um dos campos que estão na chave pix para um atributo que vai ser retornado
    val id = chavePix.pixId
    val chave = chavePix.chave
    val tipo = chavePix.tipo
    val tipoDeConta = chavePix.tipoDeConta
    val criadaEm = chavePix.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}