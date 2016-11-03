'use strict';


angular.module('cloudeyeappApp')
 .controller('WorkerNodeCtrl', function($scope, $http, $cookieStore){
 console.log('loading WorkerNodeCtrl');

    $http.get('http://localhost:8080/workernode/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.nodes= response;
      });
 });
