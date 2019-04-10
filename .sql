-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Equals
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Equals
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Equals` DEFAULT CHARACTER SET utf8 ;
USE `Equals` ;

-- -----------------------------------------------------
-- Table `Equals`.`Extrato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Equals`.`Extrato` (
  `numArquivo` CHAR(7) NOT NULL,
  `dataProcessamento` DATE NOT NULL,
  `dataInicial` DATE NOT NULL,
  `dataFinal` DATE NOT NULL,
  `tipoExtrato` TINYINT UNSIGNED NOT NULL,
  `numEstabelecimento` CHAR(10) NOT NULL,
  `Layout` CHAR(3) NOT NULL,
  `Release` CHAR(5) NOT NULL,
  `empresaAdquirente` CHAR(5) NOT NULL,
  PRIMARY KEY (`numArquivo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Equals`.`Transacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Equals`.`Transacao` (
  `codTransacao` CHAR(32) NOT NULL,
  `dataInicial` DATE NOT NULL,
  `dataConfirmacao` DATETIME NOT NULL,
  `numSerieLeitor` CHAR(8) NULL,
  `codCliente` CHAR(10) NOT NULL,
  `tipoEvento` TINYINT UNSIGNED NOT NULL,
  `codPedido` CHAR(20) NOT NULL,
  `valorTotal` FLOAT NOT NULL,
  `valorParc` FLOAT NOT NULL,
  `pagamento` ENUM('A', 'M', 'U') NOT NULL,
  `plano` CHAR(2) NOT NULL,
  `parcelaLiberada` CHAR(2) NOT NULL,
  `numParcelas` TINYINT UNSIGNED NOT NULL,
  `dataPrevisaoPagamento` DATE NOT NULL,
  `taxaParcelamentoComp` FLOAT NOT NULL,
  `tarifaBoletoComp` FLOAT NOT NULL,
  `taxaParcelamentoVend` FLOAT NOT NULL,
  `tarifaBoletoVend` FLOAT NOT NULL,
  `taxaIntermediacao` FLOAT NOT NULL,
  `tarifaIntermediacao` FLOAT NOT NULL,
  `repasseAplicacao` FLOAT NOT NULL,
  `valorOriginal` FLOAT NOT NULL,
  `valorLiquido` FLOAT NOT NULL,
  `tipoTransacao` TINYINT UNSIGNED NOT NULL,
  `statusPagamento` TINYINT UNSIGNED NOT NULL,
  `meioPagamento` TINYINT UNSIGNED NOT NULL,
  `canalEntrada` CHAR(2) NOT NULL,
  `leitor` TINYINT UNSIGNED NULL,
  `instituicaoFinanceira` CHAR(30) NOT NULL,
  `meioCaptura` TINYINT UNSIGNED NOT NULL,
  `numLogico` CHAR(32) NOT NULL,
  `NSU` CHAR(11) NOT NULL,
  `cartaoBin` CHAR(6) NOT NULL,
  `cartaoHolder` CHAR(4) NOT NULL,
  `codAutorizacao` CHAR(6) NOT NULL,
  `codCV` CHAR(32) NOT NULL,
  `Extrato_numArquivo` CHAR(7) NULL,
  PRIMARY KEY (`codTransacao`),
  INDEX `fk_Transação_Extrato_idx` (`Extrato_numArquivo` ASC),
  CONSTRAINT `fk_Transação_Extrato`
    FOREIGN KEY (`Extrato_numArquivo`)
    REFERENCES `Equals`.`Extrato` (`numArquivo`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `Equals` ;

-- -----------------------------------------------------
-- Placeholder table for view `Equals`.`qteTransacoesPorExtrato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Equals`.`qteTransacoesPorExtrato` (`numArquivo` INT, `transacoes` INT);

-- -----------------------------------------------------
-- View `Equals`.`qteTransacoesPorExtrato`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Equals`.`qteTransacoesPorExtrato`;
USE `Equals`;
CREATE  OR REPLACE VIEW `qteTransacoesPorExtrato` AS SELECT
    E.numArquivo,
    COUNT(*) transacoes
FROM 
    EXTRATO E,
    TRANSACAO T
WHERE 
    E.numArquivo = T.Extrato_numArquivo    
GROUP BY 
    E.numArquivo;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
