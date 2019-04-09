CREATE VIEW `qteTransacoesPorExtrato` AS SELECT
    E.numArquivo,
    COUNT(*) transacoes
FROM 
    EXTRATO E,
    TRANSACAO T
WHERE 
    E.numArquivo = T.Extrato_numArquivo    
GROUP BY 
    E.numArquivo;