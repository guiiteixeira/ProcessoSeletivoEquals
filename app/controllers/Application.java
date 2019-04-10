package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import play.*;
import play.mvc.*;

import java.util.*;
import java.util.logging.Level;

import models.*;
import persistence.ConexaoMySQL;
import persistence.ExtratoDAO;
import persistence.TransacaoDAO;

public class Application extends Controller {
   
    public static Extrato extrato;
    
    /**
     * Este metodo lê um arquivo exemplo, salva os dados no banco de dados e
     * abre o html
     * 
     * @param nomeArquivo String
     */
    public static void leitura(String nomeArquivo) {
        
        //Abre a conexao com o banco
        ConexaoMySQL.configureConexaoMySQL();
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        
        if(conn != null){
            
            BufferedReader reader = null;
            try {
                //Abre o leitor de arquivo de texto
                reader = new BufferedReader(new FileReader(nomeArquivo));

                
                while(reader.ready()){
                    //lê uma linha
                    String line = reader.readLine();

                    switch (line.charAt(0)){
                        case '0':
                            Application.extrato = new Extrato(line);
                            ExtratoDAO extratoDAO = new ExtratoDAO(conn);
                            extratoDAO.save(extrato);
                        break;
                        case '1':
                            Transacao transacao = new Transacao(line);
                            TransacaoDAO transacaoDAO = new TransacaoDAO(conn);
                            transacaoDAO.save(transacao, extrato.getNumArquivo());
                        break;
                    }

                }

                if(extrato != null){
                    //mostra html
                    renderTemplate("public/index.html");
                }
                else{
                    renderText("Houve algum problema ao ler extrato do arquivo: " + nomeArquivo + 
                                "\nCaso você queira apenas acessar um extrato no banco de dados, "
                                + "acesse 'localhost:9000/NUMERO_EXTRATO'");
                }



            }catch(IOException ex){
                //mostra msg de erro
                renderText("Erro encontrado ao ler arquivo: " + nomeArquivo +
                            "\nCaso você queira apenas acessar um extrato no banco de dados, "
                                + "acesse 'localhost:9000/NUMERO_EXTRATO'");
            }
            finally{
                try{
                    if(reader != null){
                        //fecha leitor de arquivo de texto
                        reader.close();
                    }
                }catch(IOException ex){
                    System.out.println("Erro ao fechar arquivo");
                }
            }         
           
            
        }else{
            //mostra msg de erro
            renderText("Impossivel conectar ao banco de dados, verifique os dados no arquivo db.stt!!!");
        }
        //fecha conexao com o banco de dados
        ConexaoMySQL.closeConnection();
    }
    
    /**
     * Este metodo busca os dados no banco de dados e abre o html
     * 
     * @param numArquivo String
     */
    public static void index(String numArquivo){
        
        //Abre a conexao com o banco de dados
        ConexaoMySQL.configureConexaoMySQL();
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        
        if(conn != null){
            //busca extrato
            ExtratoDAO extratoDAO = new ExtratoDAO(conn);
            Application.extrato = extratoDAO.queryById(numArquivo);
            if(extrato != null){
                //mostra html
                renderTemplate("public/index.html");
            }
            else{
                renderText("Houve algum problema ao acessar o extrato de numero: " + numArquivo + 
                            "\nCaso você queira ler um arquivo, acesse 'localhost:9000/leitura/NOME_DO_ARQUIVO'");
            }
        }else{
            //mostra msg de erro
            renderText("Impossivel conectar ao banco de dados, verifique os dados no arquivo db.stt!!!");
        }
        //fecha conexao com o banco de dados
            ConexaoMySQL.closeConnection();
    }

}
