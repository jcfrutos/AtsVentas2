package anexostransaccionales.atsventas.models.entities;
import org.springframework.data.annotation.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "atsventas")
@Getter
@Setter
public class AtsVentas {
    
    @Id
    private String id;
    private String tipoIdentificacionCliente; // VARCHAR(2)
    private String noIdentificacionCliente; // VARCHAR(13)
    private String parteRelacionada; // VARCHAR(2)
    private String tipoDeCliente; // VARCHAR(2)
    private String razonSocialCliente; // VARCHAR(500)
    private String codigoTipoComprobante; // VARCHAR(3)
    private String tipoDeEmision; // VARCHAR(1)
    private String noSerieComprobanteVentaEstablecimiento; // VARCHAR(3)
    private String noSerieComprobanteVentaPuntoEmision; // VARCHAR(3)
    private String noSecuencialComprobanteVenta; // VARCHAR(9)
    private BigDecimal baseImponibleNoObjetoIva; // DECIMAL(14,2)
    private BigDecimal baseImponibleTarifa0; // DECIMAL(14,2)
    private BigDecimal baseImponibleTarifaIvaDiferente0; // DECIMAL(14,2)
    private BigDecimal montoIva; // DECIMAL(14,2)
    private String tipoDeCompensaciones; // VARCHAR(2)
    private BigDecimal montoDeCompensaciones; // DECIMAL(14,2)
    private BigDecimal montoIce; // DECIMAL(14,2)
    private BigDecimal valorIvaRetenido; // DECIMAL(14,2)
    private BigDecimal valorRentaRetenido; // DECIMAL(14,2)
    private String formaDeCobro; // VARCHAR(2)
    private String codigoDelEstablecimiento; // VARCHAR(3)
    private BigDecimal ventasGeneradasEnElEstablecimiento; // DECIMAL(14,2)
    private BigDecimal ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad; // DECIMAL(14,2)
    private String usuario; // VARCHAR(30)
    private LocalDate fecha; // DATE
    private String descripcion; // VARCHAR(50)
    private String detalle; // VARCHAR(50)
    private String usuario2; // VARCHAR(50)
    private String cCostos; // VARCHAR(50)
    private String iBeneficiario; // VARCHAR(50)
    private String cuentaIvaBase; // VARCHAR(50)
    private String cuentaIngresoBase; // VARCHAR(50)
    private String formularioBaseImponible15; // VARCHAR(50)
    private String cuentaIva; // VARCHAR(50)
    private String cuentaIngreso; // VARCHAR(50)
    private String sistema; // VARCHAR(1)
    private String sistemaTipoDocumento; // VARCHAR(3)

    @Transient
    private int port;
}