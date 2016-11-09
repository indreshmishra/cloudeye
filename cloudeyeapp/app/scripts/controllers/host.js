'use strict';


angular.module('cloudeyeappApp')
 .controller('HostCtrl', function($scope, $http, $cookieStore){
 console.log('loading HostCtrl');

    $http.get('http://localhost:8080/host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.hosts= response;
      });


    $scope.newhost=function(){
        host.name=host.hostname;
        console.log($scope.host);
         $http.post('http://localhost:8080/host',$scope.host, {headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
              .success(function(response){
                console.log('new host got created');
                 $http.get('http://localhost:8080/host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                      .success(function(response){
                        $scope.hosts= response;
                      });
               $scope.host={};
              });
    }
 });
