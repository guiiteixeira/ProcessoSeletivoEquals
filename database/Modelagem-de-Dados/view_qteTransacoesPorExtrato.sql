CREATE VIEW `qteTransacoesPorExtrato` AS SELECT
    E.numArquivo,
    COUNT(*) transacoes
FROM 
    Extrato E,
    Transacao T
WHERE 
    E.numArquivo = T.Extrato_numArquivo    
GROUP BY 
    E.numArquivo;
