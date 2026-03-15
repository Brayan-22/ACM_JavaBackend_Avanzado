package acm.javaspring.order.client;

import acm.javaspring.shared.dto.inventory.InventoryCheckRequest;
import acm.javaspring.shared.dto.inventory.InventoryItemRequest;
import acm.javaspring.shared.dto.inventory.InventoryReserveRequest;
import acm.javaspring.shared.dto.inventory.InventoryResponse;
import acm.javaspring.shared.dto.order.OrderItemRequest;
import acm.javaspring.shared.dto.order.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(@Value("${services.inventory.base-url}") String inventoryBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(inventoryBaseUrl)
                .build();
    }

    public InventoryResponse check(OrderRequest orderRequest) {
        List<InventoryItemRequest> items = orderRequest.items().stream()
                .map(this::toInventoryItem)
                .toList();

        InventoryCheckRequest request = new InventoryCheckRequest(items);

        return restClient.post()
                .uri("/api/inventory/check")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(InventoryResponse.class);
    }

    public InventoryResponse reserve(OrderRequest orderRequest) {
        List<InventoryItemRequest> items = orderRequest.items().stream()
                .map(this::toInventoryItem)
                .toList();

        InventoryReserveRequest request = new InventoryReserveRequest(
                orderRequest.orderId(),
                items
        );

        return restClient.post()
                .uri("/api/inventory/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(InventoryResponse.class);
    }

    public InventoryResponse release(OrderRequest orderRequest) {
        List<InventoryItemRequest> items = orderRequest.items().stream()
                .map(this::toInventoryItem)
                .toList();

        InventoryReserveRequest request = new InventoryReserveRequest(
                orderRequest.orderId(),
                items
        );

        return restClient.post()
                .uri("/api/inventory/release")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(InventoryResponse.class);
    }

    private InventoryItemRequest toInventoryItem(OrderItemRequest item) {
        return new InventoryItemRequest(item.productId(), item.quantity());
    }
}
