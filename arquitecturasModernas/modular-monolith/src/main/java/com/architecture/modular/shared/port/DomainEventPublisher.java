package com.architecture.modular.shared.port;

public interface DomainEventPublisher {
    void publish(Object event);
}
