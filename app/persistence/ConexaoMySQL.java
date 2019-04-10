/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private static final String DB_STT = "conf/db.stt";
    
    private static String serverName = "localhost"; //caminho do servidor do BD
    private static String username = "root"; //nome de um usuário do BD
    private static String password = ""; //senha de acesso
    private static String port = "3306"; //porta de acesso
    
    public static void configureConexaoMySQL(){
        BufferedReader reader = null;
        try{
        
            reader = new BufferedReader(new FileReader(DB_STT));
            
            String line = null;
            
            while(reader.ready()){
                line = reader.readLine();
                String[] conf = line.split("@");
                if(conf.length == 2){
                    switch(conf[0].trim()){
                        case "mysql-user":
                            username = conf[1].trim();
                        break;
                        case "mysql-pass":
                            password = conf[1].trim();
                        break;
                        case "mysql-addr":
                            serverName = conf[1].trim();
                        break;
                        case "mysql-port":
                            port = conf[1].trim();
                        break;
                        default:
                            System.out.println("Configuracao de banco de dados nao reconhecida" + conf[0].trim());
                        break;
                    }
                }
                else{
                    System.out.println("Configuracao de banco de dados nao reconhecida");
                }
            }
            
        }catch(IOException ex){
            System.out.println("Problemas na leitura do arquivo de configuracao do banco de dados!!!");
        }finally{
            
            try{
                reader.close();
            }catch(IOException ex){
                System.out.println("Problemas ao fechar leitor do arquivo de configuracao do banco de dados!!!");
            }
        }
        
    }
    


    /**
     * Este metodo conecta o banco de dados e retorna a conexao
     *
     * @return Connection - conexao com o banco de dados
     */
    public static Connection getConexaoMySQL() {
        if(connection != null){
            ConexaoMySQL.closeConnection();
        }
        try {
            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.cj.jdbc.Driver";                        
            Class.forName(driverName);
            // Configurando a conexão com um banco de dados//
                
            String mydatabase = "Equals";        //nome do banco de dados

            String url = "jdbc:mysql://" + serverName + ":" + port + "/" + mydatabase + "?useTimezone=true&serverTimezone=UTC";
                
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
        return status;
    }
 

    
}
