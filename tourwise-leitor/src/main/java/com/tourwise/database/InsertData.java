package com.tourwise.database;

import com.tourwise.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.*;

public class InsertData {
    private ConnectionFactory connectionFactory;

    public InsertData() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void insertHosp(Hospedagem h) {
        JdbcTemplate template = connectionFactory.getJdbcTemplate();

        List<String> idiomas = template.queryForList("SELECT idioma FROM idioma", String.class);
        List<String> idiomasAtuais = new ArrayList<>();

        //Insert Idiomas
        if (h.getIdiomas() != null) {
            idiomasAtuais = List.of(h.getIdiomas().split("\\|"));

            for (int i = 0; i < idiomasAtuais.size(); i++) {
                if (!idiomas.contains(idiomasAtuais.get(i))) {
                    template.update("INSERT INTO idioma (idioma) VALUES (?)", idiomasAtuais.get(i));
                }
            }
        }

        List<String> cnaes = template.queryForList("SELECT codigo FROM cnae", String.class);

        List<String> cnaesAtuais = new ArrayList<>();

        //Insert cnae's
        if (h.getCnaes() != null) {
            Set<String> cnaesLimpos = new HashSet<>(Arrays.asList(h.getCnaes().split("\\|")));
            cnaesAtuais.addAll(cnaesLimpos);

            //cnaesAtuais = List.of(h.getCnaes().split("\\|"));
            String cnaeAnterior = null;

            for (int i = 0; i < cnaesAtuais.size(); i++) {
                if (!cnaes.contains(cnaesAtuais.get(i)) && (!cnaesAtuais.get(i).equalsIgnoreCase(cnaeAnterior) || cnaeAnterior == null)) {
                    template.update("INSERT INTO cnae (codigo) VALUES (?)", cnaesAtuais.get(i));

                    cnaeAnterior = cnaesAtuais.get(i);
                }
            }
        }

        //Insert hospedagem
        template.update("INSERT INTO hospedagem (cnpj, tipo_hospedagem, nome_fantasia, email_comercial, uni_habit, leitos, uni_habit_acess, leito_acess, dt_abertura, website, porte, validade_certificado)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                h.getCnpj(), h.getTipoHospedagem(), h.getNomeFantasia(), h.getEmailComercial(), h.getUniHabit(),
                h.getLeitos(), h.getUniHabit(), h.getLeitosAcess(), h.getDtAbertura(), h.getWebsite(),
                h.getPorte(), h.getValidade_certificado()
        );

        Integer fk_hosp = template.queryForObject("SELECT MAX(id_hospedagem) FROM hospedagem", Integer.class);

        //Add Idiomas da Hospedagem
        for (int i = 0; i < idiomasAtuais.size(); i++) {
            Integer fk_idioma = template.queryForObject("SELECT id_idioma FROM idioma WHERE idioma = ?", Integer.class, idiomasAtuais.get(i));

            template.update("INSERT INTO idiomas_hospedagem (fk_idioma, fk_hospedagem) VALUES (?,?)", fk_idioma, fk_hosp);
        }

        //Add cnaes da Hospedagem
        for (int i = 0; i < cnaesAtuais.size(); i++) {
            Integer fk_cnae = template.queryForObject("SELECT id_cnae FROM cnae WHERE codigo = ?", Integer.class, cnaesAtuais.get(i));

            template.update("INSERT INTO cnaes_hospedagem (fk_cnae, fk_hospedagem) VALUES (?,?)", fk_cnae, fk_hosp);
        }

        //Add Telefone da Hospedagem
        if(h.getTelefone() != null){
        template.update("INSERT INTO telefone (numero, fk_hospedagem) VALUES (?,?)",
                h.getTelefone().replace("(" , "").replace(")", "").replace("-", "")
                , fk_hosp);

        }

        //Add Endereço da Hospedagem
        template.update("INSERT INTO localizacao (endereco, municipio, uf, fk_hospedagem) VALUES (?,?,?,?)", h.getEndereco(), h.getMunicipio(), h.getUf(), fk_hosp);

    }

    public List<String> buscarPlanilhasLidas() {
        JdbcTemplate template = connectionFactory.getJdbcTemplate();

        List<String> nomePlanilhasLidas = new ArrayList<>();

        List<Leitura> leituras = template.query("SELECT * FROM leitura;", new BeanPropertyRowMapper<>(Leitura.class));

        for (Leitura leitura : leituras) {
            nomePlanilhasLidas.add(leitura.getNome_arquivo());
        }

        return nomePlanilhasLidas;
    }

    public void gravarNomeDePlanilhaLida(String nome) {
        JdbcTemplate template = connectionFactory.getJdbcTemplate();

        template.update("INSERT INTO leitura (nome_arquivo) VALUES (?)", nome);
    }

    public boolean temTodosAtributosPreenchidos(Hospedagem h) throws IllegalAccessException {
        if (h == null) return false;

        Field[] campos = h.getClass().getDeclaredFields();
        for (Field campo : campos) {
            // Torna acessíveis campos privados
            campo.setAccessible(true);
            Object valor = campo.get(h);

            if (valor == null) {
                return false;
            }
            // Se for String, verifica se está em branco
            if (valor instanceof String && ((String) valor).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
