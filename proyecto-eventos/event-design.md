# Definición de eventos para el proyecto

## Formato

Todos los eventos se representan en JSON y deben mantener una estructura fija e inmutable.

## Flujo de eventos

1. `OrderPlaced`: emitido por `order-service` cuando un cliente crea un pedido.
2. `StockReserved`: emitido por `inventory-service` si hay stock suficiente.
3. `OutOfStock`: emitido cuando no hay stock disponible.
4. `PaymentProcessed`: emitido por `payment-service` cuando el pago es exitoso.
5. `PaymentFailed`: emitido cuando el pago falla.
6. `StockReleased`: emitido por `inventory-service` como compensación cuando un pago falla.
7. `ShipmentScheduled`: emitido por `shipping-service` al programar el envío.

## Estructura base del payload

```json
{
    "eventType": "OrderPlaced",
    "orderId": "uuid",
    "timestamp": "2026-05-02T10:15:30Z",
    "data": {
        "...": "..."
    }
}
```

## Convenciones

- `eventType`: nombre del evento.
- `orderId`: identificador único del pedido.
- `timestamp`: fecha y hora en formato ISO-8601.
- `data`: objeto con la información específica del evento.
