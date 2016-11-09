'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandHostCtrl', function($scope, $http, $cookieStore){
 console.log('loading commandhostctrl');

    $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commandhosts= response;
      });
 });
