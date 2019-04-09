/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 *
 * @author Guilherme Teixeira
 */
public class Transacao {
    
    //Strings
    private String codigoTransacao;
    
    //API time
    private LocalDateTime dataConfirmacao;
    private LocalDate dataInicial;
    private LocalDate dataPrevisaoPagamento;
    
    //atributos de ponto flutuante
    private float valorTotal;
    private float valorParcela;
    private float taxaParcelamentoComprador;
    private float tarifaBoletoComprador;
    private float taxaParcelamentoVendedor;
    private float tarifaBoletoVendedor;
    private float repasseAplicacao;
    private float valorOriginal;
    private float taxaIntermediacao;
    private float tarifaIntermediacao;
    private float valorLiquido;
    
    //inteiros
    private int tipoEvento;
    private int qteParcelas;
    private int tipoTransacao;
    private int statusPagamento;
    private int meioPagamento;
    private int leitor = -1;
    private int meioCaptura;
    
    //caracteres
    private char pagamento;
    
    //sequencias de caracteres
    private char[] codigoCliente;
    private char[] numSerieLeitor = null;
    private char[] codigoPedido;
    private char[] plano;
    private char[] parcelaLiberada;
    private char[] canalEntrada;
    private char[] instituicaoFinanceira;
    private char[] numeroLogico;
    private char[] NSU;
    private char[] cartaoBin;
    private char[] cartaoHolder;
    private char[] codigoAutorizacao;
    private char[] codigoCV;
    
    /**Construtor de Transacao a partir de uma linha lida do arquivo
    * @param line String - Detalhe lido do arquivo extrato
    */
    public Transacao(String line){
        /*Este método pega a substring correspondente a cada tipo de dado, com base no layout do extrato,
        * e transforma no tipo correspondente do dado
        */
        
        //Strings
        this.codigoTransacao = line.substring(45, 77);
        
        //API time
        this.dataPrevisaoPagamento = LocalDate.parse(line.substring(130, 138),BASIC_ISO_DATE);
        this.dataInicial = LocalDate.parse(line.substring(11, 19),BASIC_ISO_DATE);
        this.dataConfirmacao = LocalDateTime.parse(line.substring(19, 33),DateTimeFormatter.ofPattern("uuuuMMddHHmmss"));
        
        //pontos flutuantes
        this.valorTotal = (Float.parseFloat(line.substring(97, 110)) / 100);
        this.valorParcela = (Float.parseFloat(line.substring(110, 123)) / 100);
        this.taxaParcelamentoComprador = (Float.parseFloat(line.substring(138, 151)) / 100);
        this.tarifaBoletoComprador = (Float.parseFloat(line.substring(151, 164)) / 100);
        this.valorOriginal = (Float.parseFloat(line.substring(164, 177)) / 100);
        this.taxaParcelamentoVendedor = (Float.parseFloat(line.substring(177, 190)) / 100);
        this.taxaIntermediacao = (Float.parseFloat(line.substring(190, 203)) / 100);
        this.tarifaIntermediacao = (Float.parseFloat(line.substring(203, 216)) / 100);
        this.tarifaBoletoVendedor = (Float.parseFloat(line.substring(216, 229)) / 100);
        this.repasseAplicacao = (Float.parseFloat(line.substring(229, 242)) / 100);
        this.valorLiquido = (Float.parseFloat(line.substring(242, 255)) / 100);
        
        //inteiros
        this.tipoTransacao = Integer.parseInt(line.substring(35, 37));
        this.tipoEvento = Integer.parseInt(line.substring(33, 35));
        this.qteParcelas = Integer.parseInt(line.substring(128, 130));
        this.statusPagamento = Integer.parseInt(line.substring(255, 257));
        this.meioPagamento = Integer.parseInt(line.substring(259, 261));
        this.meioCaptura = Integer.parseInt(line.substring(295, 297));

        //caso o leitor vier em branco, setar para -1
        try {
            this.leitor = Integer.parseInt(line.substring(293, 295));
        }catch(NumberFormatException ex){
            this.leitor = -1;
        }
        
        //caracteres
        this.pagamento = line.charAt(123);
        
        //sequencias de caracteres
        this.codigoCliente = line.substring(1, 11).toCharArray();
        this.numSerieLeitor = line.substring(37, 45).toCharArray();
        this.codigoPedido = line.substring(77, 97).toCharArray();
        this.plano = line.substring(124, 126).toCharArray();
        this.parcelaLiberada = line.substring(126, 128).toCharArray();
        this.instituicaoFinanceira = line.substring(261, 291).toCharArray();
        this.canalEntrada = line.substring(291, 293).toCharArray();
        this.numeroLogico = line.substring(297, 329).toCharArray();
        this.NSU = line.substring(329, 340).toCharArray();
        this.cartaoBin = line.substring(343, 349).toCharArray();
        this.cartaoHolder = line.substring(349,353).toCharArray();
        this.codigoAutorizacao = line.substring(353,359).toCharArray();
        this.codigoCV = line.substring(359).toCharArray();
   
        //Confere se o numero de serie do leitor eh vazio
        if(new String(this.numSerieLeitor).trim().isEmpty()) this.numSerieLeitor = null;
    }

