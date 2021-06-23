package br.com.zup.luiz.keymanager.shared.grpc

import br.com.zup.luiz.KeyManagerCarregaGrpcServiceGrpc
import br.com.zup.luiz.KeyManagerListaGrpcServiceGrpc
import br.com.zup.luiz.KeyManagerRegistraGrpcServiceGrpc
import br.com.zup.luiz.KeyManagerRemoveGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory //Responsavel por criar as chamadas dos objetos para conseguir injetar
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    /*
        Nessas funções estamos solicitando o retorno de um novo Client block para chamada GRPC
        onde o metodo newBlockingStub espera receber um channel, esse channel é do tipo ManagedChannel
        onde fala que ele é uma via de comunicação GRPC onde essa via é um KeyManager nele estamos informando qual é a URI
        do recurso que vamos querer apontar. Para isso e necessario apontar o endereço no application.yml
     */


    @Singleton //declaramos uma função eo que vai retornar essa e uma chamada normal
    fun registraChave(): KeyManagerRegistraGrpcServiceGrpc.KeyManagerRegistraGrpcServiceBlockingStub {
        return KeyManagerRegistraGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton //declaramos uma função eo que vai retornar essa e uma chamada reduzida no kotlin onde é omitido o tipo de retorno
    fun deletaChave() = KeyManagerRemoveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = KeyManagerCarregaGrpcServiceGrpc.newBlockingStub(channel)
}


