'use strict';


angular.module('cloudeyeappApp')
 .controller('RunHistoryCtrl', function($scope, $http, $cookieStore){
 console.log('loading runhistory controller');

    $http.get('http://localhost:8090/runhistory')
      .success(function(response){
        $scope.records= response;
      });



 });
