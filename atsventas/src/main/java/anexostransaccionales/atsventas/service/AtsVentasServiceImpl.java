package anexostransaccionales.atsventas.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import anexostransaccionales.atsventas.repository.AtsVentasRepository;

// Apache POI imports for Excel processing
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.List;
import java.util.ArrayList;
import anexostransaccionales.atsventas.models.entities.AtsVentas;

@Service
public class AtsVentasServiceImpl implements AtsVentasService {

    @Autowired
    private AtsVentasRepository atsVentasRepository;

    @Override
    public String importAtsVentasFromFile(MultipartFile file) {
        String fileName = file .getOriginalFilename();
        if (fileName == null) {
            return "El archivo no es válido";
        }
        try {InputStream inputStream = file.getInputStream();
            if (fileName.endsWith(".xlsx")) {
                return importAtsVentasFromExcel(inputStream);
            } 
            if (fileName.endsWith(".csv")) {
                atsVentasRepository.importAtsVentasFromCsv(inputStream);
            } 
            return "Formato de archivo no soportado. Use .xlsx o .csv";
        
          
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar el archivo: " + e.getMessage();
        }
    }

    private String importAtsVentasFromExcel(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return "La hoja de cálculo está vacía";
            }
            Row headRow = sheet.getRow(0);
            if (headRow == null) {
                return "La hoja de cálculo no contiene encabezados";
            }
            int colums = headRow.getPhysicalNumberOfCells();
            int tipoIdentificacionClienteIdx = -1, noIdentificacionClienteIdx = -1, parteRelacionadaIdx = -1,
                tipoDeClienteIdx = -1, razonSocialClienteIdx = -1, codigoTipoComprobanteIdx = -1,
                tipoDeEmisionIdx = -1, noSerieComprobanteVentaEstablecimientoIdx = -1,
                noSerieComprobanteVentaPuntoEmisionIdx = -1, noSecuencialComprobanteVentaIdx = -1,
                baseImponibleNoObjetoIvaIdx = -1, baseImponibleTarifa0Idx = -1,
                baseImponibleTarifaIvaDiferente0Idx = -1, montoIvaIdx = -1, tipoDeCompensacionesIdx = -1,
                montoDeCompensacionesIdx = -1, montoIceIdx = -1, valorIvaRetenidoIdx = -1,
                valorRentaRetenidoIdx = -1, formaDeCobroIdx = -1, codigoDelEstablecimientoIdx = -1,
                ventasGeneradasEnElEstablecimientoIdx = -1,
                ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridadIdx = -1,
                usuarioIdx = -1, fechaIdx = -1, descripcionIdx = -1, detalleIdx = -1,
                usuario2Idx = -1, cCostosIdx = -1, iBeneficiarioIdx = -1, cuentaIvaBaseIdx = -1,
                cuentaIngresoBaseIdx = -1, formularioBaseImponible15Idx = -1, cuentaIvaIdx = -1,
                cuentaIngresoIdx = -1, sistemaTipoDocumentoIdex=-1;
        
