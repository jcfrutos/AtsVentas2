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
            /**
             * Helper method to extract a String value from a Cell.
             */
            private String getCellValue(Cell cell) {
                if (cell == null) {
                    return "";
                }
                switch (cell.getCellType()) {
                    case STRING:
                        return cell.getStringCellValue();
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue());
                    case FORMULA:
                        try {
                            return cell.getStringCellValue();
                        } catch (IllegalStateException e) {
                            return String.valueOf(cell.getNumericCellValue());
                        }
                    case BLANK:
                    case ERROR:
                    default:
                        return "";
                }
            }
        }
        try {InputStream inputStream = file.getInputStream();
            if (fileName.endsWith(".xlsx")) {
                atsVentasRepository.importAtsVentasFromExcel(inputStream);
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
                return "Faltan columnas requeridas en la hoja de cálculo";
            }
            List<AtsVentas> atsVentasList = new ArrayList<>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue; // Skip empty rows
                }
                AtsVentas atsVentas = new AtsVentas();
                atsVentas.setTipoIdentificacionCliente(getCellValue(row.getCell(tipoIdentificacionClienteIdx)));
                atsVentas.setNoIdentificacionCliente(getCellValue(row.getCell(noIdentificacionClienteIdx)));
                atsVentas.setParteRelacionada(getCellValue(row.getCell(parteRelacionadaIdx)));
                atsVentas.setTipoDeCliente(getCellValue(row.getCell(tipoDeClienteIdx)));
                atsVentas.setRazonSocialCliente(getCellValue(row.getCell(razonSocialClienteIdx)));
                atsVentas.setCodigoTipoComprobante(getCellValue(row.getCell(codigoTipoComprobanteIdx)));
                atsVentas.setTipoDeEmision(getCellValue(row.getCell(tipoDeEmisionIdx)));
                atsVentas.setNoSerieComprobanteVentaEstablecimiento(getCellValue(row.getCell(noSerieComprobanteVentaEstablecimientoIdx)));
                atsVentas.setNoSerieComprobanteVentaPuntoEmision(getCellValue(row.getCell(noSerieComprobanteVentaPuntoEmisionIdx)));
                atsVentas.setNoSecuencialComprobanteVenta(getCellValue(row.getCell(noSecuencialComprobanteVentaIdx)));
                atsVentas.setBaseImponibleNoObjetoIva(getBigDecimalValue(row.getCell(baseImponibleNoObjetoIvaIdx)));
                atsVentas.setBaseImponibleTarifa0(getBigDecimalValue(row.getCell(baseImponibleTarifa0Idx)));
                atsVentas.setBaseImponibleTarifaIvaDiferente0(getBigDecimalValue(row.getCell(baseImponibleTarifaIvaDiferente0Idx)));
                atsVentas.setMontoIva(getBigDecimalValue(row.getCell(montoIvaIdx)));
                atsVentas.setTipoDeCompensaciones(getCellValue(row.getCell(tipoDeCompensacionesIdx)));
                atsVentas.setMontoDeCompensaciones(getBigDecimalValue(row.getCell(montoDeCompensacionesIdx)));
                atsVentas.setMontoIce(getBigDecimalValue(row.getCell(montoIceIdx)));
                atsVentas.setValorIvaRetenido(getBigDecimalValue(row.getCell(valorIvaRetenidoIdx)));
                atsVentas.setValorRentaRetenido(getBigDecimalValue(row.getCell(valorRentaRetenidoIdx)));
                atsVentas.setFormaDeCobro(getCellValue(row.getCell(formaDeCobroIdx)));
                atsVentas.setCodigoDelEstablecimiento(getCellValue(row.getCell(codigoDelEstablecimientoIdx)));
                atsVentas.setVentasGeneradasEnElEstablecimiento(getBigDecimalValue(row.getCell(ventasGeneradasEnElEstablecimientoIdx)));
                atsVentas.setIvaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad(getBigDecimalValue(row.getCell(ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridadIdx)));
                atsVentas.setUsuario(getCellValue(row.getCell(usuarioIdx)));
                atsVentas.setFecha(getDateValue(row.getCell(fechaIdx)));
                atsVentas.setDescripcion(getCellValue(row.getCell(descripcionIdx)));
                atsVentas.setDetalle(getCellValue(row.getCell(detalleIdx)));
                atsVentas.setUsuario2(getCellValue(row.getCell(usuario2Idx)));
                atsVentas.setCCostos(getCellValue(row.getCell(cCostosIdx)));
                atsVentas.setIBeneficiario(getCellValue(row.getCell(iBeneficiarioIdx)));
                atsVentas.setCuentaIvaBase(getCellValue(row.getCell(cuentaIvaBaseIdx)));
                atsVentas.setCuentaIngresoBase(getCellValue(row.getCell(cuentaIngresoBaseIdx)));
                atsVentas.setFormularioBaseImponible15(getCellValue(row.getCell(formularioBaseImponible15Idx)));
                atsVentas.setCuentaIva(getCellValue(row.getCell(cuentaIvaIdx)));
                atsVentas.setCuentaIngreso(getCellValue(row.getCell(cuentaIngresoIdx)));
                atsVentas.setSistemaTipoDocumento(getCellValue(row.getCell(sistemaTipoDocumentoIdex)));
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
}
