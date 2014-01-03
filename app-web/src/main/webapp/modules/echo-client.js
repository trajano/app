(function() {
	'use strict';

	angular.module('echo-client', [])

	.factory(
			'echoSocket',
			function($window) {
				var loc = $window.location;
				var wsUri;
				if (loc.protocol === "https:") {
					wsUri = "wss:";
				} else {
					wsUri = "ws:";
				}
				wsUri += '//'
						+ loc.host
						+ loc.pathname.substring(0, loc.pathname
								.lastIndexOf('/')) + '/echo';

				var webSocket = new WebSocket(wsUri);
				$window.onbeforeunload = function() {
					websocket.onclose = function() {
					}; // disable onclose handler first
					websocket.close();
				};
				return webSocket;
			})

	.directive('echoClient', function() {
		return {
			templateUrl : 'modules/echo-client/template.html',
			replace : true,
			controller : [ '$scope', '$attrs', 'echoSocket',

			function($scope, $attrs, echoSocket) {
				$scope.message = '';
				$scope.interactions = [];

				echoSocket.onmessage = function(event) {
					$scope.interactions.push({
						direction : 'RECEIVED',
						message : event.data
					});
					$scope.$apply();
				};

				$scope.sendMessage = function() {
					$scope.interactions.push({
						direction : 'SENT',
						message : $scope.message
					});
					echoSocket.send($scope.message);
					$scope.message = '';
				};
			} ]

		};
	});
}());
