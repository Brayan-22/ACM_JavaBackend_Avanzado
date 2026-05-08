package com.acm.edd;

import com.acm.edd.dto.OrderRequest;
import com.acm.edd.messaging.consumer.InventoryConsumer;
import com.acm.edd.messaging.event.OrderCreatedEvent;
import com.acm.edd.messaging.publisher.OrderEventPublisher;
import com.acm.edd.model.Order;
import com.acm.edd.repository.OrderRepository;
import com.acm.edd.repository.ProcessedEventRepository;
import com.acm.edd.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class EddDemoApplicationTests {

    @MockitoBean
    private ConnectionFactory connectionFactory; // Evita que Spring intente conectar a RabbitMQ real al levantar el contexto de test

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Autowired
    private InventoryConsumer inventoryConsumer;

    @Autowired
    private ProcessedEventRepository processedEventRepository;

    @Test
    void testCreateOrderAndPublish() {
        // Preparar
        OrderRequest request = new OrderRequest();
        request.setCustomerId("c-1");
        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
        item.setProductId("P1");
        item.setQuantity(2);
        request.setItems(List.of(item));

        // Actuar
        Order order = orderService.createOrder(request);

        // Verificar Síncrono (Base de Datos)
        assertNotNull(order.getId());
        assertEquals("CREATED", order.getStatus());

        // Verificar Asíncrono (Publicador)
        // El RabbitTemplate debió ser llamado con el Exchange correcto
        verify(rabbitTemplate, times(1)).convertAndSend(eq("orders.exchange"), eq(""), any(OrderCreatedEvent.class));
    }

    @Test
    void testInventoryConsumerIdempotencia() throws InterruptedException {
        // Limpiamos BD
        processedEventRepository.deleteAll();

        // Creamos una orden en BD
        Order order = new Order();
        order.setCustomerId("test-user");
        order.setStatus("CREATED");
        order = orderRepository.save(order);

        String eventId = UUID.randomUUID().toString();
        OrderCreatedEvent event = new OrderCreatedEvent(eventId, order.getId(), "test-user", LocalDateTime.now());

        // 1. Primer envío: debe procesarlo, esperar 1s, guardar ProcessedEvent y cambiar a INVENTORY_RESERVED
        inventoryConsumer.consumeOrderCreated(event);

        Order processedOrder1 = orderRepository.findById(order.getId()).get();
        assertEquals("INVENTORY_RESERVED", processedOrder1.getStatus());
        assertTrue(processedEventRepository.existsByEventIdAndConsumerName(eventId, "INVENTORY_CONSUMER"));

        // 2. Segundo envío con IDEMPOTENCIA: cambiamos el estado manualmente para demostrar que NO lo toca más
        processedOrder1.setStatus("CHANGED_MANUALLY");
        orderRepository.save(processedOrder1);

        inventoryConsumer.consumeOrderCreated(event); // Debería salir rápido por el IF

        Order processedOrder2 = orderRepository.findById(order.getId()).get();
        assertEquals("CHANGED_MANUALLY", processedOrder2.getStatus(), "El status no debió cambiar por Idempotencia");
    }
}
