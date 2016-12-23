'use strict';


angular.module('cloudeyeappApp')
 .controller('TenantCtrl', function($scope, $http,$cookieStore,ConfigService){
 console.log('loading userctrl');

  $http.get(ConfigService.config.serviceurl+'tenant/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.tenants=response;
  });


 });
