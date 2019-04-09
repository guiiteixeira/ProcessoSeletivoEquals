/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;


/**
 *
 * @author Guilherme Teixeira
 */

public class Extrato {
    
    //Strings
    private String numArquivo;
    
    //inteiros
    private int tipoExtrato;
    
    //API time
    private LocalDate dataProcessamento;
    private LocalDate[] periodo;
    
    //sequencias de caracteres
    private char[] empresaAdquirente;
    private char[] numeroEstabelecimento;
    private char[] versaoLayout;
    private char[] versaoRelease;

    /**Construtor de Extrato a partir de uma linha lida do arquivo
    * @param line String - Header lido do arquivo extrato
    */
    public Extrato(String line){
        /*Este método pega a substring correspondente a cada tipo de dado, com base no layout do extrato,
        * e transforma no tipo correspondente do dado
        */
        
        //Strings
        this.numArquivo = line.substring(35, 42);
        
        //inteiros
        this.tipoExtrato = Integer.parseInt(line.substring(47, 49));
        
        //API time
        this.dataProcessamento = LocalDate.parse(line.substring(11, 19),BASIC_ISO_DATE);
        this.periodo = new LocalDate[2];
        periodo[0] = LocalDate.parse(line.substring(19, 27),BASIC_ISO_DATE);
        periodo[1] = LocalDate.parse(line.substring(27, 35),BASIC_ISO_DATE);
        
        //sequencias de caracteres
        this.numeroEstabelecimento = line.substring(1, 11).toCharArray();
        this.empresaAdquirente = line.substring(42, 47).toCharArray();
        this.versaoLayout = line.substring(70, 73).toCharArray();
        this.versaoRelease = line.substring(73, 78).toCharArray();        
    }

      /**Construtor de Extrato
    * @param numArquivo String 
    * @param dataProcessamento LocalDate 
    * @param empresaAdquirente char[]
    * @param tipoExtrato int 
    * @param numeroEstabelecimento char[] 
    * @param periodo LocalDate[] 
    * @param versaoLayout char[]
    * @param versaoRelease char[]
    */
    public Extrato(String numArquivo, LocalDate dataProcessamento, char[] empresaAdquirente, int tipoExtrato, char[] numeroEstabelecimento,
                   LocalDate[] periodo, char[] versaoLayout, char[] versaoRelease) {
        //settando os atributos
        this.numArquivo = numArquivo;
        this.dataProcessamento = dataProcessamento;
        this.empresaAdquirente = empresaAdquirente;
        this.tipoExtrato = tipoExtrato;
        this.numeroEstabelecimento = numeroEstabelecimento;
        this.periodo = periodo;
        this.versaoLayout = versaoLayout;
        this.versaoRelease = versaoRelease;
    }

    public String getNumArquivo() {
        return numArquivo;
    }

    public void setNumArquivo(String numArquivo) {
        this.numArquivo = numArquivo;
    }

    public LocalDate getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDate dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public char[] getEmpresaAdquirente() {
        return empresaAdquirente;
    }

    public void setEmpresaAdquirente(char[] empresaAdquirente) {
        this.empresaAdquirente = empresaAdquirente;
    }

    public int getTipoExtrato() {
        return tipoExtrato;
    }

    public void setTipoExtrato(int tipoExtrato) {
        this.tipoExtrato = tipoExtrato;
    }

    public char[] getNumeroEstabelecimento() {
        return numeroEstabelecimento;
    }

    public void setNumeroEstabelecimento(char[] numeroEstabelecimento) {
        this.numeroEstabelecimento = numeroEstabelecimento;
    }

    public LocalDate[] getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalDate[] periodo) {
        this.periodo = periodo;
    }

    public char[] getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(char[] versaoLayout) {
        this.versaoLayout = versaoLayout;
    }

    public char[] getVersaoRelease() {
        return versaoRelease;
    }

    public void setVersaoRelease(char[] versaoRelease) {
        this.versaoRelease = versaoRelease;
    }



}
