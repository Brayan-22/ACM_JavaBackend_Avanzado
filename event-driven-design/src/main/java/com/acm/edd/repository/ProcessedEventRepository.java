package com.acm.edd.repository;

import com.acm.edd.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Object> {
    
    boolean existsByEventIdAndConsumerName(String eventId, String consumerName);
}
