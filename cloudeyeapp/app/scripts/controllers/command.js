'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandCtrl', function($scope, $http, $cookieStore){
 console.log('loading commandctrl');

    $http.get('http://localhost:8080/command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commands= response;
      });


      $scope.delete=function(id){
      console.log("deleting command with id "+id);
      $http.delete('http://localhost:8080/command/'+id ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
        console.log('command with id '+id+'is deleted now');
        $http.get('http://localhost:8080/command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
              .success(function(response){
                $scope.commands= response;
              });

      });
      };


$scope.newcommand=function(){
      console.log('creating new command');
      $http.post('http://localhost:8080/command' ,$scope.command,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
              console.log('command is created');
               $scope.command={};
              $http.get('http://localhost:8080/command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                    .success(function(response){
                      $scope.commands= response;
                    });

            });
      };

 });
