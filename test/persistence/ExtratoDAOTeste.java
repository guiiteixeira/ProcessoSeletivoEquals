package persistence;

import models.Extrato;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ExtratoDAOTeste {

    //instancia de extrato retirada do arquivo exemplo para testes
    private final String extratoTxt = "012345678912018112920180925201809250017799FICTI01A                    002.002c";
    private final String idExtrato = "0017799";

    @Test
    public void cadastraExtrato(){

        Extrato extrato = new Extrato(extratoTxt);
        ExtratoDAO extratoDAO = new ExtratoDAO(ConexaoMySQL.getConexaoMySQL());

        System.out.println(extratoDAO.save(extrato));

    }

    @Test
    public void buscarExtrato(){

        ExtratoDAO extratoDAO = new ExtratoDAO(ConexaoMySQL.getConexaoMySQL());
        Extrato extrato = extratoDAO.queryById(idExtrato);

        System.out.println(extrato.getDataProcessamento().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));

    }
}
