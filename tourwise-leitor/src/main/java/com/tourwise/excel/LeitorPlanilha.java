package com.tourwise.excel;

import com.tourwise.model.Hospedagem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorPlanilha {

    private final DataFormatter formatter = new DataFormatter();
    private final Map<String, Integer> colunas = new HashMap<>();

    //le o arquivo inteiro
    public List<Hospedagem> ler(String caminho) throws Exception {
        List<Hospedagem> lista = new ArrayList<>();

        try (Workbook wb = WorkbookFactory.create(new File(caminho))) {
            Sheet sheet = wb.getSheetAt(0);

            //pega o nome das colunas
            for (Cell c : sheet.getRow(0)) {
                colunas.put(formatter.formatCellValue(c).trim(), c.getColumnIndex());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Hospedagem h = new Hospedagem();
                h.setCnpj(texto(row, "Número de Inscrição do CNPJ"));
                h.setNomeFantasia(texto(row, "Nome Fantasia"));
                h.setEmailComercial(texto(row, "E-mail Comercial"));
                h.setWebsite(texto(row, "Website"));
                h.setPorte(texto(row, "Porte"));
                h.setTipoHospedagem(texto(row, "Tipo de Hospedagem"));
                h.setDtAbertura(data(row, "Data de Abertura"));
                h.setUniHabit(inteiro(row, "Unidade Habitacionais"));
                h.setLeitos(inteiro(row, "Leitos"));
                h.setUniHabitAcess(inteiro(row, "UHs Acessíveis"));
                h.setLeitosAcess(inteiro(row, "Leitos Acessíveis"));
                h.setEndereco(texto(row, "Endereço Completo Comercial"));
                h.setMunicipio(texto(row, "Município"));
                h.setUf(texto(row, "UF"));
                h.setIdiomas(texto(row, "Idiomas"));
                h.setCnaes(texto(row, "CNAE(S) relacionados à atividade"));

                if (h.getCnpj() != null) {
                    lista.add(h);
                }
            }
        }
        return lista;
    }

    //pega o dado da celula
    private Cell celula(Row row, String coluna) {
        return row.getCell(colunas.get(coluna));
    }

    //converte o dado em String
    private String texto(Row row, String coluna) {
        Cell c = celula(row, coluna);
        if (c == null) return null;
        String s = formatter.formatCellValue(c).trim();
        return (s.isEmpty() || s.equals("-")) ? null : s;
    }

    //converte numeros numeros
    private Integer inteiro(Row row, String coluna) {
        Cell c = celula(row, coluna);
        if (c == null || c.getCellType() != CellType.NUMERIC) {
            return null;
        };

        return (int) c.getNumericCellValue();
    }

    //converte data
    private LocalDate data(Row row, String coluna) {
        Cell c = celula(row, coluna);
        if (c == null || c.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(c)) {
            return null;
        }
        return c.getLocalDateTimeCellValue().toLocalDate();
    }
}
