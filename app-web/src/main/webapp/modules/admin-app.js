(function() {
	'use strict';

	// TODO choose modules that are needed rather than loading up the whole
	// thing.

	angular
			.module('admin-app', [ 'mgcrea.ngStrap', 'ngSanitize' ])

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

									function($scope, $attrs) {

										var applicationConfiguration = {
											'title' : 'Trajano Enterprise Framework'
										};

										$scope.applicationConfiguration = applicationConfiguration;
										$scope.title = applicationConfiguration.title;
										// $scope.notification.messages
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
