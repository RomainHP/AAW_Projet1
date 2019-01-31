(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$http', '$q', '$cookies', '$rootScope'];
    function AuthentificationService($http, $q, $cookies, $rootScope) {
        var service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
 
        return service;
 
        function login(username, password) {
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/login.htm',
                method: "POST",
                data: {
                        'email': username,
                        'password': password
                    }
                }
            ).then(
                function(response){
                    deferred.resolve(response);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
 
        function setCredentials(username, password) {
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password,
                    isPro: false,
                    isAdmin: false
                }
            };
            $cookies.putObject('globals', $rootScope.globals);
        }
        
        function setCredentials(username, password, isPro, isAdmin) {
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password,
                    isPro: isPro,
                    isAdmin: isAdmin
                }
            };
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function clearCredentials() {
            $cookies.remove('globals');
            $rootScope.globals.currentUser = null;
        }
    }
 
})();