package acm.javaspring.persistenciaavanzada.mapper;

import acm.javaspring.persistenciaavanzada.domain.PurchaseOrder;
import acm.javaspring.persistenciaavanzada.persistence.entity.PurchaseOrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
uses = {
        CustomerMapper.class, OrderItemMapper.class
})
public interface PurchaseOrderMapper {
    // domain to entity
    PurchaseOrderEntity domainToEntity(PurchaseOrder purchaseOrder);

    // entity to domain
    PurchaseOrder entityToDomain(PurchaseOrderEntity entity);

    @AfterMapping
    default void linkItems(PurchaseOrder source, @MappingTarget PurchaseOrderEntity target) {
        if (source == null || target == null || target.getItems() == null) {
            return;
        }
        target.getItems().forEach(item -> item.setPurchaseOrder(target));
    }

    @AfterMapping
    default void linkItems(PurchaseOrderEntity source, @MappingTarget PurchaseOrder target) {
        if (source == null || target == null || target.getItems() == null) {
            return;
        }
        target.getItems().forEach(item -> item.setPurchaseOrder(target));
    }
}
