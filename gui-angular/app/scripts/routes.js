(function(){
	'use strict';

	function config($routeProvider) {
		// body...
		$routeProvider.when('/compare', {
			templateUrl: 'views/compare/compare.html',
			controller: 'compareCtrl', 
			controllerAs: 'compare'
		}).otherwise({ redirectTo: '/' })
	}

	angular.module('kangaroo').config(config);

})();