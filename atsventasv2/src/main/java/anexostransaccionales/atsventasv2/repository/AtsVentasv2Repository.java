package anexostransaccionales.atsventasv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import anexostransaccionales.atsventasv2.models.entities.AtsVentasv2;

public interface AtsVentasv2Repository extends JpaRepository<AtsVentasv2, Long> {
    // Additional query methods can be defined here if needed
}
