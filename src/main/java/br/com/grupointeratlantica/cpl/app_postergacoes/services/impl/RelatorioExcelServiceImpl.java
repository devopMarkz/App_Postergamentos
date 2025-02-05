package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.RelatorioExcelService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.GeracaoDeRelatorioInvalidaException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioExcelServiceImpl implements RelatorioExcelService {

    @Override
    public File gerarRelatorioNotas(List<NotaPostergada> notas) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Notas Postergadas");

            Row headerRow = sheet.createRow(0);
            String[] colunas = {"Número Único", "Número Nota", "Empresa", "Parceiro", "Nova Data Vencimento", "Justificativa"};
            for (int i = 0; i < colunas.length; i++) {
                headerRow.createCell(i).setCellValue(colunas[i]);
            }

            int rowNum = 1;
            for (NotaPostergada nota : notas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nota.getNumeroUnico());
                row.createCell(1).setCellValue(nota.getNumeroNota());
                row.createCell(2).setCellValue(nota.getEmpresa().getNome());
                row.createCell(3).setCellValue(nota.getParceiro());
                row.createCell(4).setCellValue(nota.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                row.createCell(5).setCellValue(nota.getJustificativa() != null ? nota.getJustificativa() : ""); // Evita null
            }

            // Criar arquivo temporário
            File tempFile = File.createTempFile("relatorio_notas", ".xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                workbook.write(outputStream);
            }
            workbook.close();

            return tempFile;
        } catch (IOException e){
            throw new GeracaoDeRelatorioInvalidaException("Erro ao gerar relatório.");
        }
    }

}
