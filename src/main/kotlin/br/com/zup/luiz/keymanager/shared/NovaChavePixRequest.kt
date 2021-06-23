package br.com.zup.luiz.keymanager

import br.com.caelum.stella.validation.CPFValidator
import br.com.zup.luiz.RegistraChavePixRequest
import br.com.zup.luiz.TipoDeChave
import br.com.zup.luiz.TipoDeConta
import br.com.zup.luiz.keymanager.shared.validations.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(@field:NotNull val tipoDeConta: TipoDeContaRequest?,
                          @field:Size(max = 77) val chave: String?,
                          @field:NotNull val tipoDeChave: TipoDeChaveRequest?) {

    /*
        Aqui nos recebemos os tipos que vem do corpo da requisição o RegistraChavePixRequest ele é do grpc
        e nessa classe usamos um DTO de entrada que será convertido para outro. Nos não usamos o Request que é um
        do GRPC justamente pois essa classe é criada automaticamente pelos interpretadores do protobuf, não temos controle
        sobre a criação dela caso nos tivessemos interesse em fazer validações nela no processo de serialização e descerialização
        na transformação de um objeto para JSON e vice versa. Possivelmente iria ser quebrada e evitamos o acoplamento direto
        fazendo essa separação visando separação e proceção para camada Rest evitando problemas de validação.
     */

    fun paraModeloGrpc(clienteId: UUID): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoDeConta(tipoDeConta?.atributoGrpc ?: TipoDeConta.UNKNOWN_TIPO_CONTA)
            .setTipoDeChave(tipoDeChave?.atributoGrpc ?: TipoDeChave.UNKNOWN_TIPO_CHAVE)
            .setChave(chave ?: "")
            .build()
    }
}

enum class TipoDeChaveRequest(val atributoGrpc: TipoDeChave) {

    CPF(TipoDeChave.CPF) {

        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }

            return CPFValidator(false)
                .invalidMessagesFor(chave)
                .isEmpty()
        }

    },

    CELULAR(TipoDeChave.CELULAR) {
        override fun valida(chave: String?): Boolean {

            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    EMAIL(TipoDeChave.EMAIL) {

        override fun valida(chave: String?): Boolean {

            if (chave.isNullOrBlank()) {
                return false
            }
            //Não esta funcionando desse jeito
//            return EmailValidator().run {
//                initialize(null)
//                isValid(chave, null)
//            }

            //Desse jeito ta filé
            return chave.matches("^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.?([a-z]+)?\$".toRegex())

        }
    },

    ALEATORIA(TipoDeChave.ALEATORIA) {
        override fun valida(chave: String?) = chave.isNullOrBlank() // não deve se preenchida
    };

    abstract fun valida(chave: String?): Boolean
}

// Aqui é feito um mapeamento Um para Um
enum class TipoDeContaRequest(val atributoGrpc: TipoDeConta) {

    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),

    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA)
}