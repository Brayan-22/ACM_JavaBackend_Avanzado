# Layered Architecture (Arquitectura en Capas)

Este módulo muestra el enfoque clásico Top-Down o de N capas. Es el diseño más estandarizado en el mundo corporativo tradicional.

## Concepto
El sistema se divide en capas lógicas de responsabilidad decreciente:
1. **Presentation Layer (`presentation`)**: Expone las interfaces públicas (Controllers, DTOs de entrada y salida). No sabe nada de persistencia, solo delega en Application.
2. **Application Layer (`application`)**: Orquesta el flujo de negocio (Casos de uso y transaccionalidad). Coordina el uso de Repositorios (Infraestructura) con Entidades (Dominio).
3. **Domain Layer (`domain`)**: Contiene las Entidades del sistema (`Product`, `Order`, `Inventory`). A diferencia del estilo *Bad Example*, estas entidades tienen **modelado rico**: comportamiento válido, métodos que protegen el estado interno y constructores de paquete.
4. **Infrastructure Layer (`infrastructure`)**: Repositorios de acceso a base de datos.

## Mejoras Respecto a 'Bad Example'
- **Separación de Responsabilidades**: El Controlador es ligero (`Thin Controller`), solo procesa HTTP y JSON.
- **DTOs explícitos**: Se aísla el modelo de datos de la interfaz HTTP usando `OrderCreateRequest` y `OrderResponse`. No más `@JsonIgnore` raros en el Dominio.
- **Rich Domain Model (Modelo de Dominio Rico)**: La lógica que descuenta stock está dentro de `Inventory.decrease()`. La lógica que calcula precios y arma items está en `Order.addItem()`.
- **Transaccionalidad clara**: El `@Transactional` vive en la capa Application.

## Problema de este enfoque (Por qué miramos hacia Hexagonal)
En este enfoque, el dominio sigue violando *Clean Architecture* levemente porque `domain` está contaminado con anotaciones transaccionales y de infraestructura de base de datos (`@Entity`, `@Table`). Esto hace difícil mover el modelo de negocio o probarlo sin arrastrar las dependencias y constraints de JPA.

## Diagrama

```mermaid
graph TD
    classDef layers fill:#f9f,stroke:#333;
    
    P[Presentation Layer - Controllers] --> A[Application Layer - Services]
    A --> D[Domain Layer - Rich Entities]
    A --> I[Infrastructure Layer - Repositories]
    I --> DB[(Base de Datos H2)]
    
    subgraph Spring Context
      P
      A
      I
    end
    
    subgraph Pure Logic (mostly)
      D
    end
```
