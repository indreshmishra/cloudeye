'use strict';


angular.module('cloudeyeappApp')
 .controller('HostCtrl', function($scope, $http, $cookieStore){
 console.log('loading HostCtrl');

    $http.get('http://localhost:8080/host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.hosts= response;
      });
 });
