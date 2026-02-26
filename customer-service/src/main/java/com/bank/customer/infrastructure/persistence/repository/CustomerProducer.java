package com.bank.customer.infrastructure.persistence.repository;

import com.bank.customer.domain.model.CustomerEvent;
import com.bank.customer.domain.repository.CustomerExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerProducer implements CustomerExternalPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "customer-events";


    @Override
    public Mono<Void> notifyCustomerChange(CustomerEvent event) {
        return Mono.fromRunnable(() -> {
            kafkaTemplate.send(TOPIC, event.getCustomerId().toString(), event);
        }).then();
    }

    @Override
    public Mono<Void> notifyCustomerChange(String action, Long customerId) {
        CustomerEvent event = new CustomerEvent();
        event.setCustomerId(customerId);
        event.setAction(action);
        return Mono.fromRunnable(() -> {
            kafkaTemplate.send(TOPIC, event.getCustomerId().toString(), event);
        }).then();
    }
}
