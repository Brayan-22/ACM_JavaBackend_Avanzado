package acm.javaspring.persistenciaavanzada.mapper;

import acm.javaspring.persistenciaavanzada.domain.OrderItem;
import acm.javaspring.persistenciaavanzada.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
uses = {
        ProductMapper.class
})
public interface OrderItemMapper {
    // domain to entity
    @Mapping(target = "purchaseOrder", ignore = true)
    OrderItemEntity domainToEntity(OrderItem domain);

    // entity to domain
    @Mapping(target = "purchaseOrder", ignore = true)
    OrderItem entityToDomain(OrderItemEntity entity);
}
