'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandHostCtrl', function($scope, $http, $cookieStore){
 console.log('loading commandhostctrl');

    $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commandhosts= response;
      });
    $http.get('http://localhost:8080/host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
          .success(function(response){
            $scope.hosts= response;
          });
     $http.get('http://localhost:8080/command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
           .success(function(response){
             $scope.commands= response;
           });


       $scope.delete=function(id){
            console.log("deleting command host with id "+id);
            $http.delete('http://localhost:8080/commandhost/'+id ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
              console.log('command host with id '+id+'is deleted now');
               $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                    .success(function(response){
                      $scope.commandhosts= response;
                    });
            });
            };

       $scope.disable=function(id){
                                  console.log("disabling command host with id "+id);
                                  $http.put('http://localhost:8080/commandhost/'+id+'/disable' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                    console.log('command host with id '+id+'is disabled now');
                                     $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                          .success(function(response){
                                            $scope.commandhosts= response;
                                          });
                                  });
                                  };
        $scope.enable=function(id){
                                         console.log("enabling command host with id "+id);
                                         $http.put('http://localhost:8080/commandhost/'+id+'/enable' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                           console.log('command host with id '+id+'is enabled now');
                                            $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                 .success(function(response){
                                                   $scope.commandhosts= response;
                                                 });
                                         });
                                         };
        $scope.run=function(id){
                                                 console.log("running command host with id "+id);
                                                 $http.put('http://localhost:8080/commandhost/'+id+'/run' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                                   console.log('command host with id '+id+'is running now');
                                                    $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                         .success(function(response){
                                                           $scope.commandhosts= response;
                                                         });
                                                 });
                                                 };

        $scope.newcommandhost=function(){
            console.log("creating new commandhost");
            $http.post('http://localhost:8080/command/'+$scope.selectedcommandid+'/host/'+$scope.selectedhostid ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                console.log("new command host is created");
                 $http.get('http://localhost:8080/commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                                 .success(function(response){
                                                                   $scope.commandhosts= response;
                                                                 });
            });
        };
 });
