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

    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println(LocalDateTime.now().format(formatter) + " - Programa iniciado" + "\n");

        //definir caminho da pasta de leitura
        File caminho = new File("C:\\Users\\cilei\\Desktop\\S3");
        File[] arquivos = caminho.listFiles();

        InsertData insert = new InsertData();

        System.out.println(LocalDateTime.now().format(formatter) + " - Buscando planilhas já inseridas no BD\n");

        List<String> nomePlanilhasLidas = insert.buscarPlanilhasLidas();

        System.out.println(LocalDateTime.now().format(formatter) + " - Planilhas encontradas no BD: " + nomePlanilhasLidas.toString() + "\n");

        System.out.println(LocalDateTime.now().format(formatter) + " - Procurando novas planilhas em " + caminho + "\n");

        Boolean temPlanilhaNova = false;

        for (File arquivo : arquivos) {
            if (!nomePlanilhasLidas.contains(arquivo.getName())) {
                temPlanilhaNova = true;

                System.out.print(LocalDateTime.now().format(formatter) + " - Nova planilha encontrada: ");
                System.out.println(arquivo.getName() + "\n");

                String path = arquivo.getPath();

                System.out.println(LocalDateTime.now().format(formatter) + " - Iniciando gravação da planilha " + arquivo.getName() + " no BD \n");

                List<Hospedagem> hospedagens = new LeitorPlanilha().ler(path);

                for (int i = 0; i < hospedagens.size(); i++) {
                    insert.insertHosp(hospedagens.get(i));
                }

                System.out.println(LocalDateTime.now().format(formatter) + " - Inserção da planilha " + arquivo.getName() + " finalizada\n");

                System.out.println(LocalDateTime.now().format(formatter) + " - Registrando no banco que a planilha já foi lida\n");

                System.out.println(LocalDateTime.now().format(formatter) + " - Verificando se há mais novas planilhas\n");

                insert.gravarNomeDePlanilhaLida(arquivo.getName());
            }
        }

        System.out.println(LocalDateTime.now().format(formatter) + " - Nenhuma planilha nova encontrada");
    }


}
