'use strict';


angular.module('cloudeyeappApp')
 .controller('CommandHostCtrl', function($scope, $http, $cookieStore, ConfigService){
 console.log('loading commandhostctrl');

    $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
        $scope.commandhosts= response;
      });
    $http.get(ConfigService.config.serviceurl+'host',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
          .success(function(response){
            $scope.hosts= response;
          });
     $http.get(ConfigService.config.serviceurl+'command/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
           .success(function(response){
             $scope.commands= response;
           });


       $scope.delete=function(id){
            console.log("deleting command host with id "+id);
            $http.delete(ConfigService.config.serviceurl+'commandhost/'+id ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
              console.log('command host with id '+id+'is deleted now');
               $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                    .success(function(response){
                      $scope.commandhosts= response;
                    });
            });
            };

       $scope.disable=function(id){
                                  console.log("disabling command host with id "+id);
                                  $http.put(ConfigService.config.serviceurl+'commandhost/'+id+'/disable' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                    console.log('command host with id '+id+'is disabled now');
                                     $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                          .success(function(response){
                                            $scope.commandhosts= response;
                                          });
                                  });
                                  };
        $scope.enable=function(id){
                                         console.log("enabling command host with id "+id);
                                         $http.put(ConfigService.config.serviceurl+'commandhost/'+id+'/enable' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                           console.log('command host with id '+id+'is enabled now');
                                            $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                 .success(function(response){
                                                   $scope.commandhosts= response;
                                                 });
                                         });
                                         };
        $scope.run=function(id){
                                                 console.log("running command host with id "+id);
                                                 $http.put(ConfigService.config.serviceurl+'commandhost/'+id+'/run' ,{},{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                                                   console.log('command host with id '+id+'is running now');
                                                    $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                         .success(function(response){
                                                           $scope.commandhosts= response;
                                                         });
                                                 });
                                                 };
        $scope.commandhost={'runAgain':false,'fixedDelay':0};
        $scope.newcommandhost=function(){
            console.log("creating new commandhost");
            $http.post(ConfigService.config.serviceurl+'command/'+$scope.selectedcommandid+'/host/'+$scope.selectedhostid ,$scope.commandhost,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata ,'Content-Type':'application/json'}}).success(function(data,status){
                console.log("new command host is created");
                 $http.get(ConfigService.config.serviceurl+'commandhost',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                                                 .success(function(response){
                                                                   $scope.commandhosts= response;
                                                                 });
            });
        };
 });
