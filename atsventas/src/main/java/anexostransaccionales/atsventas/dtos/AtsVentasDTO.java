package anexostransaccionales.atsventas.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AtsVentasDTO {
    private String id;
    private String tipoIdentificacionCliente;
    private String noIdentificacionCliente;
    private String parteRelacionada;
    private String tipoDeCliente;
    private String razonSocialCliente;
    private String codigoTipoComprobante;
    private String tipoDeEmision;
    private String noSerieComprobanteVentaEstablecimiento;
    private String noSerieComprobanteVentaPuntoEmision;
    private String noSecuencialComprobanteVenta;
    private BigDecimal baseImponibleNoObjetoIva;
    private BigDecimal baseImponibleTarifa0;
    private BigDecimal baseImponibleTarifaIvaDiferente0;
    private BigDecimal montoIva;
    private String tipoDeCompensaciones;
    private BigDecimal montoDeCompensaciones;
    private BigDecimal montoIce;
    private BigDecimal valorIvaRetenido;
    private BigDecimal valorRentaRetenido;
    private String formaDeCobro;
    private String codigoDelEstablecimiento;
    private BigDecimal ventasGeneradasEnElEstablecimiento;
    private BigDecimal ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad;
    private String usuario;
    private LocalDate fecha;
    private String descripcion;
    private String detalle;
    private String usuario2;
    private String cCostos;
    private String iBeneficiario;
    private String cuentaIvaBase;
    private String cuentaIngresoBase;
    private String formularioBaseImponible15;
    private String cuentaIva;
    private String cuentaIngreso;
    private String sistema;
    private String sistemaTipoDocumento;
}
