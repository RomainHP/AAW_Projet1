(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UtilisateurService', UtilisateurService);
 
    UtilisateurService.$inject = ['$http', '$q'];
    
    function UtilisateurService($http, $q) {
        
        var service = {};
        service.register = register;
        
        return service;
        
        function register(email, password){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/register.htm',
                method: "POST",
                data: {
                        'email': email,
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
    }
 
})();
 