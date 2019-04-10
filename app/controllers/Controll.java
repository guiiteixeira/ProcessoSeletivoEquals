/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.Extrato;
import models.Transacao;
import persistence.ConexaoMySQL;
import persistence.ExtratoDAO;
import persistence.TransacaoDAO;
import play.mvc.*;

/**
 *
 * @author teixeira
 */
public class Controll extends Controller{
    
    //formatadores de data 
    private final static String DATE = "dd/MM/uuuu";
    private final static String DATE_TIME = "dd/MM/uuuu - HH:mm:ss";
    
    /**
     * Este metodo gera um html com as informacoes do extrato
     */
    public static void buscaExtrato(){
                
        Extrato extrato = Application.extrato;
        
        //gera html com dados do extrato
        String html = "<div class=\"col-md-1\"></div>\n" +
"          <div class=\"col-md-6\">\n" +
"            <br>\n" +
"            <h5><strong>Número do extrato: " + extrato.getNumArquivo() + "</strong></h5>\n" +
"            <h5>Número do estabelecimento: " + new String(extrato.getNumeroEstabelecimento()) + "</h5>\n" +
"            <h5>Data de Processamento: " + extrato.getDataProcessamento().format(DateTimeFormatter.ofPattern(DATE)) + "</h5>\n" +
"            <h5>Período do extrato: " + extrato.getPeriodo()[0].format(DateTimeFormatter.ofPattern(DATE)) + " até " + extrato.getPeriodo()[1].format(DateTimeFormatter.ofPattern(DATE)) + "</h5>\n" +
"          </div>\n" +
"          <div class=\"col-md-5\">\n" +
"            <br>\n" +
"            <h5>Empresa adquirente: " + new String(extrato.getEmpresaAdquirente()) + "</h5>\n" +
"            <h5>Tipo de extrato: " + Integer.toString(extrato.getTipoExtrato()) + "</h5>\n" +
"            <h5>Versão layout: " + new String(extrato.getVersaoLayout()) + "</h5>\n" +
"            <h5>Versão release: " + new String(extrato.getVersaoRelease()) + "</h5>\n" +
"          </div>";
        
        // responde a requisicao com o html gerado
        renderText(html);
        
    }
    
    /**
     * Este metodo busca transacoes no banco de dados e gera um html com as informacoes de cada transacao buscada
     */
    public static void buscaTransacoes(){
    
        //Abre conexao com o banco de dados
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        TransacaoDAO transacaoDAO = new TransacaoDAO(conn);
        ArrayList<Transacao> listTransacoes;
        String html = "";
        
        //seta parametros de leitura
        int limit = 100;
        int offset = 0;
        listTransacoes = transacaoDAO.queryByFileNumberLimited(Application.extrato.getNumArquivo(),offset,limit);

        while(listTransacoes.size() > 0){

            //para cada transacao gera uma linha de tabela html com os dados
            for(Transacao transacao : listTransacoes){

                html += "          <tr>\n" +
                        "            <td scope=\"col\">" + transacao.getCodigoTransacao() + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCodigoCliente()) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getDataInicial().format(DateTimeFormatter.ofPattern(DATE)) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getDataConfirmacao().format(DateTimeFormatter.ofPattern(DATE_TIME)) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getTipoEvento()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getTipoTransacao()) + "</td>\n" +
                        "            <td scope=\"col\">";
                
                if(transacao.getNumSerieLeitor() != null){
                    html += new String(transacao.getNumSerieLeitor());
                }
                html += "</td>\n" +
                        "            <td scope=\"col\">";
                if(transacao.getLeitor() > -1){
                    html += Integer.toString(transacao.getLeitor());
                }
                html += "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCodigoPedido()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getValorTotal()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getValorParcela()) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getPagamento() + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getPlano()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getParcelaLiberada()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getQteParcelas()) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getDataPrevisaoPagamento().format(DateTimeFormatter.ofPattern(DATE)) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTaxaParcelamentoComprador()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTarifaBoletoComprador()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTaxaParcelamentoVendedor()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTarifaBoletoVendedor()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTaxaIntermediacao()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getTarifaIntermediacao()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getValorOriginal()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getValorLiquido()) + "</td>\n" +
                        "            <td scope=\"col\">" + Float.toString(transacao.getRepasseAplicacao()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getStatusPagamento()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getMeioPagamento()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getInstituicaoFinanceira()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCanalEntrada()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getMeioCaptura()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getNumeroLogico()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getNSU()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCartaoBin()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCartaoHolder()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCodigoAutorizacao()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCodigoCV()) + "</td>\n" +
                        "\n" +
                        "          </tr>\n";

            }

            offset += limit;
            listTransacoes = transacaoDAO.queryByFileNumberLimited(Application.extrato.getNumArquivo(),offset,limit);
        }

        //fecha conexao com o banco de dados
        ConexaoMySQL.closeConnection();
        //responde requisicao com o html gerado
        renderText(html);
    
    }
    
    
}
