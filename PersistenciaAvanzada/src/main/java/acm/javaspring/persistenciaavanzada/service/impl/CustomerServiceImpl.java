package acm.javaspring.persistenciaavanzada.service.impl;

import acm.javaspring.persistenciaavanzada.domain.Customer;
import acm.javaspring.persistenciaavanzada.mapper.CustomerMapper;
import acm.javaspring.persistenciaavanzada.persistence.repository.CustomerJpaRepository;
import acm.javaspring.persistenciaavanzada.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper mapper;

    @Override
    public Optional<Customer> findById(Long id) {
        var entity = customerJpaRepository.findById(id);
        return entity.map(mapper::entityToDomain);
    }

    @Override
    public List<Customer> findAll(Pageable pageable) {
        return customerJpaRepository.findAll(pageable).stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    public Customer save(Customer domain) {
        var entity = mapper.domainToEntity(domain);
        var savedEntity = customerJpaRepository.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> update(Long id, Customer entity) {
        if (customerJpaRepository.existsById(id)) {
            entity.setId(id);
            var updatedEntity = customerJpaRepository.save(mapper.domainToEntity(entity));
            return Optional.of(mapper.entityToDomain(updatedEntity));
        }
        return Optional.empty();
    }
}
