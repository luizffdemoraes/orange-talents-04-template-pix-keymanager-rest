package br.com.zup.luiz.keymanager.shared.validations

import br.com.zup.luiz.keymanager.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.reflect.KClass


@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ ValidPixKeyValidator::class ])
annotation class ValidPixKey(
    val message: String = "chave Pix inválida (\${validatedValue.tipoDeChave})", // needs module: io.micronaut.beanvalidation:micronaut-hibernate-validator
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

/*
    Aqui implementamos com ConstraintValidator essa é uma interface do Micronaut
    Essa assinatura e recebemos a anotação e o objeto alvo de validação e sobrescrevemos
    a anotação de isValid recebemos o contexto da anotação fazemos uma verificação caso venha null
    retorna falso, caso contrario dizemos você que é o objeto alvo da validação pega o tipo de chave
    e pede para validar o valor da chave todos metodos Enum no NovaChavePixRequest tem uma validação

 */

@Singleton
class ValidPixKeyValidator: ConstraintValidator<ValidPixKey, NovaChavePixRequest> {

    override fun isValid(
        value: NovaChavePixRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {

        if (value?.tipoDeChave == null) {
            return false
        }

        return value.tipoDeChave.valida(value.chave)
    }

}