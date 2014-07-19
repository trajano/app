// ...and add 'ui.router' as a dependency
(function() {
	'use strict';

	angular.module('myApp', [ 'ui.router', 'mgcrea.ngStrap', 'Mac' ])

	.directive(
			'tfScrollspyList',
			[
					'$location',
					'$anchorScroll',
					'$window',
					function($location, $anchorScroll, $window) {
						return {
							compile : function postLink(el, attr) {
								var children = el[0]
										.querySelectorAll('li > a[href]');

								angular.forEach(children, function(child) {
									var c = angular.element(child);
									var href = c.attr('href');
									c.removeAttr('href');

									var parent = c.parent();
									parent.attr('bs-scrollspy', '').attr(
											'data-target', href);

									c.bind('click', function(event) {
										console.log("HERE");
										$location.hash(href.substring(1));
										console.log("HERE");
										$anchorScroll();
										console.log("HERE");
										// TODO don't hardcode
										console.log("HERE");
										var yourHeight = getComputedStyle(angular.element(".navbar")[0]).height.replace("px","");
										var scrolledY = $window.scrollY;
										console.log("HERE");
										if(scrolledY){
										  $window.scroll(0, scrolledY - yourHeight);
										}
									});
								})
							}
						}
					} ])

	.config(
			[ '$stateProvider', '$urlRouterProvider',
					function($stateProvider, $urlRouterProvider) {
						$urlRouterProvider.otherwise('/home');

						$stateProvider.state('home', {
							url : '/home',
							templateUrl : 'dashboard.html'
						}).state('searchresults', {
							url : '/searchresults',
							templateUrl : 'searchresults2.html'
						}).state('dashboard', {
							url : '/dashboard',
							templateUrl : 'dashboard.html'
						})

						// HOME STATES AND NESTED VIEWS
						// ========================================
						.state('patient', {
							url : '/patient',
							templateUrl : 'chart.html'
						})

						// ABOUT PAGE AND MULTIPLE NAMED VIEWS
						// =================================
						.state('about', {
						// we'll get to this in a bit
						});

					} ]);

}());
