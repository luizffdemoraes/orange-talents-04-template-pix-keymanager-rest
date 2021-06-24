package br.com.zup.luiz.keymanager.shared

import br.com.zup.luiz.CarregaChavePixRequest
import br.com.zup.luiz.KeyManagerCarregaGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController(val carregaChavePixClient: KeyManagerCarregaGrpcServiceGrpc.KeyManagerCarregaGrpcServiceBlockingStub)  {

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
}