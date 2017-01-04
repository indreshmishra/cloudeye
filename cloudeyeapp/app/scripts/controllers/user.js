'use strict';


angular.module('cloudeyeappApp')
 .controller('UserCtrl', function($scope, $http,$cookieStore, ConfigService){
 console.log('loading userctrl');
 //below is test data
  //$scope.users=[{username:'temp'},{username:'cloudeye'},{username:'admin'}];
  $http.get(ConfigService.config.serviceurl+'user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.users=response;
  });

$http.get(ConfigService.config.serviceurl+'role',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.roles=response;
  });
  $http.get(ConfigService.config.serviceurl+'tenant',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
      $scope.tenants=response;
    });

    $scope.delete=function(id){
         console.log("deleting user with id "+id);
              $http.delete(ConfigService.config.serviceurl+'user/'+id ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                console.log('user with id '+id+'is deleted now');
                $http.get(ConfigService.config.serviceurl+'user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                      .success(function(response){
                        $scope.users= response;
                      });

              });
    };

    $scope.user={};
    $scope.user.roles=[];

    $scope.newuser=function(){
    console.log("creating user with name "+$scope.user.name);
    $scope.user.roles=[];
                  $scope.user.roles.push({'id':$scope.selectedroleid});
                  $http.post(ConfigService.config.serviceurl+'user',$scope.user ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                    console.log('user with name '+$scope.user.name+'is created now');
                    $http.get(ConfigService.config.serviceurl+'user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                          .success(function(response){
                            $scope.users= response;
                          });

                  });

    };
    $scope.editable=false;
    $scope.edit=function(user){
        console.log('editing user with name '+ user.name);
        $scope.editable=true;
        $scope.euser=user;
    };

    $scope.saveediteduser=function(){
      console.log('updating user with name '+$scope.euser.name);
       $http.put(ConfigService.config.serviceurl+'user',$scope.euser ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                          console.log('user with name '+$scope.user.name+'is created now');
                          $http.get(ConfigService.config.serviceurl+'user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                                .success(function(response){
                                  $scope.users= response;
                                });

                        });
    };
 });
