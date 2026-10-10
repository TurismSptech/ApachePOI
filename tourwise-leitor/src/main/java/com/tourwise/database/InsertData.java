package com.tourwise.database;

import com.tourwise.model.Cnae;
import com.tourwise.model.Hospedagem;
import com.tourwise.model.Idioma;
import com.tourwise.model.Localizacao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class InsertData {
    private ConnectionFactory connectionFactory;

    public InsertData(){
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
        template.update("INSERT INTO hospedagem (cnpj, tipo_hospedagem, nome_fantasia, email_comercial, uni_habit, leitos, uni_habit_acess, leito_acess, dt_abertura, website, porte)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                h.getCnpj(), h.getTipoHospedagem(), h.getNomeFantasia(), h.getEmailComercial(), h.getUniHabit(),
                h.getLeitos(), h.getUniHabit(), h.getLeitosAcess(), h.getDtAbertura(), h.getWebsite(),
                h.getPorte()
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

        //Add Endereço da Hospedagem
        template.update("INSERT INTO localizacao (endereco, municipio, uf, fk_hospedagem) VALUES (?,?,?,?)", h.getEndereco(), h.getMunicipio(), h.getUf(), fk_hosp);

    }

}
