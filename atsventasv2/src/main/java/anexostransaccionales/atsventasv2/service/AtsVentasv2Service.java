package anexostransaccionales.atsventasv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import anexostransaccionales.atsventasv2.models.entities.AtsVentasv2;

public interface AtsVentasv2Service {
    String importAtsVentasv2FromFile(MultipartFile file);
    
    List<AtsVentasv2> findAll();
    Optional<AtsVentasv2> findById(Long id);
    AtsVentasv2 create(AtsVentasv2 atsVentasv2);
    AtsVentasv2 update(Long id, AtsVentasv2 atsVentasv2);
    void delete(Long id);
}