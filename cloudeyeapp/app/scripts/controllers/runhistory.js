'use strict';


angular.module('cloudeyeappApp')
 .controller('RunHistoryCtrl', function($scope, $http, $cookieStore, ConfigService){
 console.log('loading runhistory controller');

    $http.get(ConfigService.config.reporterurl+'runhistory')
      .success(function(response){
        $scope.records= response;
      });



 });
