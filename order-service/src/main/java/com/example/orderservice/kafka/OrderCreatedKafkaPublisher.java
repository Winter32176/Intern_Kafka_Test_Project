package com.example.orderservice.kafka;

import com.example.orderservice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCreatedKafkaPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedKafkaPublisher.class);

    private final OrderEventProducer orderEventProducer;

    public OrderCreatedKafkaPublisher(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        orderEventProducer.publish(event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Failed to publish order-created event for orderId={}",
                                event.orderId(),
                                exception
                        );
                        return;
                    }

                    log.info(
                            "Published order-created event for orderId={}",
                            event.orderId()
                    );
                });
    }
}