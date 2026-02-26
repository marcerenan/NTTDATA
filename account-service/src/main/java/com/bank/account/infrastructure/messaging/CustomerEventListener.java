package com.bank.account.infrastructure.messaging;

import com.bank.account.domain.model.CustomerEvent;
import com.bank.account.service.SyncCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventListener {
    private final SyncCustomerUseCase syncUseCase;

    @KafkaListener(topics = "customer-events")
    public void listen(CustomerEvent event) {
        // Al ser un listener de Spring Kafka tradicional, no devuelve un Mono.
        // Por eso usamos .subscribe() para ejecutar el flujo reactivo de guardado.
        syncUseCase.sync(event)
                .subscribe(
                        result -> System.out.println("Cliente sincronizado: " + event.getCustomerId()),
                        error -> System.err.println("Error sincronizando: " + error.getMessage())
                );
    }
}
