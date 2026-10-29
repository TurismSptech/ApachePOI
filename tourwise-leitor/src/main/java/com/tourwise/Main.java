package com.tourwise;

import com.tourwise.database.InsertData;
import com.tourwise.excel.LeitorPlanilha;
import com.tourwise.model.Hospedagem;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.File;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_BLUE + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Programa iniciado" + "\n");

        //definir caminho da pasta de leitura
        File caminho = new File("C:\\Users\\icaro\\OneDrive\\Desktop\\xlsx-tourwise");
        File[] arquivos = caminho.listFiles();

        InsertData insert = new InsertData();

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_BLUE + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Buscando planilhas já inseridas no BD\n");

        List<String> nomePlanilhasLidas = insert.buscarPlanilhasLidas();

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_BLUE + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Planilhas encontradas no BD: " + nomePlanilhasLidas.toString() + "\n");

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_BLUE + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Procurando novas planilhas em " + caminho + "\n");

        Boolean temPlanilhaNova = false;

        for (File arquivo : arquivos) {
            if (!nomePlanilhasLidas.contains(arquivo.getName())) {
                temPlanilhaNova = true;

                System.out.print(LocalDateTime.now().format(formatter) +
                        ANSI_GREEN + " - [INFO]" +
                        "[SYSTEM]" + ANSI_RESET +
                        " - Nova planilha encontrada: ");
                System.out.println(arquivo.getName() + "\n");

                String path = arquivo.getPath();

                System.out.println(LocalDateTime.now().format(formatter) +
                        ANSI_BLUE + " - [INFO]" +
                        "[SYSTEM]" + ANSI_RESET +
                        " - Iniciando gravação da planilha " + arquivo.getName() + " no BD \n");

                List<Hospedagem> hospedagens = new LeitorPlanilha().ler(path);
                Integer qtdRegistroIncompleto = 0;

                for (int i = 0; i < hospedagens.size(); i++) {
                    if(!insert.temTodosAtributosPreenchidos(hospedagens.get(i))){ qtdRegistroIncompleto++; }

                    insert.insertHosp(hospedagens.get(i));
                    System.out.println(LocalDateTime.now().format(formatter) +
                            ANSI_GREEN + " - [INFO]" +
                            "[INSERT] " + ANSI_RESET + hospedagens.get(i).getNomeFantasia());
                }

                System.out.println(LocalDateTime.now().format(formatter) +
                        ANSI_GREEN + "\n - [INFO]" +
                        "[SYSTEM]" + ANSI_RESET +
                        " - Inserção da planilha " + arquivo.getName() + " finalizada\n");

                if(qtdRegistroIncompleto != 0){
                    System.out.print(LocalDateTime.now().format(formatter) + ANSI_YELLOW + " - [WARN]" + "[SYSTEM]" + ANSI_RESET);
                    System.out.printf(" - %d registros possuem dados incompletos\n\n", qtdRegistroIncompleto);
                }

                System.out.print(LocalDateTime.now().format(formatter) + ANSI_BLUE + " - [INFO]" + "[SYSTEM]" + ANSI_RESET);
                System.out.printf(" - %d registros encontrados\n\n", hospedagens.size());

                System.out.println(LocalDateTime.now().format(formatter) +
                        ANSI_GREEN + " - [INFO]" +
                        "[SYSTEM]" + ANSI_RESET +
                        " - Registrando no banco que a planilha já foi lida\n");

                System.out.println(LocalDateTime.now().format(formatter) +
                        ANSI_BLUE + " - [INFO]" +
                        "[SYSTEM]" + ANSI_RESET +
                        " - Verificando se há mais novas planilhas\n");

                insert.gravarNomeDePlanilhaLida(arquivo.getName());
            }
        }

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_YELLOW + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Nenhuma planilha nova encontrada\n");

        System.out.println(LocalDateTime.now().format(formatter) +
                ANSI_BLUE + " - [INFO]" +
                "[SYSTEM]" + ANSI_RESET +
                " - Programa encerrado" + "\n");
    }

}
