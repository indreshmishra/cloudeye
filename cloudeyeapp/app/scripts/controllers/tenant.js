'use strict';


angular.module('cloudeyeappApp')
 .controller('TenantCtrl', function($scope, $http,$cookieStore){
 console.log('loading userctrl');

  $http.get('http://localhost:8080/tenant/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.tenants=response;
  });


 });
