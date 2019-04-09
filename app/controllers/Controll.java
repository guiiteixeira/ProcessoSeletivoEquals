/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
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
    
    private final static String DATE = "dd/MM/uuuu";
    private final static String DATE_TIME = "dd/MM/uuuu - HH:mm:ss";
    
    public static void buscaExtrato(){
        
        ExtratoDAO extratoDAO = new ExtratoDAO(ConexaoMySQL.getConexaoMySQL());
        
        Extrato extrato = extratoDAO.queryById(Application.extrato.getNumArquivo());
        
        String html = "<div class=\"col-md-1\"></div>\n" +
"          <div class=\"col-md-6\">\n" +
"            <br>\n" +
"            <h5><strong>Número do arquivo: " + extrato.getNumArquivo() + "</strong></h5>\n" +
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
        
        try {
            extratoDAO.getConnection().close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão");
        }
        
        renderText(html);
        
    }
    
    public static void buscaTransacoes(){
    
        TransacaoDAO transacaoDAO = new TransacaoDAO(ConexaoMySQL.getConexaoMySQL());
        ArrayList<Transacao> listTransacoes;
        String html = "";
        
        int limit = 100;
        int offset = 0;
        listTransacoes = transacaoDAO.queryByFileNumberLimited(Application.extrato.getNumArquivo(),offset,limit);

        while(listTransacoes.size() > 0){

            for(Transacao transacao : listTransacoes){

                html += "          <tr>\n" +
                        "            <td scope=\"col\">" + transacao.getCodigoTransacao() + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getCodigoCliente()) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getDataInicial().format(DateTimeFormatter.ofPattern(DATE)) + "</td>\n" +
                        "            <td scope=\"col\">" + transacao.getDataConfirmacao().format(DateTimeFormatter.ofPattern(DATE_TIME)) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getTipoEvento()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getTipoTransacao()) + "</td>\n" +
                        "            <td scope=\"col\">" + new String(transacao.getNumSerieLeitor()) + "</td>\n" +
                        "            <td scope=\"col\">" + Integer.toString(transacao.getLeitor()) + "</td>\n" +
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

        try {
            transacaoDAO.getConnection().close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão");
        }    
        
        renderText(html);
    
    }
    
    public static void buscaNumArquivo(){
        renderText(Application.extrato.getNumArquivo());
    }
    
}
