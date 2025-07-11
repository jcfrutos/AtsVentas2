package anexostransaccionales.atsventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import anexostransaccionales.atsventas.models.entities.AtsVentas;
import anexostransaccionales.atsventas.service.AtsVentasService;
import anexostransaccionales.atsventas.service.EmailService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/atsventas")
public class AtsVentasController {
    @Autowired
    private AtsVentasService atsVentasService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/import")
    public ResponseEntity<?> importAtsVentas(@RequestParam("file") MultipartFile file) {
        String result = atsVentasService.importAtsVentasFromFile(file);
        if (result.startsWith("el archivo xml debe contener un solo nodo")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<AtsVentas>> getAllAtsVentas() {
        emailService.enviarCorreo(
            "jcfrutos@utpl.edu.ec",
            "AtsVentas - Consulta de datos",
            "Se ha realizado una consulta a los datos de AtsVentas."
        );
            
        return ResponseEntity.ok(atsVentasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtsVentas> getAtsVentasById(@PathVariable String id) {
        return atsVentasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AtsVentas> createAtsVentas(@RequestBody AtsVentas atsVentas) {
        AtsVentas save = atsVentasService.create(atsVentas);
        return ResponseEntity.status(201).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtsVentas> updateAtsVentas(@PathVariable String id, @RequestBody AtsVentas updated) {
        return atsVentasService.update(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtsVentas(@PathVariable String id) {
        if (atsVentasService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}