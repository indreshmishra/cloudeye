'use strict';

/**
 * @ngdoc function
 * @name cloudeyeappApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cloudeyeappApp
 */
angular.module('cloudeyeappApp')
  .controller('MainCtrl',[ '$scope','$http', '$cookieStore','AuthenticationService', function ($scope,$http,$cookieStore,AuthenticationService) {
    if($cookieStore.get('globals')){
      $scope.loggedin = $cookieStore.get('globals').loggedin;
      $scope.username = $cookieStore.get('globals').currentUser.username;
    }
    else{
      $scope.loggedin=false;
          $scope.user={};
          $scope.errormessage='';
    }

    $scope.submitloginform=function(){
      //console.log('submit..');

      AuthenticationService.login($scope.user.username,$scope.user.password,function(response){

        $scope.loggedin=true;
        $scope.username=$scope.user.username;
        $scope.errormessage='';
        console.log(response);

      } , function(response){
        $scope.errormessage='login failed,try again';
        console.log(response);
      });
    };

    $scope.logout=function(){
      AuthenticationService.clearCredentials();
      $scope.loggedin=false;
      $scope.user={};
    };

  }]);
