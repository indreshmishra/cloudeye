'use strict';


angular.module('cloudeyeappApp')
 .controller('HostCtrl', function($scope, $http, $cookieStore, ConfigService){
 console.log('loading HostCtrl');

    $http.get(ConfigService.config.serviceurl+'host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.hosts= response;
      });


    $scope.newhost=function(){
        $scope.host.name=$scope.host.hostname;
        console.log($scope.host);
         $http.post(ConfigService.config.serviceurl+'host',$scope.host, {headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
              .success(function(response){
                console.log('new host got created');
                 $http.get(ConfigService.config.serviceurl+'host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                      .success(function(response){
                        $scope.hosts= response;
                      });
               $scope.host={};
              });
    }
 });
