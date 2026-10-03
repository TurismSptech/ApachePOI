package com.tourwise;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm:ss");


        //definir caminho do arquivo
        String arquivo = "meios-de-hospedagem-2-trimestre-2026.xlsx";

        //ler o arquivo
        System.out.print("Iniciando leitura. | " + LocalDateTime.now().format(formatter));
        List<Hospedagem> hospedagens = new LeitorPlanilha().ler(arquivo);
        System.out.println(hospedagens.size() + " hospedagens lidas" + "\n");


        //inserir no banco **TO DO**
        System.out.println("Inserindo os dados no banco. | " + LocalDateTime.now().format(formatter) + "\n");
        for (int i = 0; i < 5; i++) {
            System.out.println(hospedagens.get(i) + "");
        }
        System.out.println("\nInsercao finalizada. | " + LocalDateTime.now().format(formatter) + "\n");
    }
}
