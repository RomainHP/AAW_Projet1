(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies', '$rootScope', 'UserService'];
    function AuthentificationService($cookies, $rootScope, UserService) {
        var service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
 
        return service;
 
        function login(username, password) {
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/login.htm',
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
                    password: password
                }
            };
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function clearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();