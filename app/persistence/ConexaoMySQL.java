/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme Teixeira
 */
public class ConexaoMySQL {

    //Conexao com o banco de dados
    private static Connection connection = null;

    //status da conexao
    private static String status = "Não Conectado" ;


    /**
     * Este metodo conecta o banco de dados e retorna a conexao
     *
     * @return Connection - conexao com o banco de dados
     */
    public static Connection getConexaoMySQL() {
        
        try {
            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.cj.jdbc.Driver";                        
            Class.forName(driverName);
            // Configurando a conexão com um banco de dados//
            String serverName = "localhost";    //caminho do servidor do BD
            String mydatabase = "Equals";        //nome do banco de dados
            String url = "jdbc:mysql://" + serverName + ":3306/" + mydatabase + "?useTimezone=true&serverTimezone=UTC";
            String username = "default";        //nome de um usuário do BD
            String password = "";      //senha de acesso
            connection = DriverManager.getConnection(url, username, password);

           //Testa a conexão//
            if (connection != null) {
                status = ("STATUS--->Conectado com sucesso!");
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
            }

            return connection;
        } catch (ClassNotFoundException e) {  //Driver não encontrado
            status += "driver nao encontrado";
            System.out.println("O driver expecificado nao foi encontrado.");
            return null;
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            status += "nao foi possivel conectar ao banco";
            System.out.println(e.getMessage() + "\nNao foi possivel conectar ao Banco de Dados.");
            return null;
        }
    }

    /**
     * Este metodo fecha a conexao com o banco de dados
     */
    public static void closeConnection(){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "\nNao foi possivel fechar a conexao com o banco de dados");
            }
        }
    }
    
    public static String getStatus(){
        return ConexaoMySQL.status;
    }
 

    
}
