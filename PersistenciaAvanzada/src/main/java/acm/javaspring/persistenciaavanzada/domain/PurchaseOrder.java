package acm.javaspring.persistenciaavanzada.domain;

import acm.javaspring.persistenciaavanzada.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseOrder {
    private Long id;
    private Customer customer;
    private OrderStatus status;
    private BigDecimal total;
    private List<OrderItem> items;
}
