package com.tourwise.database;

import com.tourwise.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class InsertData {
    private ConnectionFactory connectionFactory;

    public InsertData() {
        this.connectionFactory = new ConnectionFactory();
    }


    public void insertHosp(Hospedagem h) {
        JdbcTemplate template = connectionFactory.getJdbcTemplate();

        template.update("INSERT INTO idioma (idioma) VALUES (?)", h.getIdiomas());
        Integer fk_idioma = template.queryForObject("SELECT MAX(id_idioma) FROM idioma", Integer.class);

        template.update("INSERT INTO localizacao (endereco, municipio, uf) VALUES (?,?,?)", h.getEndereco(), h.getMunicipio(), h.getUf());
        Integer fk_localizacao = template.queryForObject("SELECT MAX(id_localizacao) FROM localizacao", Integer.class);

        template.update("INSERT INTO cnae (codigo) VALUES (?)", h.getCnaes());
        Integer fk_cnae = template.queryForObject("SELECT MAX(id_cnae) FROM cnae", Integer.class);

        template.update("INSERT INTO hospedagem (cnpj, tipo_hospedagem, nome_fantasia, email_comercial, uni_habit, leitos, uni_habit_acess, leito_acess, dt_abertura, website, porte)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                h.getCnpj(), h.getTipoHospedagem(), h.getNomeFantasia(), h.getEmailComercial(), h.getUniHabit(),
                h.getLeitos(), h.getUniHabit(), h.getLeitosAcess(), h.getDtAbertura(), h.getWebsite(),
                h.getPorte()
        );


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

}
