package com.example.inventoryservice.kafka;

import com.example.inventoryservice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DltMonitorConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(DltMonitorConsumer.class);

    @KafkaListener(
            topics = "${app.kafka.topics.order-created-dlt}",
            groupId = "dlt-monitor"
    )
    public void consume(OrderCreatedEvent event) {
        log.error(
                "DLT message received: orderId={}, product={}, quantity={}",
                event.orderId(),
                event.product(),
                event.quantity()
        );
    }
}
