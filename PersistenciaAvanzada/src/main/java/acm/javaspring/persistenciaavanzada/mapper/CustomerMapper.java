package acm.javaspring.persistenciaavanzada.mapper;

import acm.javaspring.persistenciaavanzada.domain.Customer;
import acm.javaspring.persistenciaavanzada.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    //domain to entity
    CustomerEntity domainToEntity(Customer customer);

    //entity to domain
    Customer entityToDomain(CustomerEntity entity);

    //domain to DTOS

    //DTOS to domain
}
