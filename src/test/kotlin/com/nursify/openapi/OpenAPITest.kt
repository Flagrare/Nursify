package com.nursify.openapi

import lombok.SneakyThrows
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import java.nio.file.Files
import java.nio.file.Path

@DisplayName("[OpenAPI] Generator files")

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("qa")
class OpenAPITest(@Autowired private val rest: TestRestTemplate) {

    @Test
    @SneakyThrows
    @DisplayName("should generate the OpenAPI specification")
    fun generateOpenApiSpec() {
        val response = rest.getForEntity("/v3/api-docs", String::class.java)
        Assertions.assertTrue(response.statusCode.is2xxSuccessful, "Unexpected status code: " + response.statusCode)
        val path = Path.of("build/api/src/main/resources/openapi.yaml")

        Files.writeString(
            path,
            response.body
        )
    }
}
