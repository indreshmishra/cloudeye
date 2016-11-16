'use strict';

/**
 * @ngdoc overview
 * @name cloudeyeappApp
 * @description
 * # cloudeyeappApp
 *
 * Main module of the application.
 */
angular
  .module('cloudeyeappApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/user',{
       templateUrl: 'views/user.html',
       controller: 'UserCtrl'
      })
      .when('/tenant',{
      templateUrl: 'views/tenant.html',
      controller: 'TenantCtrl'
      })
      .when('/host',{
      templateUrl: 'views/host.html',
      controller: 'HostCtrl'
      })
      .when('/command',{
       templateUrl: 'views/command.html',
       controller: 'CommandCtrl'
      })
      .when('/commandhost',{
      templateUrl: 'views/commandhost.html',
      controller: 'CommandHostCtrl'
      })
      .when('/workernode',{
        templateUrl: 'views/workernode.html',
        controller: 'WorkerNodeCtrl'
      })
      .when('/commandhosthistory',{
      templateUrl: 'views/runhistory.html',
      controller: 'RunHistoryCtrl'})
      .otherwise({
        redirectTo: '/'
      });
  })
  .factory('AuthenticationService',['Base64','$http','$cookieStore','$rootScope',
      function(Base64,$http,$cookieStore,$rootScope){
        var service={};
        service.setCredentials=function(username,password){
              var authdata=Base64.encode(username+':'+password);
              $rootScope.globals={
                  currentUser:{
                    username: username,
                    authdata: authdata
                  }

                };

                //$http.defaults.headers.Authorization='Basic '+authdata;
                $cookieStore.put('globals',$rootScope.globals);
          };

          service.clearCredentials=function(){
            $rootScope.globals={};
            $cookieStore.remove('globals');
            $http.defaults.headers.Authorization='Basic ';
          };

          service.login=function(username,password,successcallback,errorcallback){

            var authdata=Base64.encode(username+':'+password);
            //$http.defaults.headers.Authorization='Basic '+authdata;
            var config={};
            config.headers={};
            config.headers.Authorization= 'Basic '+authdata;
            //delete $httpProvider.defaults.headers.common['X-Requested-With'];
//            $http.post('http://localhost:8080/auth/check',{username:username,password:password},config)
//            .success(function(response){
//                callback(response);
//              });

             $http({
              method:'POST',
              url: 'http://localhost:8080/auth/check',
              data:{username:username,password:password},
              headers:{
                'Authorization': 'Basic '+authdata,
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers':'X-Requested-With'
              }
             }).then(function successCallback(response){
                             var authdata=Base64.encode(username+':'+password);
                                          $rootScope.globals={
                                              currentUser:{
                                                username: username,
                                                authdata: authdata
                                              },
                                              loggedin:true
                                            };

                                            $http.defaults.headers.Authorization='Basic '+authdata;
                                            $cookieStore.put('globals',$rootScope.globals);
                            successcallback(response);},

                     function errorCallback(response){
                            $rootScope.globals={};
                            $cookieStore.remove('globals');
                            $http.defaults.headers.Authorization='Basic ';
                            errorcallback(response);
             });

          };
        return service;
      }]
    )
    .factory('Base64',function(){
     return {
      encode:function(input){
          return btoa(input);
        }
      };
    });
