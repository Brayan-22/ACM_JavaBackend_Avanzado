package acm.javaspring.inventory.store;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryInventoryStore {
    private final ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        reset();
    }

    public int getStock(String productId) {
        return stock.getOrDefault(productId, new AtomicInteger(0)).get();
    }

    public boolean hasEnoughStock(String productId, int requestedQuantity) {
        AtomicInteger current = stock.get(productId);
        return current != null && current.get() >= requestedQuantity;
    }

    public boolean reserve(String productId, int quantity) {
        AtomicInteger current = stock.get(productId);
        if (current == null) {
            return false;
        }

        while (true) {
            int available = current.get();
            if (available < quantity) {
                return false;
            }

            int updated = available - quantity;
            if (current.compareAndSet(available, updated)) {
                return true;
            }
        }
    }

    public void release(String productId, int quantity) {
        stock.compute(productId, (key, current) -> {
            if (current == null) {
                return new AtomicInteger(quantity);
            }
            current.addAndGet(quantity);
            return current;
        });
    }

    public void reset() {
        stock.clear();
        stock.put("P-100", new AtomicInteger(10));
        stock.put("P-200", new AtomicInteger(5));
        stock.put("P-300", new AtomicInteger(20));
        stock.put("HOT-ITEM", new AtomicInteger(3));
    }

    public Map<String, Integer> snapshot() {
        Map<String, Integer> result = new ConcurrentHashMap<>();
        stock.forEach((key, value) -> result.put(key, value.get()));
        return result;
    }
}
