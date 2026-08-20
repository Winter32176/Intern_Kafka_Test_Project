package com.example.inventoryservice.repository;

import com.example.inventoryservice.domain.ProcessedMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.UUID;

public interface ProcessedMessageRepository
        extends JpaRepository<ProcessedMessageEntity, UUID> {

    @Modifying
    @Query(
            value = """
                    INSERT INTO processed_messages (order_id, processed_at)
                    VALUES (:orderId, :processedAt)
                    ON CONFLICT (order_id) DO NOTHING
                    """,
            nativeQuery = true
    )
    int insertIfAbsent(
            @Param("orderId") UUID orderId,
            @Param("processedAt") Instant processedAt
    );
}