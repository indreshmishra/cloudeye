'use strict';

/**
 * @ngdoc function
 * @name cloudeyeappApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the cloudeyeappApp
 */
angular.module('cloudeyeappApp')
  .controller('AboutCtrl', function ($scope, $http) {

    $scope.aboutinfo = 'This is Cloudeye Application Portal.';

    $http.get('http://localhost:8080/about/').success(function(response){

      $scope.aboutinfo=response.message;
    });
  });
