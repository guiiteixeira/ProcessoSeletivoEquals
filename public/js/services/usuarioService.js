angular.module('GerenciadorUsuarios').service('usuarioService', ["$http", function($http){

	return {
		buscaUsuario: function(usuarioId) {
			return $http.get('/usuarios/' + usuarioId);
		},

		buscaTodosUsuarios: function() {
			return $http.get('/usuarios');
		},

		atualizaUsuario: function(usuario) {
			return $http({
				method: "put",
				url: '/usuarios/edit/' + usuario.id,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				params: {
					nome: usuario.nome,
					cpf: usuario.cpf,
					dataNascimento: usuario.dataNascimento,
					sexo: usuario.sexo,
					cargo: usuario.cargo.nome
				}
			});
		},

		adicionaUsuario: function(usuario) {
			return $http({
				method: "post",
				url: '/usuarios',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				params: {
					nome: usuario.nome,
					cpf: usuario.cpf,
					dataNascimento: usuario.dataNascimento,
					sexo: usuario.sexo,
					cargo: usuario.cargo.nome
				}
			});
		},

		deletaUsuario: function(usuario) {
			return $http({
				method: "put",
				url: '/usuarios/delete',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				params: {
					cpf: usuario.cpf,
				}
			});
		}
	}
}]);