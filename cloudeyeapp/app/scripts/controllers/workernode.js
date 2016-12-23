'use strict';


angular.module('cloudeyeappApp')
 .controller('WorkerNodeCtrl', function($scope, $http, $cookieStore,ConfigService){
 console.log('loading WorkerNodeCtrl');

    $http.get(ConfigService.config.serviceurl+'workernode/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.nodes= response;
      });
 });
