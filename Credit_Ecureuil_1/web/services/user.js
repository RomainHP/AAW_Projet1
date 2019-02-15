(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UtilisateurService', UtilisateurService);
 
    UtilisateurService.$inject = ['$http', '$q'];
    
    function UtilisateurService($http, $q) {
        
        var service = {};
        service.register = register;
        service.registerpro = registerpro;
        service.getAllUsers = getAllUsers;
        
        return service;
        
        function register(email, password){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/register.htm',
                method: "POST",
                params: {
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
        
        function registerpro(email, entreprise, siret, password){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/register_pro.htm',
                method: "POST",
                params: {
                        'email': email,
                        'entreprise': entreprise,
                        'siret': siret,
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
        
        function getAllUsers(){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8084/Credit_Ecureuil_1/envoyer_message.htm',
                method: "GET"
            }
            ).then(
                function(response){
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        
    }
 
})();
 