package anexostransaccionales.atsventasv2.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import anexostransaccionales.atsventasv2.models.entities.AtsVentasv2;
import anexostransaccionales.atsventasv2.repository.AtsVentasv2Repository;

public class AtsVentasv2ServiceImpl implements AtsVentasv2Service {
    private AtsVentasv2Repository atsVentasv2Repository;
    private Environment environment;
    
    public AtsVentasv2ServiceImpl(AtsVentasv2Repository atsVentasv2Repository, Environment environment) {
        this.atsVentasv2Repository = atsVentasv2Repository;
        this.environment = environment;
    }
    @Override
    public String importAtsVentasv2FromFile(MultipartFile file) {
        String fileName = file .getOriginalFilename();
        if (fileName == null) {
            return "El archivo no es válido";
        }
        try {InputStream inputStream = file.getInputStream();
            if (fileName.endsWith(".xlsx")) {
                return importAtsVentasv2FromExcel(inputStream);
            }
            if (fileName.endsWith(".xls")) {
                return "Formato de archivo .xls no soportado. Use .xlsx";
            }
            return "Formato de archivo no soportado. Use .xlsx";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar el archivo: " + e.getMessage();
        }

    }
    private String importAtsVentasv2FromExcel(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            if(sheet == null) {
                return "La hoja de cálculo está vacía";
            }
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return "La hoja de cálculo no contiene encabezados";
            }
            int colums = headerRow.getPhysicalNumberOfCells();
            int tipoIdentificacionCliente = -1, noIdentificacionCliente = -1, parteRelacionada = -1, tipoDeCliente = -1,
                razonSocialCliente = -1, codigoTipoComprobante = -1, tipoDeEmision = -1,
                noSerieComprobanteVentaEstablecimiento = -1, noSerieComprobanteVentaPuntoEmision = -1,
                noSecuencialComprobanteVenta = -1, baseImponibleNoObjetoIva = -1, baseImponibleTarifa0 = -1,
                baseImponibleTarifaIvaDiferente0 = -1, montoIva = -1, tipoDeCompensaciones = -1,
                montoDeCompensaciones = -1, montoIce = -1, valorIvaRetenido = -1, valorRentaRetenido = -1,
                formaDeCobro = -1, codigoDelEstablecimiento = -1, ventasGeneradasEnElEstablecimiento = -1,
                ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad = -1, usuario = -1,
                fecha = -1, descripcion = -1, detalle = -1, usuario2 = -1, cCostos = -1, iBeneficiario = -1,
                cuentaIvaBase = -1, cuentaIngresoBase = -1, sistema = -1, sistemaTipoDocumento = -1;

