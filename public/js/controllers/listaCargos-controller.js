angular.module('GerenciadorUsuarios').controller('CargosController',function($scope, cargoService){
	$scope.cargos = [];

	cargoService.buscaTodosCargos()
	.success(function(cargos){
		$scope.cargos = cargos;
	})
	.error(function(erro){
		console.log(erro);
	});
});