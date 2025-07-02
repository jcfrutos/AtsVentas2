package anexostransaccionales.atsventas.service;

import org.springframework.web.multipart.MultipartFile;

public interface AtsVentasService {
    String importAtsVentasFromFile(MultipartFile file);

}
