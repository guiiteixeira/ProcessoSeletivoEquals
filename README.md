# Processo Seletivo Equals

O Projeto utiliza a Framework Play vers�o 1.4.5

Para instala��o da Framework basta seguir os passos:

	1- Acesse https://www.playframework.com/
	2- Clique em 'browse all versions' e selecione a vers�o 1.4.5 para fazer o download
	3- Ap�s fazer o download � necessario extrair os arquivos em uma pasta de sua escolha e configurar seu PATH para poder executar o play
	   na linha de comando

Configura��o do Banco de Dados:

	1- O padr�o do acesso ao banco de dados �:
		
		Usu�rio - "root"
		Senha - ""
		IP - "localhost"
		Porta - "3306"
		
		- Caso seja necess�ria a substitui��o desses dados, siga os passos:
			
			1.1- Na pasta 'conf' abra o arquivo 'db.stt'
			1.2- Preencha os dados da seguinte forma:
				
				mysql-user@NOVO_USUARIO
				mysql-pass@NOVA_SENHA
				mysql-addr@NOVO_IP
				mysql-port@NOVA_PORTA

				Obs: � importante que os dados sejam preenchidos exatamente como no exemplo acima!
		
	2- O script de cria��o do banco de dados encontra-se na pasta 'database'


Execu��o do projeto:

	1- Abra a pasta do projeto no terminal
	2- Execute 'play run'
	3- O passo anterior iniciou o projeto no framework, agora v� at� seu WEB Browser e acesse:
		
		3.1- localhost:9000/leitura/NOME_DO_ARQUIVO  --> Se voc� quer ler um arquivo, adicionando seus dados no banco de dados,
								 e apresentar os dados na p�gina WEB
		
		3.2- localhost:9000/NUMERO_EXTRATO	     --> Se voc� quer apenas acessar um extrato no banco de dados e apresentar 
								 os dados na pagina WEB
