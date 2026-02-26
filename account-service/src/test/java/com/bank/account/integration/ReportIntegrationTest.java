package com.bank.account.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ReportIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAccountStatementReport() {

        webTestClient.get()
                .uri("/reports/1?startDate=2024-01-01&endDate=2024-12-31")
                .exchange()
                .expectStatus().isOk();
    }
}
