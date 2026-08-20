package org.example.notificationservice.kafka;

import org.example.notificationservice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedNotificationConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedNotificationConsumer.class);

    @KafkaListener(topics = "${app.kafka.topics.order-created}")
    public void consume(OrderCreatedEvent event) {
        log.info(
                "Email sent to customer: customerId={}, orderId={}, product={}, quantity={}",
                event.customerId(),
                event.orderId(),
                event.product(),
                event.quantity()
        );
    }
}