            for (int i = 0; i < colums; i++) {
                String value = headerRow.getCell(i).getStringCellValue();
                switch (value) {
                    case "tipoIdentificacionCliente":
                        tipoIdentificacionCliente = i;
                        break;
                    case "noIdentificacionCliente":
                        noIdentificacionCliente = i;
                        break;
                    case "parteRelacionada":
                        parteRelacionada = i;
                        break;
                    case "tipoDeCliente":
                        tipoDeCliente = i;
                        break;
                    case "razonSocialCliente":
                        razonSocialCliente = i;
                        break;
                    case "codigoTipoComprobante":
                        codigoTipoComprobante = i;
                        break;
                    case "tipoDeEmision":
                        tipoDeEmision = i;
                        break;
                    case "noSerieComprobanteVentaEstablecimiento":
                        noSerieComprobanteVentaEstablecimiento = i;
                        break;
                    case "noSerieComprobanteVentaPuntoEmision":
                        noSerieComprobanteVentaPuntoEmision = i;
                        break;
                    case "noSecuencialComprobanteVenta":
                        noSecuencialComprobanteVenta = i;
                        break;
                    case "baseImponibleNoObjetoIva":
                        baseImponibleNoObjetoIva = i;
                        break;
                    case "baseImponibleTarifa0":
                        baseImponibleTarifa0 = i;
                        break;
                    case "baseImponibleTarifaIvaDiferente0":
                        baseImponibleTarifaIvaDiferente0 = i;
                        break;
                    case "montoIva":
                        montoIva = i;
                        break;
                    case "tipoDeCompensaciones":
                        tipoDeCompensaciones = i;
                        break;
                    case "montoDeCompensaciones":
                        montoDeCompensaciones = i;
                        break;
                    case "montoIce":
                        montoIce = i;
                        break;
                    case "valorIvaRetenido":
                        valorIvaRetenido = i;
                        break;
                    case "valorRentaRetenido":
                        valorRentaRetenido = i;
                        break;
                    case "formaDeCobro":
                        formaDeCobro = i;
                        break;
                    case "codigoDelEstablecimiento":
                        codigoDelEstablecimiento = i;
                        break;
                    case "ventasGeneradasEnElEstablecimiento":
                        ventasGeneradasEnElEstablecimiento = i;
                        break;
                    case "ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad":
                        ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad = i;
                        break;
                    case "usuario":
                        usuario = i;
                        break;
                    case "fecha":
                        fecha = i;
                        break;
                    case "descripcion":
                        descripcion = i;
                        break;
                    case "detalle":
                        detalle = i;
                        break;
                    case "usuario2":
                        usuario2 = i;
                        break;
                    case "cCostos":
                        cCostos = i;
                        break;
                    case "iBeneficiario":
                        iBeneficiario = i;
                        break;
                    case "cuentaIvaBase":
                        cuentaIvaBase = i;
                        break;
                    case "cuentaIngresoBase":
                        cuentaIngresoBase = i;
                        break;
                    case "sistema":
                        sistema = i;
                        break;
                    case "sistemaTipoDocumento":
                        sistemaTipoDocumento = i;
                        break;
                
                }
            }
            if (tipoIdentificacionCliente == -1 || noIdentificacionCliente == -1 || parteRelacionada == -1 ||
                tipoDeCliente == -1 || razonSocialCliente == -1 || codigoTipoComprobante == -1 ||
                tipoDeEmision == -1 || noSerieComprobanteVentaEstablecimiento == -1 ||
                noSerieComprobanteVentaPuntoEmision == -1 || noSecuencialComprobanteVenta == -1 ||
                baseImponibleNoObjetoIva == -1 || baseImponibleTarifa0 == -1 ||
                baseImponibleTarifaIvaDiferente0 == -1 || montoIva == -1 || tipoDeCompensaciones == -1 ||
                montoDeCompensaciones == -1 || montoIce == -1 || valorIvaRetenido == -1 ||
                valorRentaRetenido == -1 || formaDeCobro == -1 || codigoDelEstablecimiento == -1 ||
                ventasGeneradasEnElEstablecimiento == -1 || ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad == -1 ||
                usuario == -1 || fecha == -1 || descripcion == -1 || detalle == -1 || usuario2 == -1 ||
                cCostos == -1 || iBeneficiario == -1 || cuentaIvaBase == -1 || cuentaIngresoBase == -1 ||
                sistema == -1 || sistemaTipoDocumento == -1) {
                return "Faltan columnas requeridas en el archivo";
            }
            List<AtsVentasv2> atsVentasv2List = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }
                AtsVentasv2 atsVentasv2 = new AtsVentasv2();
                atsVentasv2.setTipoIdentificacionCliente(row.getCell(tipoIdentificacionCliente).getStringCellValue());
                atsVentasv2.setNoIdentificacionCliente(row.getCell(noIdentificacionCliente).getStringCellValue());
                atsVentasv2.setParteRelacionada(row.getCell(parteRelacionada).getStringCellValue());
                atsVentasv2.setTipoDeCliente(row.getCell(tipoDeCliente).getStringCellValue());
                atsVentasv2.setRazonSocialCliente(row.getCell(razonSocialCliente).getStringCellValue());
                atsVentasv2.setCodigoTipoComprobante(row.getCell(codigoTipoComprobante).getStringCellValue());
                atsVentasv2.setTipoDeEmision(row.getCell(tipoDeEmision).getStringCellValue());
                atsVentasv2.setNoSerieComprobanteVentaEstablecimiento(
                    row.getCell(noSerieComprobanteVentaEstablecimiento).getStringCellValue());
                atsVentasv2.setNoSerieComprobanteVentaPuntoEmision(
                    row.getCell(noSerieComprobanteVentaPuntoEmision).getStringCellValue());
                atsVentasv2.setNoSecuencialComprobanteVenta(
                    row.getCell(noSecuencialComprobanteVenta).getStringCellValue());
                atsVentasv2.setBaseImponibleNoObjetoIva(java.math.BigDecimal.valueOf(row.getCell(baseImponibleNoObjetoIva).getNumericCellValue()));
                atsVentasv2.setBaseImponibleTarifa0(java.math.BigDecimal.valueOf(row.getCell(baseImponibleTarifa0).getNumericCellValue()));
                atsVentasv2.setBaseImponibleTarifaIvaDiferente0(
                    java.math.BigDecimal.valueOf(row.getCell(baseImponibleTarifaIvaDiferente0).getNumericCellValue()));
                atsVentasv2.setMontoIva(java.math.BigDecimal.valueOf(row.getCell(montoIva).getNumericCellValue()));
                atsVentasv2.setTipoDeCompensaciones(row.getCell(tipoDeCompensaciones).getStringCellValue());
                atsVentasv2.setMontoDeCompensaciones(java.math.BigDecimal.valueOf(row.getCell(montoDeCompensaciones).getNumericCellValue()));
                atsVentasv2.setMontoIce(java.math.BigDecimal.valueOf(row.getCell(montoIce).getNumericCellValue()));
                atsVentasv2.setValorIvaRetenido(java.math.BigDecimal.valueOf(row.getCell(valorIvaRetenido).getNumericCellValue()));
                atsVentasv2.setValorRentaRetenido(java.math.BigDecimal.valueOf(row.getCell(valorRentaRetenido).getNumericCellValue()));
                atsVentasv2.setFormaDeCobro(row.getCell(formaDeCobro).getStringCellValue());
                atsVentasv2.setCodigoDelEstablecimiento(row.getCell(codigoDelEstablecimiento).getStringCellValue());
                atsVentasv2.setVentasGeneradasEnElEstablecimiento(
                    java.math.BigDecimal.valueOf(row.getCell(ventasGeneradasEnElEstablecimiento).getNumericCellValue()));
                atsVentasv2.setIvaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad(
                    java.math.BigDecimal.valueOf(row.getCell(ivaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad).getNumericCellValue()));
                atsVentasv2.setUsuario(row.getCell(usuario).getStringCellValue());
                atsVentasv2.setFecha(row.getCell(fecha).getLocalDateTimeCellValue().toLocalDate());
                atsVentasv2.setDescripcion(row.getCell(descripcion).getStringCellValue());
                atsVentasv2.setDetalle(row.getCell(detalle).getStringCellValue());
                atsVentasv2.setUsuario2(row.getCell(usuario2).getStringCellValue());
                atsVentasv2.setCCostos(row.getCell(cCostos).getStringCellValue());
                atsVentasv2.setIBeneficiario(row.getCell(iBeneficiario).getStringCellValue());
                atsVentasv2.setCuentaIvaBase(row.getCell(cuentaIvaBase).getStringCellValue());
                atsVentasv2.setCuentaIngresoBase(row.getCell(cuentaIngresoBase).getStringCellValue());
                atsVentasv2.setSistema(row.getCell(sistema).getStringCellValue());
                atsVentasv2.setSistemaTipoDocumento(row.getCell(sistemaTipoDocumento).getStringCellValue());
                atsVentasv2.setPort(Integer.parseInt(environment.getProperty("server.port", "8080")));
                atsVentasv2List.add(atsVentasv2);
            }
            atsVentasv2Repository.saveAll(atsVentasv2List);
            return "Archivo importado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al importar el archivo: " + e.getMessage();
        }
    }
    // Removed unused method getDateValue(Cell cell) as it was never used locally and caused compilation issues.
    @Override
    public List<AtsVentasv2> findAll() {
        return (atsVentasv2Repository.findAll().stream().map(atsVentasv2 -> {
                atsVentasv2.setPort(Integer.parseInt(environment.getProperty("server.port", "8080")));
                return atsVentasv2;
        }).collect(Collectors.toList()));
    }
    @Override
    public Optional<AtsVentasv2> findById(Long id) {
        return atsVentasv2Repository.findById(id);
    }
    @Override
    public AtsVentasv2 create(AtsVentasv2 atsVentasv2) {
        atsVentasv2.setPort(Integer.parseInt(environment.getProperty("server.port", "8080")));
        return atsVentasv2Repository.save(atsVentasv2);
    }
    @Override
    public AtsVentasv2 update(Long id, AtsVentasv2 atsVentasv2) {
        return atsVentasv2Repository.findById(id).map(existingAtsVentasv2 -> {
            existingAtsVentasv2.setTipoIdentificacionCliente(atsVentasv2.getTipoIdentificacionCliente());
            existingAtsVentasv2.setNoIdentificacionCliente(atsVentasv2.getNoIdentificacionCliente());
            existingAtsVentasv2.setParteRelacionada(atsVentasv2.getParteRelacionada());
            existingAtsVentasv2.setTipoDeCliente(atsVentasv2.getTipoDeCliente());
            existingAtsVentasv2.setRazonSocialCliente(atsVentasv2.getRazonSocialCliente());
            existingAtsVentasv2.setCodigoTipoComprobante(atsVentasv2.getCodigoTipoComprobante());
            existingAtsVentasv2.setTipoDeEmision(atsVentasv2.getTipoDeEmision());
            existingAtsVentasv2.setNoSerieComprobanteVentaEstablecimiento(
                atsVentasv2.getNoSerieComprobanteVentaEstablecimiento());
            existingAtsVentasv2.setNoSerieComprobanteVentaPuntoEmision(
                atsVentasv2.getNoSerieComprobanteVentaPuntoEmision());
            existingAtsVentasv2.setNoSecuencialComprobanteVenta(atsVentasv2.getNoSecuencialComprobanteVenta());
            existingAtsVentasv2.setBaseImponibleNoObjetoIva(atsVentasv2.getBaseImponibleNoObjetoIva());
            existingAtsVentasv2.setBaseImponibleTarifa0(atsVentasv2.getBaseImponibleTarifa0());
            existingAtsVentasv2.setBaseImponibleTarifaIvaDiferente0(atsVentasv2.getBaseImponibleTarifaIvaDiferente0());
            existingAtsVentasv2.setMontoIva(atsVentasv2.getMontoIva());
            existingAtsVentasv2.setTipoDeCompensaciones(atsVentasv2.getTipoDeCompensaciones());
            existingAtsVentasv2.setMontoDeCompensaciones(atsVentasv2.getMontoDeCompensaciones());
            existingAtsVentasv2.setMontoIce(atsVentasv2.getMontoIce());
            existingAtsVentasv2.setValorIvaRetenido(atsVentasv2.getValorIvaRetenido());
            existingAtsVentasv2.setValorRentaRetenido(atsVentasv2.getValorRentaRetenido());
            existingAtsVentasv2.setFormaDeCobro(atsVentasv2.getFormaDeCobro());
            existingAtsVentasv2.setCodigoDelEstablecimiento(atsVentasv2.getCodigoDelEstablecimiento());
            existingAtsVentasv2.setVentasGeneradasEnElEstablecimiento(
                atsVentasv2.getVentasGeneradasEnElEstablecimiento());
            existingAtsVentasv2.setIvaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad(
                atsVentasv2.getIvaCompensadoEnElEstablecimientoPorVentasLeyDeSolidaridad());
            existingAtsVentasv2.setUsuario(atsVentasv2.getUsuario());
            existingAtsVentasv2.setFecha(atsVentasv2.getFecha());
            existingAtsVentasv2.setDescripcion(atsVentasv2.getDescripcion());
            existingAtsVentasv2.setDetalle(atsVentasv2.getDetalle());
            existingAtsVentasv2.setUsuario2(atsVentasv2.getUsuario2());
            existingAtsVentasv2.setCCostos(atsVentasv2.getCCostos());
            existingAtsVentasv2.setIBeneficiario(atsVentasv2.getIBeneficiario());
            existingAtsVentasv2.setCuentaIvaBase(atsVentasv2.getCuentaIvaBase());
            existingAtsVentasv2.setCuentaIngresoBase(atsVentasv2.getCuentaIngresoBase());
            existingAtsVentasv2.setSistema(atsVentasv2.getSistema());
            existingAtsVentasv2.setSistemaTipoDocumento(atsVentasv2.getSistemaTipoDocumento());
            existingAtsVentasv2.setPort(Integer.parseInt(environment.getProperty("server.port", "8080")));
            return atsVentasv2Repository.save(existingAtsVentasv2);
        }).orElse(null);
    }
    @Override
    public void delete(Long id) {
        if (atsVentasv2Repository.existsById(id)) {
            atsVentasv2Repository.deleteById(id);
        }
    }

}
