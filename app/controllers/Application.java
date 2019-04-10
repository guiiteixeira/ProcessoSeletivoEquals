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
    
    public static void leitura(String nomeArquivo) {
        
        ConexaoMySQL.configureConexaoMySQL();
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        
        if(conn != null){
            BufferedReader reader = null;
            try {
                 reader = new BufferedReader(new FileReader(nomeArquivo));

                try{
                    while(reader.ready()){

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

                }catch(IOException ex){
                    renderText("Erro encontrado ao ler arquivo: " + nomeArquivo );
                }

            }catch(IOException ex){
                renderText("Erro encontrado ao ler arquivo: " + nomeArquivo );
            }
            finally{
                try{
                    reader.close();
                }catch(IOException ex){
                    System.out.println("Erro ao fechar arquivo");
                }
            }
            ConexaoMySQL.closeConnection();
            renderTemplate("public/index.html");
            
        }else{
            renderText("Impossivel conectar ao banco de dados, verifique os dados no arquivo db.stt!!!");
        }
    }
    
    public static void index(String numArquivo){
        
        ConexaoMySQL.configureConexaoMySQL();
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        
        if(conn != null){
            ExtratoDAO extratoDAO = new ExtratoDAO(conn);
            Application.extrato = extratoDAO.queryById(numArquivo);
            try {
                extratoDAO.getConnection().close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão");
            }
            ConexaoMySQL.closeConnection();
            renderTemplate("public/index.html");
        }else{
            renderText("Impossivel conectar ao banco de dados");
        }
    }

}
