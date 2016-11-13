'use strict';


angular.module('cloudeyeappApp')
 .controller('UserCtrl', function($scope, $http,$cookieStore){
 console.log('loading userctrl');
 //below is test data
  //$scope.users=[{username:'temp'},{username:'cloudeye'},{username:'admin'}];
  $http.get('http://localhost:8080/user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.users=response;
  });

$http.get('http://localhost:8080/role',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
    .success(function(response){
    $scope.roles=response;
  });
  $http.get('http://localhost:8080/tenant',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
      .success(function(response){
      $scope.tenants=response;
    });

    $scope.delete=function(id){
         console.log("deleting user with id "+id);
              $http.delete('http://localhost:8080/user/'+id ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                console.log('user with id '+id+'is deleted now');
                $http.get('http://localhost:8080/user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
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
                  $http.post('http://localhost:8080/user',$scope.user ,{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}}).success(function(data,status){
                    console.log('user with name '+$scope.user.name+'is created now');
                    $http.get('http://localhost:8080/user/',{headers:{ 'Authorization': 'Basic '+$cookieStore.get('globals').currentUser.authdata}})
                          .success(function(response){
                            $scope.users= response;
                          });

                  });

    };
 });
