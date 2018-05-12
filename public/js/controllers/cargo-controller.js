angular.module('GerenciadorUsuarios').controller('CargoController',function($scope,$routeParams, cargoService){
	$scope.cargo = {};
	$scope.mensagem = '';

	if($routeParams.cargoId){
		cargoService.buscaCargo($routeParams.cargoId)
		.success(function(cargo){
			$scope.cargo = cargo;
		})
		.error(function(erro){
			console.log(erro);
			$scope.mensagem = 'Não foi possivel recuperar o cargo'
		})
	}

	$scope.submeter = function(){
		var cargo = $scope.cargo;

		if(cargo.id){
			cargoService.atualizaCargo(cargo)
			.success(function(){
				$scope.mensagem= "Cargo editado com sucesso";
			})
			.error(function(erro){
				$scope.mensagem = "Não foi possível editar o cargo";
				console.log(erro);
			});
		} else {
			cargoService.adicionaCargo(cargo)
			.success(function(){
				$scope.mensagem = 'Cargo adicionado com sucesso';
			})
			.error(function(erro){
				console.log(erro);
				$scope.mensagem = 'Não foi possível adicionar o cargo' + cargo.nome;
			});
		}

	};
});