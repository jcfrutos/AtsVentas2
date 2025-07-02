package anexostransaccionales.atsventas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import anexostransaccionales.atsventas.models.entities.AtsVentas;

public interface AtsVentasRepository extends MongoRepository<AtsVentas, String> {
    
}
