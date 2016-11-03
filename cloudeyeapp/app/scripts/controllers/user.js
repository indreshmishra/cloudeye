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


 });
