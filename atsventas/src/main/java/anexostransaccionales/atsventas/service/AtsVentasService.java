package anexostransaccionales.atsventas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import anexostransaccionales.atsventas.models.entities.AtsVentas;

public interface AtsVentasService {
    String importAtsVentasFromFile(MultipartFile file);

    List<AtsVentas> findAll();

    Optional<AtsVentas> findById(String id);

    AtsVentas create(AtsVentas atsVentas);

    Optional<AtsVentas> update(String id, AtsVentas atsVentas);

    boolean delete(String id);

}
