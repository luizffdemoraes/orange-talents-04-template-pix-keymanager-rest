package br.com.zup.luiz.keymanager.shared

import br.com.zup.luiz.CarregaChavePixRequest
import br.com.zup.luiz.KeyManagerCarregaGrpcServiceGrpc
import br.com.zup.luiz.KeyManagerListaGrpcServiceGrpc
import br.com.zup.luiz.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController(val carregaChavePixClient: KeyManagerCarregaGrpcServiceGrpc.KeyManagerCarregaGrpcServiceBlockingStub,
                                val listaChavesPixClient: KeyManagerListaGrpcServiceGrpc.KeyManagerListaGrpcServiceBlockingStub)  {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun carrega(clienteId: UUID,
                pixId: UUID) : HttpResponse<Any> {

        LOGGER.info("[$clienteId] carrega chave pix por id: $pixId")
        val chaveResponse = carregaChavePixClient.carrega(
            CarregaChavePixRequest.newBuilder()
            .setPixId(CarregaChavePixRequest.FiltroPorPixId.newBuilder()
                .setClienteId(clienteId.toString())
                .setPixId(pixId.toString())
                .build()).
            build())

        return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
    }

    @Get("/pix/")
    fun lista(clienteId: UUID) : HttpResponse<Any> {

        LOGGER.info("[$clienteId] listando chaves pix")
        val pix = listaChavesPixClient.lista(
            ListaChavesPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build())

        // Aqui é omitido o it poderia ser colocado it -> ChavePixResponse(it)
        //quando esse map terminar de executar vai devolver uma lista / coleção / interavel / conjunto de chave pix
        val chaves = pix.chavesList.map { ChavePixResponse(it) }
        return HttpResponse.ok(chaves)
    }

}