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
        service.updateProfile = updateProfile;
        service.getProfile = getProfile;
        
        return service;
        
        function register(email, password){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/register.htm',
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
                url: 'http://localhost:8080/Credit_Ecureuil_1/register_pro.htm',
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
                url: 'http://localhost:8080/Credit_Ecureuil_1/envoyer_message.htm',
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
        
        function updateProfile(login, password, password_confirmation, nom, prenom, company){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/profil.htm',
                method: "POST",
                params : {
                    "login" : login,
                    "password" : password,
                    "password_confirmation" : password_confirmation,
                    "nom":nom,
                    "prenom":prenom,
                    "company": company
                }
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
        
        function getProfile(login){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/profil.htm',
                method: "GET",
                params : {
                    "login" : login
                }
            }
            ).then(
                function(response){
                    //console.log(response);
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
 