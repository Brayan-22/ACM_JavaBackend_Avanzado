package com.acm.edd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProcessedEventId.class)
public class ProcessedEvent {

    @Id
    private String eventId;

    @Id
    private String consumerName;

    private LocalDateTime processedAt;
}

class ProcessedEventId implements Serializable {
    private String eventId;
    private String consumerName;

    // Default constructor, equals, and hashCode are required for composite keys.
    // Lombok @Data or standard implementations generally work. 
    public ProcessedEventId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessedEventId that = (ProcessedEventId) o;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        return consumerName != null ? consumerName.equals(that.consumerName) : that.consumerName == null;
    }

    @Override
    public int hashCode() {
        int result = eventId != null ? eventId.hashCode() : 0;
        result = 31 * result + (consumerName != null ? consumerName.hashCode() : 0);
        return result;
    }
}