    /**
     * Construtor de Transacao
     *
    * @param codigoTransacao String 
    * @param dataInicial LocalDate 
    * @param dataConfirmacao LocalDateTime
    * @param codigoCliente char[] 
    * @param tipoEvento int 
    * @param codigoPedido char[]
    * @param valorTotal float
    * @param valorParcela float
    * @param pagamento char 
    * @param plano char[] 
    * @param parcelaLiberada char[] 
    * @param qteParcelas int 
    * @param dataPrevisaoPagamento LocalDate 
    * @param taxaParcelamentoComprador float 
    * @param tarifaBoletoComprador float 
    * @param taxaParcelamentoVendedor float 
    * @param tarifaBoletoVendedor float 
    * @param repasseAplicacao float 
    * @param valorOriginal float 
    * @param taxaIntermediacao float 
    * @param tarifaIntermediacao float 
    * @param valorLiquido float
    * @param tipoTransacao int 
    * @param statusPagamento int 
    * @param meioPagamento int 
    * @param canalEntrada char[] 
    * @param instituicaoFinanceira char[] 
    * @param meioCaptura int
    * @param numeroLogico char[] 
    * @param NSU char[] 
    * @param cartaoBin char[] 
    * @param cartaoHolder char[]
    * @param codigoAutorizacao char[]
    * @param codigoCV char[] 
    */
    public Transacao(String codigoTransacao, LocalDate dataInicial, LocalDateTime dataConfirmacao, char[] codigoCliente, int tipoEvento,
                     char[] codigoPedido, float valorTotal, float valorParcela, char pagamento, char[] plano, char[] parcelaLiberada,
                     int qteParcelas, LocalDate dataPrevisaoPagamento, float taxaParcelamentoComprador, float tarifaBoletoComprador,
                     float taxaParcelamentoVendedor, float tarifaBoletoVendedor, float repasseAplicacao, float valorOriginal,
                     float taxaIntermediacao, float tarifaIntermediacao, float valorLiquido, int tipoTransacao, int statusPagamento,
                     int meioPagamento, char[] canalEntrada, char[] instituicaoFinanceira, int meioCaptura, char[] numeroLogico, char[] NSU,
                     char[] cartaoBin, char[] cartaoHolder, char[] codigoAutorizacao, char[] codigoCV,int leitor,char[] numSerieLeitor) {

        //settando os atributos
        this.codigoTransacao = codigoTransacao;
        this.dataInicial = dataInicial;
        this.dataConfirmacao = dataConfirmacao;
        this.codigoCliente = codigoCliente;
        this.tipoEvento = tipoEvento;
        this.codigoPedido = codigoPedido;
        this.valorTotal = valorTotal;
        this.valorParcela = valorParcela;
        this.pagamento = pagamento;
        this.plano = plano;
        this.parcelaLiberada = parcelaLiberada;
        this.qteParcelas = qteParcelas;
        this.dataPrevisaoPagamento = dataPrevisaoPagamento;
        this.taxaParcelamentoComprador = taxaParcelamentoComprador;
        this.tarifaBoletoComprador = tarifaBoletoComprador;
        this.taxaParcelamentoVendedor = taxaParcelamentoVendedor;
        this.tarifaBoletoVendedor = tarifaBoletoVendedor;
        this.repasseAplicacao = repasseAplicacao;
        this.valorOriginal = valorOriginal;
        this.taxaIntermediacao = taxaIntermediacao;
        this.tarifaIntermediacao = tarifaIntermediacao;
        this.valorLiquido = valorLiquido;
        this.tipoTransacao = tipoTransacao;
        this.statusPagamento = statusPagamento;
        this.meioPagamento = meioPagamento;
        this.canalEntrada = canalEntrada;
        this.instituicaoFinanceira = instituicaoFinanceira;
        this.meioCaptura = meioCaptura;
        this.numeroLogico = numeroLogico;
        this.NSU = NSU;
        this.cartaoBin = cartaoBin;
        this.cartaoHolder = cartaoHolder;
        this.codigoAutorizacao = codigoAutorizacao;
        this.codigoCV = codigoCV;
        this.leitor = leitor;
        this.numSerieLeitor = numSerieLeitor;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public char[] getNumSerieLeitor() {
        return numSerieLeitor;
    }

    public void setNumSerieLeitor(char[] numSerieLeitor) {
        this.numSerieLeitor = numSerieLeitor;
    }

    public char[] getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(char[] codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public char[] getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(char[] codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public char getPagamento() {
        return pagamento;
    }

    public void setPagamento(char pagamento) {
        this.pagamento = pagamento;
    }

    public char[] getPlano() {
        return plano;
    }

    public void setPlano(char[] plano) {
        this.plano = plano;
    }

    public char[] getParcelaLiberada() {
        return parcelaLiberada;
    }

    public void setParcelaLiberada(char[] parcelaLiberada) {
        this.parcelaLiberada = parcelaLiberada;
    }

    public int getQteParcelas() {
        return qteParcelas;
    }

    public void setQteParcelas(int qteParcelas) {
        this.qteParcelas = qteParcelas;
    }

    public LocalDate getDataPrevisaoPagamento() {
        return dataPrevisaoPagamento;
    }

    public void setDataPrevisaoPagamento(LocalDate dataPrevisaoPagamento) {
        this.dataPrevisaoPagamento = dataPrevisaoPagamento;
    }

    public float getTaxaParcelamentoComprador() {
        return taxaParcelamentoComprador;
    }

    public void setTaxaParcelamentoComprador(float taxaParcelamentoComprador) {
        this.taxaParcelamentoComprador = taxaParcelamentoComprador;
    }

    public float getTarifaBoletoComprador() {
        return tarifaBoletoComprador;
    }

    public void setTarifaBoletoComprador(float tarifaBoletoComprador) {
        this.tarifaBoletoComprador = tarifaBoletoComprador;
    }

    public float getTaxaParcelamentoVendedor() {
        return taxaParcelamentoVendedor;
    }

    public void setTaxaParcelamentoVendedor(float taxaParcelamentoVendedor) {
        this.taxaParcelamentoVendedor = taxaParcelamentoVendedor;
    }

    public float getTarifaBoletoVendedor() {
        return tarifaBoletoVendedor;
    }

    public void setTarifaBoletoVendedor(float tarifaBoletoVendedor) {
        this.tarifaBoletoVendedor = tarifaBoletoVendedor;
    }

    public float getRepasseAplicacao() {
        return repasseAplicacao;
    }

    public void setRepasseAplicacao(float repasseAplicacao) {
        this.repasseAplicacao = repasseAplicacao;
    }

    public float getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(float valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public float getTaxaIntermediacao() {
        return taxaIntermediacao;
    }

    public void setTaxaIntermediacao(float taxaIntermediacao) {
        this.taxaIntermediacao = taxaIntermediacao;
    }

    public float getTarifaIntermediacao() {
        return tarifaIntermediacao;
    }

    public void setTarifaIntermediacao(float tarifaIntermediacao) {
        this.tarifaIntermediacao = tarifaIntermediacao;
    }

    public float getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(float valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public int getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(int tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public int getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(int statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public int getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(int meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public char[] getCanalEntrada() {
        return canalEntrada;
    }

    public void setCanalEntrada(char[] canalEntrada) {
        this.canalEntrada = canalEntrada;
    }

    public int getLeitor() {
        return leitor;
    }

    public void setLeitor(int leitor) {
        this.leitor = leitor;
    }

    public char[] getInstituicaoFinanceira() {
        return instituicaoFinanceira;
    }

    public void setInstituicaoFinanceira(char[] instituicaoFinanceira) {
        this.instituicaoFinanceira = instituicaoFinanceira;
    }

    public int getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(int meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public char[] getNumeroLogico() {
        return numeroLogico;
    }

    public void setNumeroLogico(char[] numeroLogico) {
        this.numeroLogico = numeroLogico;
    }

    public char[] getNSU() {
        return NSU;
    }

    public void setNSU(char[] NSU) {
        this.NSU = NSU;
    }

    public char[] getCartaoBin() {
        return cartaoBin;
    }

    public void setCartaoBin(char[] cartaoBin) {
        this.cartaoBin = cartaoBin;
    }

    public char[] getCartaoHolder() {
        return cartaoHolder;
    }

    public void setCartaoHolder(char[] cartaoHolder) {
        this.cartaoHolder = cartaoHolder;
    }

    public char[] getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(char[] codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public char[] getCodigoCV() {
        return codigoCV;
    }

    public void setCodigoCV(char[] codigoCV) {
        this.codigoCV = codigoCV;
    }
    
    
    
}
