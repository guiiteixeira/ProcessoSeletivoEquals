angular.module('GerenciadorUsuarios').controller('UsuariosController', function($scope, $filter, usuarioService){

	$scope.usuarios = [];
	$scope.filtro = '';
	$scope.filterValue = '';
	$scope.options = [
		{nome: "Nenhum", valor: "nenhum"},
		{nome: "Nome", valor:"nome"},
		{nome: "Cargo", valor:"cargo"},
	];
	$scope.mensagem = '';

	$scope.filtroCargo= "";
	$scope.filtroNome= "";
	$scope.filtroPerfil= "";

	$scope.updateFiltro = function(){
		if($scope.filterValue == "nenhum"){
			$scope.filtroCargo = "";
			$scope.filtroNome = "";
			$scope.filtroPerfil = "";
		} else if($scope.filterValue == "nome"){
			$scope.filtroCargo = "";
			$scope.filtroNome = $scope.filtro;
			$scope.filtroPerfil = "";
		} else if($scope.filterValue == "cargo"){
			$scope.filtroCargo = $scope.filtro;
			$scope.filtroNome = "";
			$scope.filtroPerfil = "";
		} else if($scope.filterValue == "perfil"){
			$scope.filtroCargo = "";
			$scope.filtroNome = "";
			$scope.filtroPerfil = $scope.filtro;
		}
	}

	usuarioService.buscaTodosUsuarios()
	.success(function(usuarios){
		$scope.usuarios = usuarios;
		$scope.totalItems = usuarios.length;
	})
	.error(function(erro){
		console.log(erro);
	});

	$scope.remover = function(usuario){
		usuario.removido = true;

		usuarioService.deletaUsuario(usuario)
		.success(function(){
			$scope.mensagem= "Usuário removido com sucesso";
		})
		.error(function(erro){
			$scope.mensagem = "Não foi possível excluir o usuário";
			console.log(erro);
		});


		console.log(usuario);
	};

});
