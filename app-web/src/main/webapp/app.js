(function() {
	'use strict';

	angular.module('app', [ 'ngResource', 'echo-client' ])

	.directive("helloDirective", function() {
		return {
			template : "Hello from directive"
		};
	})

	.directive("helloRest", function($resource) {
		return {
			scope : {
				attribute : "@helloRest"
			},
			controller : function($scope) {
				$scope.returned = $resource('rest/' + $scope.attribute).get();
			},
			templateUrl : "hello-rest.html"
		};
	});
}());
