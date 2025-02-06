package br.com.grupointeratlantica.cpl.app_postergacoes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                summary = "API feita exclusivamente para o Grupo Atlântica.",
                version = "v1",
                contact = @Contact(
                        name = "Marcos André",
                        email = "marcosdev2002@gmail.com"
                )
        )
)
public class OpenApiConfiguration {
}