            for (int i = 0; i < colums; i++) {
                String value = headRow.getCell(i).getStringCellValue().trim().toLowerCase();
                switch (value) {
                    case "tipo_identificacion_cliente":
                        tipoIdentificacionClienteIdx = i;
                        break;
                    case "no_identificacion_cliente":
                        noIdentificacionClienteIdx = i;
                        break;
                    case "parte_relacionada":
                        parteRelacionadaIdx = i;
                        break;
                    case "tipo_de_cliente":
                        tipoDeClienteIdx = i;
                        break;
                    case "razon_social_cliente":
                        razonSocialClienteIdx = i;
                        break;
                    case "codigo_tipo_comprobante":
                        codigoTipoComprobanteIdx = i;
                        break;
                    case "tipo_de_emision":
                        tipoDeEmisionIdx = i;
                        break;
                    case "no_serie_comprobante_venta_establecimiento":
                        noSerieComprobanteVentaEstablecimientoIdx = i;
                        break;
                    case "no_serie_comprobante_venta_punto_emision":
                        noSerieComprobanteVentaPuntoEmisionIdx = i;
                        break;
                    case "no_secuencial_comprobante_venta":
                        noSecuencialComprobanteVentaIdx = i;
                        break;
                    case "base_imponible_no_objeto_iva":
                        baseImponibleNoObjetoIvaIdx = i;
                        break;
                    case "base_imponible_tarifa_0":
                        baseImponibleTarifa0Idx = i;
                        break;
                    case "base_imponible_tarifa_iva_diferente_0":
                        baseImponibleTarifaIvaDiferente0Idx = i;
                        break;
                    case "monto_iva":
                        montoIvaIdx = i;
                        break;
                    case "tipo_de_compensaciones":
                        tipoDeCompensacionesIdx = i;
                        break;
                    case "monto_de_compensaciones":
                        montoDeCompensacionesIdx = i;
                        break;
                    case "monto_ice":
                        montoIceIdx = i;
                        break;
                    case "valor_iva_retenido":
                        valorIvaRetenidoIdx = i;
                        break;
                    case "valor_renta_retenido":
                        valorRentaRetenidoIdx = i;
                        break;
                    case "forma_de_cobro":
                        formaDeCobroIdx = i;
                        break;
                    case "codigo_del_establecimiento":
                        codigoDelEstablecimientoIdx = i;
                        break;
                    case "ventas_generadas_en_el_establecimiento":
                        ventasGeneradasEnElEstablecimientoIdx = i;
                        break;
                    case "iva_compensado_en_el_establecimiento_por_ventas_ley_de_solidaridad":
                        ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridadIdx = i;
                        break;
                    case "usuario":
                        usuarioIdx = i;
                        break;
                    case "fecha":
                        fechaIdx = i;
                        break;
                    case "descripcion":
                        descripcionIdx = i;
                        break;
                    case "detalle":
                        detalleIdx = i;
                        break;
                    case "usuario2":
                        usuario2Idx = i;
                        break;
                    case "c_costos":
                        cCostosIdx = i;
                        break;
                    case "i_beneficiario":
                        iBeneficiarioIdx = i;
                        break;
                    case "cuenta_iva_base":
                        cuentaIvaBaseIdx = i;
                        break;
                    case "cuenta_ingreso_base":
                        cuentaIngresoBaseIdx = i;
                        break;
                    case "formulario_base_imponible_15":
                        formularioBaseImponible15Idx = i;
                        break;
                    case "cuenta_iva":
                        cuentaIvaIdx = i;
                        break;
                    case "cuenta_ingreso":
                        cuentaIngresoIdx = i;
                        break;
                    case "sistema_tipo_documento":
                        sistemaTipoDocumentoIdex = i;
                        break;
 
            }
            if (tipoIdentificacionClienteIdx == -1 || noIdentificacionClienteIdx == -1 ||
                parteRelacionadaIdx == -1 || tipoDeClienteIdx == -1 || razonSocialClienteIdx == -1 ||
                codigoTipoComprobanteIdx == -1 || tipoDeEmisionIdx == -1 ||
                noSerieComprobanteVentaEstablecimientoIdx == -1 ||
                noSerieComprobanteVentaPuntoEmisionIdx == -1 ||
                noSecuencialComprobanteVentaIdx == -1 || baseImponibleNoObjetoIvaIdx == -1 ||
                baseImponibleTarifa0Idx == -1 || baseImponibleTarifaIvaDiferente0Idx == -1 ||
                montoIvaIdx == -1 || tipoDeCompensacionesIdx == -1 ||
                montoDeCompensacionesIdx == -1 || montoIceIdx == -1 ||
                valorIvaRetenidoIdx == -1 || valorRentaRetenidoIdx == -1 ||
                formaDeCobroIdx == -1 || codigoDelEstablecimientoIdx == -1 ||
                ventasGeneradasEnElEstablecimientoIdx == -1 ||
                ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridadIdx == -1 ||
                usuarioIdx == -1 || fechaIdx == -1 || descripcionIdx == -1 ||
                detalleIdx == -1 || usuario2Idx == -1 || cCostosIdx == -1 ||
                iBeneficiarioIdx == -1 || cuentaIvaBaseIdx == -1 ||
                cuentaIngresoBaseIdx == -1 || formularioBaseImponible15Idx == -1 ||
                cuentaIvaIdx == -1 || cuentaIngresoIdx == -1 || sistemaTipoDocumentoIdex == -1) {
                return "Faltan columnas requeridas en la hoja de cálculo";
            }
            List<AtsVentas> atsVentasList = new ArrayList<>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue; // Skip empty rows
                }
                AtsVentas atsVentas = new AtsVentas();
                atsVentas.setTipoIdentificacionCliente(getCellString(row, tipoIdentificacionClienteIdx));
                atsVentas.setNoIdentificacionCliente(getCellString(row, noIdentificacionClienteIdx));
                atsVentas.setParteRelacionada(getCellString(row, parteRelacionadaIdx));
                atsVentas.setTipoDeCliente(getCellString(row, tipoDeClienteIdx));
                atsVentas.setRazonSocialCliente(getCellString(row, razonSocialClienteIdx));
                atsVentas.setCodigoTipoComprobante(getCellString(row, codigoTipoComprobanteIdx));
                atsVentas.setTipoDeEmision(getCellString(row, tipoDeEmisionIdx));
                atsVentas.setNoSerieComprobanteVentaEstablecimiento(getCellString(row, noSerieComprobanteVentaEstablecimientoIdx));
                atsVentas.setNoSerieComprobanteVentaPuntoEmision(getCellString(row, noSerieComprobanteVentaPuntoEmisionIdx));
                atsVentas.setNoSecuencialComprobanteVenta(getCellString(row, noSecuencialComprobanteVentaIdx));
                atsVentas.setBaseImponibleNoObjetoIva(java.math.BigDecimal.valueOf(getCellDouble(row.getCell(baseImponibleNoObjetoIvaIdx))));
                atsVentas.setBaseImponibleTarifa0(getBigDecimalValue(row.getCell(baseImponibleTarifa0Idx)));
                atsVentas.setBaseImponibleTarifaIvaDiferente0(getBigDecimalValue(row.getCell(baseImponibleTarifaIvaDiferente0Idx)));
                atsVentas.setMontoIva(getBigDecimalValue(row.getCell(montoIvaIdx)));
                atsVentas.setTipoDeCompensaciones(getCellString(row, tipoDeCompensacionesIdx));
                atsVentas.setMontoDeCompensaciones(getBigDecimalValue(row.getCell(montoDeCompensacionesIdx)));
                atsVentas.setMontoIce(getBigDecimalValue(row.getCell(montoIceIdx)));
                atsVentas.setValorIvaRetenido(getBigDecimalValue(row.getCell(valorIvaRetenidoIdx)));
                atsVentas.setValorRentaRetenido(getBigDecimalValue(row.getCell(valorRentaRetenidoIdx)));
                atsVentas.setFormaDeCobro(getCellString(row, formaDeCobroIdx));
                atsVentas.setCodigoDelEstablecimiento(getCellString(row, codigoDelEstablecimientoIdx));
                atsVentas.setVentasGeneradasEnElEstablecimiento(getBigDecimalValue(row.getCell(ventasGeneradasEnElEstablecimientoIdx)));
                atsVentas.setIvaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad(getBigDecimalValue(row.getCell(ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridadIdx)));
                atsVentas.setUsuario(getCellString(row, usuarioIdx));
                java.util.Date fechaDate = getDateValue(row.getCell(fechaIdx));
                atsVentas.setFecha(fechaDate == null ? null : fechaDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                atsVentas.setDescripcion(getCellString(row, descripcionIdx));
                atsVentas.setDetalle(getCellString(row, detalleIdx));
                atsVentas.setUsuario2(getCellString(row, usuario2Idx));
                atsVentas.setCCostos(getCellString(row, cCostosIdx));
                atsVentas.setIBeneficiario(getCellString(row, iBeneficiarioIdx));
                atsVentas.setCuentaIvaBase(getCellString(row, cuentaIvaBaseIdx));
                atsVentas.setCuentaIngresoBase(getCellString(row, cuentaIngresoBaseIdx));
                atsVentas.setFormularioBaseImponible15(getCellString(row, formularioBaseImponible15Idx));
                atsVentas.setCuentaIva(getCellString(row, cuentaIvaIdx));
                atsVentas.setCuentaIngreso(getCellString(row, cuentaIngresoIdx));
                atsVentas.setSistemaTipoDocumento(getCellString(row, sistemaTipoDocumentoIdex));
                atsVentasList.add(atsVentas);
            }
            atsVentasRepository.saveAll(atsVentasList);
            return "Datos importados correctamente desde Excel";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar el archivo Excel: " + e.getMessage(); 
        }

    }
    private String getCellString(Row row, int idx) {
        Cell cell = row.getCell(idx);
        if (cell == null) 
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case ERROR:
            default:
                return "";
        }
        
    }

    private java.math.BigDecimal getBigDecimalValue(Cell cell) {
        if (cell == null) {
            return java.math.BigDecimal.ZERO;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return java.math.BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                try {
                    return new java.math.BigDecimal(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return java.math.BigDecimal.ZERO;
                }
            case FORMULA:
                try {
                    return java.math.BigDecimal.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return java.math.BigDecimal.ZERO;
                }
            default:
                return java.math.BigDecimal.ZERO;
        }
    }

    private java.util.Date getDateValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return null;
                }
            case STRING:
                try {
                    // Try parsing as yyyy-MM-dd or similar
                    return java.sql.Date.valueOf(cell.getStringCellValue().trim());
                } catch (Exception e) {
                    return null;
                }
            default:
                return null;
        }
    }

    // Helper method to get double value from a cell
    private Double getCellDouble(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            case FORMULA:
                try {
                    return cell.getNumericCellValue();
                } catch (Exception e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }
}
