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

                try{
                    while(reader.ready()){
                        //lê uma linha
                        String line = reader.readLine();

                        switch (line.charAt(0)){
                            //header
                            case '0':
                                Application.extrato = new Extrato(line);
                                ExtratoDAO extratoDAO = new ExtratoDAO(conn);
                                extratoDAO.save(extrato);
                            break;
                            //detalhe
                            case '1':
                                Transacao transacao = new Transacao(line);
                                TransacaoDAO transacaoDAO = new TransacaoDAO(conn);
                                transacaoDAO.save(transacao, extrato.getNumArquivo());
                            break;
                        }

                    }

                }catch(IOException ex){
                    //mostra msg de erro
                    renderText("Erro encontrado ao ler arquivo: " + nomeArquivo );
                }

            }catch(IOException ex){
                //mostra msg de erro
                renderText("Erro encontrado ao ler arquivo: " + nomeArquivo );
            }
            finally{
                try{
                    //fecha leitor de arquivo de texto
                    reader.close();
                }catch(IOException ex){
                    System.out.println("Erro ao fechar arquivo");
                }
            }
            //fecha conexao com o banco de dados
            ConexaoMySQL.closeConnection();
            //mostra o html
            renderTemplate("public/index.html");
            
        }else{
            //mostra msg de erro
            renderText("Impossivel conectar ao banco de dados, verifique os dados no arquivo db.stt!!!");
        }
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
            //fecha conexao com o banco de dados
            ConexaoMySQL.closeConnection();
            //mostra html
            renderTemplate("public/index.html");
        }else{
            //mostra msg de erro
            renderText("Impossivel conectar ao banco de dados");
        }
    }

}
