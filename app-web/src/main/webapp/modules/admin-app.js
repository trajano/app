(function() {
  'use strict';

  // TODO choose modules that are needed rather than loading up the whole
  // thing.

  var m = angular.module('admin-app', ['mgcrea.ngStrap', 'ngRoute']);

  m.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {
      templateUrl: 'content.html'
    }).when('/:page/:id', {
      templateUrl: function($routeParams) {
        return $routeParams.page + '.html';
      },
      controller: ['$scope', '$routeParams', function($scope, $routeParams) {
        console.warn("retrieval of " + $routeParams.id);
      }]
    }).when('/:page', {
      templateUrl: function($routeParams) {
        return $routeParams.page + '.html';
      }
    }).otherwise({
      redirectTo: '/home'
    });
  }]);

  m.directive('tfScrollspyList', [
      '$location',
      '$anchorScroll',
      '$window',
      function($location, $anchorScroll, $window) {
        return {
          compile: function postLink(el, attr) {
            var children = el[0].querySelectorAll('a[href]');
            angular.forEach(children, function(child) {
              var c = angular.element(child);
              var href = c.attr('href');
              c.removeAttr('href');

              c.attr('bs-scrollspy', '').attr('data-target', href);
              c.bind('click',
                      function(event) {
                        $location.hash(href.substring(1));
                        $anchorScroll();
                        // TODO don't hard code this.
                        var yourHeight = getComputedStyle(angular
                                .element(".navbar")[0]).height
                                .replace("px", "");
                        var scrolledY = $window.scrollY;
                        console.log("HERE");
                        if (scrolledY) {
                          $window.scroll(0, scrolledY - yourHeight);
                        }

                      });
            })
          }
        }
      }]);

  m.directive('tfScrollspyList', ['$location', '$anchorScroll',
      function($location, $anchorScroll) {
        return {
          compile: function postLink(el, attr) {
            var children = el[0].querySelectorAll('li > a[href]');

            angular.forEach(children, function(child) {
              var c = angular.element(child);
              console.log(c);
              var href = c.attr('href');
              c.removeAttr('href');

              var parent = c.parent();
              parent.attr('bs-scrollspy', '').attr('data-target', href);

              c.bind('click', function(event) {
                $location.hash(href.substring(1));
                $anchorScroll();
              });
            })
          }
        }
      }]);

  m
          .directive(
                  "adminApp",
                  function() {
                    return {
                      templateUrl: 'modules/admin-app/base.html',

                      // TODO add module to get the application name and
                      // such
                      controller: [
                          '$scope',
                          '$attrs',
                          '$window',

                          function($scope, $attrs, $window) {

                            $scope.onResize = function() {
                              var width = ($window.innerWidth > 0)
                                      ? $window.innerWidth : this.screen.width;
                              if (width < 768) {
                                angular.element('div.sidebar-collapse')
                                        .addClass('collapse')
                              } else {
                                angular.element('div.sidebar-collapse')
                                        .removeClass('collapse')
                              }
                            };

                            angular.element($window).bind('resize', function() {
                              $scope.onResize();
                              $scope.$apply();
                            });
                            angular.element('#side-menu').metisMenu();

                            // TODO this would be loaded by via REST
                            // since the results should be readily
                            // cachable.
                            var applicationConfiguration = {
                              'title': 'Trajano Enterprise Framework',
                              'profileUri': "#",
                              'logoutUri': "#"
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
                              messageCount: 15,
                              messages: [
                                  {
                                    'from': 'John Smith',
                                    'when': 'Yesterday',
                                    'href': '#',
                                    'excerpt': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
                                  },
                                  {
                                    'from': 'John Smith2',
                                    'when': 'Yesterday',
                                    'href': '#',
                                    'excerpt': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
                                  }],
                              taskCount: 12,
                              tasks: [{
                                subject: "First task",
                                progress: 42
                              }, {
                                subject: "Second task",
                                progress: 12
                              }, {
                                subject: "Three task",
                                progress: 99
                              }],
                              alertCount: 16,
                              alerts: [{
                                icon: "comment",
                                message: "New comment",
                                when: "4 minutes ago"
                              }, {
                                icon: "twitter",
                                message: "3 new followers",
                                when: "12 minutes ago"
                              }, {
                                icon: "envelope",
                                message: "Message sent",
                                when: "4 minutes ago"
                              }, {
                                icon: "tasks",
                                message: "New task",
                                when: "4 minutes ago"
                              }

                              ]
                            };

                            $scope.userInfo = {
                              "name": "Archimedes Trajano",
                              "email": "archimedes@trajano.net"
                            };
                            $scope.messages = [

                                {
                                  'from': 'John Smith',
                                  'when': 'Yesterday',
                                  'href': '#',
                                  'excerpt': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
                                },
                                {
                                  'from': 'John Smith2',
                                  'when': 'Yesterday',
                                  'href': '#',
                                  'excerpt': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...'
                                }

                            ];
                          }]
                    };
                  });

}());
