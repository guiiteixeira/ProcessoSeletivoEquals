angular.module('GerenciadorUsuarios').service('cargoService', ['$http', function($http) {

	return {
		buscaCargo: function(idCargo) {
			return $http.get('/cargos/' + idCargo);
		},

		buscaTodosCargos: function() {
			return $http.get('/cargos');
		},

		atualizaCargo: function(cargo) {
			return $http({
				method: "put",
				url: '/cargos/edit/' + cargo.id,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				params: {
					cargo: cargo.nome
				}
			});
		},

		adicionaCargo: function(cargo){
			return $http({
				method: 'post',
				url: '/cargos',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				params: {
					cargo: cargo.nome
				}
			});
		}
	}
}]);