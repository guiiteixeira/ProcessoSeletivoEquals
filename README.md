# Processo Seletivo Equals

O Projeto utiliza a Framework Play versão 1.4.5

Para instalação da Framework basta seguir os passos:

	1- Acesse https://www.playframework.com/
	2- Clique em 'browse all versions' e selecione a versão 1.4.5 para fazer o download
	3- Após fazer o download é necessario extrair os arquivos em uma pasta de sua escolha e configurar seu PATH para poder executar o play
	   na linha de comando

Configuração do Banco de Dados:

	1- O padrão do acesso ao banco de dados é:
		
		Usuário - "root"
		Senha - ""
		IP - "localhost"
		Porta - "3306"
		
		- Caso seja necessária a substituição desses dados, siga os passos:
			
			1.1- Na pasta 'conf' abra o arquivo 'db.stt'
			1.2- Preencha os dados da seguinte forma:
				
				mysql-user@NOVO_USUARIO
				mysql-pass@NOVA_SENHA
				mysql-addr@NOVO_IP
				mysql-port@NOVA_PORTA

				Obs: É importante que os dados sejam preenchidos exatamente como no exemplo acima!
		
	2- O script de criação do banco de dados encontra-se na pasta 'database'


Execução do projeto:

	1- Abra a pasta do projeto no terminal
	2- Execute 'play run'
	3- O passo anterior iniciou o projeto no framework, agora vá até seu WEB Browser e acesse:
		
		3.1- localhost:9000/leitura/NOME_DO_ARQUIVO  --> Se você quer ler um arquivo, adicionando seus dados no banco de dados,
								 e apresentar os dados na página WEB
		
		3.2- localhost:9000/NUMERO_EXTRATO	     --> Se você quer apenas acessar um extrato no banco de dados e apresentar 
								 os dados na pagina WEB
