'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandCtrl', function($scope, $http, $cookieStore, ConfigService){
 console.log('loading commandctrl');

    $http.get(ConfigService.config.serviceurl+'command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commands= response;
      });


      $scope.delete=function(id){
      console.log("deleting command with id "+id);
      $http.delete(ConfigService.config.serviceurl+'command/'+id ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
        console.log('command with id '+id+'is deleted now');
        $http.get(ConfigService.config.serviceurl+'command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
              .success(function(response){
                $scope.commands= response;
              });

      });
      };


$scope.newcommand=function(){
      console.log('creating new command');
      $http.post(ConfigService.config.serviceurl+'command' ,$scope.command,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
              console.log('command is created');
               $scope.command={};
              $http.get(ConfigService.config.serviceurl+'command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                    .success(function(response){
                      $scope.commands= response;
                    });

            });
      };

 });
