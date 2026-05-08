package com.architecture.hexagonal.infrastructure.adapter.out.persistence;

import com.architecture.hexagonal.application.port.out.OrderRepositoryPort;
import com.architecture.hexagonal.domain.model.Order;
import com.architecture.hexagonal.domain.model.OrderItem;
import com.architecture.hexagonal.domain.model.OrderStatus;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.OrderItemJpaEntity;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import com.architecture.hexagonal.infrastructure.adapter.out.persistence.repository.SpringDataOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    public OrderPersistenceAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setUserId(order.getUserId());
        entity.setStatus(order.getStatus().name());
        entity.setTotalAmount(order.getTotalAmount());
        
        List<OrderItemJpaEntity> itemEntities = order.getItems().stream().map(item -> {
            OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
            itemEntity.setId(item.getId());
            itemEntity.setProductId(item.getProductId());
            itemEntity.setPrice(item.getPrice());
            itemEntity.setQuantity(item.getQuantity());
            return itemEntity;
        }).collect(Collectors.toList());

        entity.setItems(itemEntities);
        
        OrderJpaEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    private Order mapToDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
            .map(item -> new OrderItem(item.getId(), item.getProductId(), item.getQuantity(), item.getPrice()))
            .collect(Collectors.toList());
            
        return new Order(
            entity.getId(), 
            entity.getUserId(), 
            OrderStatus.valueOf(entity.getStatus()), 
            entity.getTotalAmount(), 
            items
        );
    }
}
