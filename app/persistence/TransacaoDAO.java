package persistence;

import models.Transacao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class TransacaoDAO {

    //conexao a ser usada para acessar o banco de dados
    private Connection conn;
    //formatador de data (padrao sql)
    private DateTimeFormatter DATE = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    //formatador de data e hora (padrao sql)
    private DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    /**
     * Este metodo constroi um transacaoDAO com sua conexao definida
     *
     * @param conn Connection
     */
    public TransacaoDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Este metodo eh responsavel por executar um insert na tabela 'Transacao' do banco de dados
     *
     * @param transacao Transacao
     * @return boolean - Se inseriu ou nao no banco
     */
    public boolean save(Transacao transacao,String numArquivo){
        //constroi um statement contendo o insert dos dados de transacao
        String sql = "INSERT INTO Transacao VALUES('" + transacao.getCodigoTransacao() + "','" + transacao.getDataInicial().format(DATE) +
                    "','" + transacao.getDataConfirmacao().format(DATE_TIME) + "','";
        if(transacao.getNumSerieLeitor() != null){
            sql += new String(transacao.getNumSerieLeitor());
        }else{
            sql += "null";
        }
            sql += "','" + new String(transacao.getCodigoCliente()) + "'," + Integer.toString(transacao.getTipoEvento()) + ",'" + new String(transacao.getCodigoPedido()) +
                    "'," + Float.toString(transacao.getValorTotal()) + "," + Float.toString(transacao.getValorParcela()) + ",'" + transacao.getPagamento() + "','" +
                    new String(transacao.getPlano()) + "','" + new String(transacao.getParcelaLiberada()) + "'," + Integer.toString(transacao.getQteParcelas()) + ",'" +
                    transacao.getDataPrevisaoPagamento().format(DATE) + "'," + Float.toString(transacao.getTaxaParcelamentoComprador()) + "," +
                    Float.toString(transacao.getTarifaBoletoComprador()) + "," + Float.toString(transacao.getTaxaParcelamentoVendedor()) + "," +
                    Float.toString(transacao.getTarifaBoletoVendedor()) + "," + Float.toString(transacao.getTaxaIntermediacao()) + "," +
                    Float.toString(transacao.getTarifaIntermediacao()) + "," + Float.toString(transacao.getRepasseAplicacao()) + "," +
                    Float.toString(transacao.getValorOriginal()) + "," + Float.toString(transacao.getValorLiquido()) + "," + Integer.toString(transacao.getTipoTransacao()) +
                    "," + Integer.toString(transacao.getStatusPagamento()) + "," + Integer.toString(transacao.getMeioPagamento()) + ",'" +
                    new String(transacao.getCanalEntrada()) + "',";

        //confere se existe leitor
        if(transacao.getLeitor() > -1){
            sql += Integer.toString(transacao.getLeitor());
        }
        else{
            sql += "null";
        }
        //continua construindo statement
        sql += ",'" + new String(transacao.getInstituicaoFinanceira()) + "'," + Integer.toString(transacao.getMeioCaptura()) + ",'" +
                new String(transacao.getNumeroLogico()) + "','" + new String(transacao.getNSU()) + "','" + new String(transacao.getCartaoBin()) +
                "','" + new String(transacao.getCartaoHolder()) + "','" + new String(transacao.getCodigoAutorizacao()) + "','" +
                new String(transacao.getCodigoCV()) + "','" + numArquivo + "');";


        try {
            //prepara statement e executa
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;

        }
    }

    /**
     * Este metodo eh resposavel por buscar transacoes a partir de um numero de arquivo que corresponde a um extrato.
     * A busca eh limitada por quantidade de registros, iniciando de offset.
     *
     * @param numArquivo String
     * @param offset int
     * @param quantidade int
     * @return ArrayList<Transacao> - lista contendo 'quantidade' transacoes
     */
    public ArrayList<Transacao> queryByFileNumberLimited(String numArquivo,int offset,int quantidade){
        //cria a query
        String sql = "SELECT * FROM Transacao WHERE Extrato_numArquivo = '" + numArquivo + "' LIMIT " + Integer.toString(quantidade) + " OFFSET " +
                    Integer.toString(offset) + ";";

        try {
            //prepara statement e executa
            ResultSet results = null;
            //cria lista de Transacoes
            ArrayList<Transacao> listTransacoes = new ArrayList<Transacao>();
            PreparedStatement statement = conn.prepareStatement(sql);
            results = statement.executeQuery();

            //para todos os resultados faça:
            while(results.next()){

                //confere se existe um leitor
                int leitor = -1;
                char[] numSerieLeitor = null;
                if(results.getObject("leitor") != null){
                    leitor = results.getInt("leitor");
                    numSerieLeitor = results.getString("numSerieLeitor").toCharArray();
                }

                String date = results.getString("dataInicial");
                LocalDate dataInicial = LocalDate.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                date = results.getString("dataConfirmacao");
                LocalDateTime dataConfirmacao = LocalDateTime.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));

                date = results.getString("dataPrevisaoPagamento");
                LocalDate dataPrevisao = LocalDate.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                //chama o construtor de Transacao com todos os parametros
                Transacao transacao = new Transacao(results.getString("codTransacao"),dataInicial,
                                                    dataConfirmacao,results.getString("codCliente").toCharArray(),
                                                    results.getInt("tipoEvento"),results.getString("codPedido").toCharArray(),results.getFloat("valorTotal"),
                                                    results.getFloat("valorParc"),results.getString("pagamento").charAt(0),
                                                    results.getString("plano").toCharArray(),results.getString("parcelaLiberada").toCharArray(),
                                                    results.getInt("numParcelas"),dataPrevisao,
                                                    results.getFloat("taxaParcelamentoComp"),results.getFloat("tarifaBoletoComp"),
                                                    results.getFloat("taxaParcelamentoVend"),results.getFloat("tarifaBoletoComp"),
                                                    results.getFloat("repasseAplicacao"),results.getFloat("valorOriginal"),
                                                    results.getFloat("taxaIntermediacao"),results.getFloat("tarifaIntermediacao"),
                                                    results.getFloat("valorLiquido"),results.getInt("tipoTransacao"),
                                                    results.getInt("statusPagamento"),results.getInt("meioPagamento"),
                                                    results.getString("canalEntrada").toCharArray(),results.getString("instituicaoFinanceira").toCharArray(),
                                                    results.getInt("meioCaptura"),results.getString("numLogico").toCharArray(),
                                                    results.getString("NSU").toCharArray(),results.getString("cartaoBin").toCharArray(),
                                                    results.getString("cartaoHolder").toCharArray(),results.getString("codAutorizacao").toCharArray(),
                                                    results.getString("codCV").toCharArray(),leitor,numSerieLeitor);
                //adiciona a transacao na lista
                listTransacoes.add(transacao);
            }
            return listTransacoes;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        }
    }

    /**
     * Este metodo conta quantas transacoes provenientes de um determinado arquivo ha no banco de dados
     *
     * @param numArquivo String
     * @return int
     */
    public int countInstancesByFile(String numArquivo){
        //cria a query
        String sql = "SELECT COUNT(codTransacao) AS transacoes FROM TRANSACAO WHERE Extrato_numArquivo = '" + numArquivo + "';";

        try {
            //prepara statement e executa
            ResultSet result = null;
            int qte = -1;
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery();

            if(result.next()){
                qte = result.getInt("transacoes");
            }

            //retorna resultado
            return qte;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return -1;

        }

    }
    
    public Connection getConnection(){
        return this.conn;
    }

}
