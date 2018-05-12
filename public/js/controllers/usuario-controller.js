angular.module('GerenciadorUsuarios').controller('UsuarioController', function($scope,$routeParams, usuarioService){
	$scope.usuario = {};
	$scope.mensagem = '';
	$scope.sexos = [ 
		'Masculino',
		'Feminino',
	];

	if($routeParams.usuarioId){
		usuarioService.buscaUsuario($routeParams.usuarioId)
		.success(function(usuario){
			$scope.usuario = usuario;
		})
		.error(function(erro){
			console.log(erro);
			$scope.mensagem = "Não foi possível recuperar o usuário";
		});
	}

	$scope.submeter = function() {
		var usuario = $scope.usuario;

		if($scope.formAddUsuario.$valid){
			
			if(usuario.id) {
				usuarioService.atualizaUsuario(usuario)
				.success(function(){
					$scope.mensagem= "Usuário editado com sucesso";
				})
				.error(function(erro){
					$scope.mensagem = "Não foi possível editar o usuário";
					console.log(erro);
				});

			} else {
				usuarioService.adicionaUsuario(usuario)
				.success(function(){
					$scope.mensagem= "Usuário adicionado com sucesso";
					$scope.usuario = {};
				})
				.error(function(erro){
					$scope.mensagem = erro;
				});
			}

		};
	};



});
