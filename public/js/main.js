angular.module('GerenciadorUsuarios', ['ngRoute'])
.config(function($routeProvider){

	$routeProvider.when('/index', {
		templateUrl: 'public/partials/principal.html',
	});

	$routeProvider.when('/cargos', {
		templateUrl: 'public/partials/listaCargos.html',
		controller: 'CargosController'
	});
	$routeProvider.when('/cargo/new', {
		templateUrl: 'public/partials/cargo.html',
		controller: "CargoController"
	});
	$routeProvider.when('/cargo/edit/:cargoId', {
		templateUrl: 'public/partials/cargo.html',
		controller: "CargoController"
	});

	$routeProvider.when('/usuarios', {
		templateUrl: 'public/partials/listaUsuarios.html',
		controller: 'UsuariosController'
	});
	$routeProvider.when('/usuario/new', {
		templateUrl: 'public/partials/usuario.html',
		controller: "UsuarioController"
	});
	$routeProvider.when('/usuario/edit/:usuarioId', {
		templateUrl: 'public/partials/usuario.html',
		controller: "UsuarioController"
	});


	$routeProvider.otherwise({redirectTo: '/index'});
});
