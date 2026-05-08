package acm.javaspring.persistenciaavanzada.mapper;

import acm.javaspring.persistenciaavanzada.domain.Product;
import acm.javaspring.persistenciaavanzada.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // domain to entity
    ProductEntity domainToEntity(Product product);

    // entity to domain
    Product entityToDomain(ProductEntity entity);
}
