'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandCtrl', function($scope, $http, $cookieStore){
 console.log('loading commandctrl');

    $http.get('http://localhost:8080/command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commands= response;
      });
 });
