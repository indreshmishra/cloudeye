'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandHostCtrl', function($scope, $http, $cookieStore){
 console.log('loading commandhostctrl');

    $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commandhosts= response;
      });

       $scope.delete=function(id){
            console.log("deleting command host with id "+id);
            $http.delete('http://localhost:8080/commandhost/'+id ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
              console.log('command host with id '+id+'is deleted now');
               $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                    .success(function(response){
                      $scope.commandhosts= response;
                    });
            });
            };
 });
