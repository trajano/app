(function() {
	'use strict';

	// TODO choose modules that are needed rather than loading up the whole
	// thing.

	angular
			.module('admin-app',
					[ 'mgcrea.ngStrap', 'ngSanitize', 'ngAnimate' ])

			.directive(
					"adminApp",
					function() {
						return {
							templateUrl : 'modules/admin-app/base.html',

							// TODO add module to get the application name and
							// such
							controller : [
									'$scope',
									'$attrs',
									'$window',

									function($scope, $attrs, $window) {

										$scope.onResize = function() {
											var width = ($window.innerWidth > 0) ? $window.innerWidth
													: this.screen.width;
											if (width < 768) {
												angular.element(
														'div.sidebar-collapse')
														.addClass('collapse')
											} else {
												angular
														.element(
																'div.sidebar-collapse')
														.removeClass('collapse')
											}
										};

										angular.element($window).bind('resize',
												function() {
													$scope.onResize();
													$scope.$apply();
												});
										angular.element('#side-menu')
												.metisMenu();

										// TODO this would be loaded by via REST
										// since the results should be readily
										// cachable.
										var applicationConfiguration = {
											'title' : 'Trajano Enterprise Framework',
											'profileUri' : "#",
											'logoutUri' : "#"
										};

										$scope.applicationConfiguration = applicationConfiguration;
										$scope.title = applicationConfiguration.title;
										$window.document.title = applicationConfiguration.title;

										// TODO This will be loaded by web
										// sockets, the data set should be
										// relatively small.

										// alerts and user configuration are
										// using the default layout

										// alerts is using the default layout to
										// allow flexibility in the messages
										// being sent

										$scope.notifications = {
											messageCount : 15,
											messages : [
													{
														'from' : 'John Smith',
														'when' : 'Yesterday',
														'href' : '#',
														'excerpt' : 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
													},
													{
														'from' : 'John Smith2',
														'when' : 'Yesterday',
														'href' : '#',
														'excerpt' : 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
													} ],
											taskCount : 12,
											tasks : [ {
												subject : "First task",
												progress : 42
											}, {
												subject : "Second task",
												progress : 12
											}, {
												subject : "Three task",
												progress : 99
											} ],
											alertCount : 16,
											alerts : [ {
												icon : "comment",
												message : "New comment",
												when : "4 minutes ago"
											}, {
												icon : "twitter",
												message : "3 new followers",
												when : "12 minutes ago"
											}, {
												icon : "envelope",
												message : "Message sent",
												when : "4 minutes ago"
											}, {
												icon : "tasks",
												message : "New task",
												when : "4 minutes ago"
											},

											]
										};

										$scope.userInfo = {
											"name" : "Archimedes Trajano",
											"email" : "archimedes@trajano.net"
										};
										$scope.messages = [

												{
													'from' : 'John Smith',
													'when' : 'Yesterday',
													'href' : '#',
													'excerpt' : 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
												},
												{
													'from' : 'John Smith2',
													'when' : 'Yesterday',
													'href' : '#',
													'excerpt' : 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
												}

										];

										/*
										 * <ul class="dropdown-menu dropdown-messages">
										 * <li><a href="#"> <div> <strong>John
										 * Smith</strong> <span
										 * class="pull-right text-muted"> <em>Yesterday</em>
										 * </span> </div> <div></div> </a></li>
										 * <li class="divider"></li> <li><a
										 * href="#"> <div> <strong>John Smith</strong>
										 * <span class="pull-right text-muted">
										 * <em>Yesterday</em> </span> </div>
										 * <div>Lorem ipsum dolor sit amet,
										 * consectetur adipiscing elit.
										 * Pellentesque eleifend...</div> </a></li>
										 * <li class="divider"></li> <li><a
										 * href="#"> <div> <strong>John Smith</strong>
										 * <span class="pull-right text-muted">
										 * <em>Yesterday</em> </span> </div>
										 * <div>Lorem ipsum dolor sit amet,
										 * consectetur adipiscing elit.
										 * Pellentesque eleifend...</div> </a></li>
										 * <li class="divider"></li> <li><a
										 * class="text-center" href="#">
										 * <strong>Read All Messages</strong>
										 * <i class="fa fa-angle-right"></i>
										 * </a></li> </ul> <!--
										 * /.dropdown-messages -->
										 */

									} ]
						};
					})

	;

}());
