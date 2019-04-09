package persistence;

import models.Extrato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExtratoDAO {

    //conexao a ser usada para acessar o banco de dados
    private Connection conn;
    //formatador de data (padrao sql)
    private DateTimeFormatter DATE = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    /**
     * Este metodo constroi um extratoDAO com sua conexao definida
     *
     * @param conn Connection
     */
    public ExtratoDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Este metodo eh responsavel por executar um insert na tabela 'Extrato' do banco de dados
     *
     * @param extrato Extrato
     * @return boolean - Se inseriu ou nao no banco
     */
    public boolean save(Extrato extrato){
        //constroi um statement contendo o insert dos dados de extrato
        String sql = "INSERT INTO Extrato VALUES('" + extrato.getNumArquivo() + "','" + extrato.getDataProcessamento().format(DATE) +
                    "','" + extrato.getPeriodo()[0].format(DATE) + "','" + extrato.getPeriodo()[1].format(DATE) + "'," +
                    Integer.toString(extrato.getTipoExtrato()) + ",'" + new String(extrato.getNumeroEstabelecimento()) + "','" +
                    new String(extrato.getVersaoLayout()) + "','" + new String(extrato.getVersaoRelease()) + "','" +
                    new String(extrato.getEmpresaAdquirente()) + "');";

        try {
            //prepara o statement e executa
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;

        }
    }

    /**
     * Este metodo eh responsavel por buscar um extrato no banco de dados pelo seu numero de identificacao
     *
     * @param numArquivo String
     * @return Extrato
     */
    public Extrato queryById(String numArquivo){
        String sql = "SELECT * FROM Extrato WHERE numArquivo = '" + numArquivo + "';";

        try {
            //prepara o statement e executa
            ResultSet result = null;
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            Extrato extrato = null;

            //se houver resultado valido
            if(result.next()){

                LocalDate[] periodo = new LocalDate[2];
                String date = result.getString("dataInicial");
                periodo[0] = LocalDate.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd"));
                date = result.getString("dataFinal");
                periodo[1] = LocalDate.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                date = result.getString("dataProcessamento");
                LocalDate dataProcessamento = LocalDate.parse(date.subSequence(0,date.length()),DateTimeFormatter.ofPattern("uuuu-MM-dd"));

                //constroi o extrato
                extrato = new Extrato(result.getString("numArquivo"),dataProcessamento,
                                            result.getString("empresaAdquirente").toCharArray(),result.getInt("tipoExtrato"),
                                            result.getString("numEstabelecimento").toCharArray(),periodo,result.getString("Layout").toCharArray(),
                                            result.getString("Release").toCharArray());

                //retorna o extrato

            }
            return extrato;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        }
    }
    
    public Connection getConnection(){
        return this.conn;
    }

}
