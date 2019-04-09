package persistence;

import models.Extrato;
import models.Transacao;
import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransacaoDAOTeste {

    //instancias de extrato e transacao retiradas do arquivo exemplo para testes
    private final String extratoTxt = "012345678912018112920180925201809250017799FICTI01A                    002.002c";
    private final String transacaoTxt = "11234567891201809252018092513173601016831815412345678910111213141516171819202000001              00000000001000000000000098M1 1 012018102500000000000000000000000000000000000010000000000000000000000000002000000000000000000000000000000000000000000000000009801  03MASTERCARD                    ME1201                                1003413168    1234567890589082115720";

    @Test
    public void cadastraTransacao(){

        Extrato extrato = new Extrato(extratoTxt);
        Transacao transacao = new Transacao(transacaoTxt);
        TransacaoDAO transacaoDAO = new TransacaoDAO(ConexaoMySQL.getConexaoMySQL());

        System.out.println(transacaoDAO.save(transacao,extrato.getNumArquivo()));
        ConexaoMySQL.closeConnection();
    }

    @Test
    public void lerTransacoes(){

        Extrato extrato = new Extrato(extratoTxt);
        TransacaoDAO transacaoDAO = new TransacaoDAO(ConexaoMySQL.getConexaoMySQL());

        ArrayList<Transacao> listTransacoes = transacaoDAO.queryByFileNumberLimited(extrato.getNumArquivo(),0,3);

        for(Transacao t : listTransacoes){
            System.out.println(t.getCodigoTransacao() + " " + t.getDataConfirmacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss")));
        }

        ConexaoMySQL.closeConnection();
    }

    @Test
    public void contarTransacoes(){

        Extrato extrato = new Extrato(extratoTxt);
        TransacaoDAO transacaoDAO = new TransacaoDAO(ConexaoMySQL.getConexaoMySQL());

        System.out.println(Integer.toString(transacaoDAO.countInstancesByFile(extrato.getNumArquivo())));
    }

}
