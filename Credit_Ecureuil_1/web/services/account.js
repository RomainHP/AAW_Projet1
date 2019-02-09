(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CompteService', CompteService);
 
    CompteService.$inject = ['$http', '$q', '$rootScope'];
    
    function CompteService($http, $q) {
        
        var service = {};
        service.consultation = consultation;
        service.createAccount = createAccount;
        service.getAllAccounts = getAllAccounts;
        service.virement = virement;
        
        return service;
    
        function consultation(mail){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/consultation.htm',
                method: "GET",
                params : {mail : mail}
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
        
        function createAccount(name, user){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/ajout_livret.htm',
                method: "POST",
                data : {
                        'name': name,
                        'login': user
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
        
        function getAllAccounts(){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/virement.htm',
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
        
        function virement(source, dest, montant){
            var deferred = $q.defer();
            $http({
                url: 'http://localhost:8080/Credit_Ecureuil_1/virement.htm',
                method: "POST",
                data : {
                    'source' : source,
                    'dest' : dest,
                    'montant' : montant
                }
            }
            ).then(
                function(response){
                    console.log(response.data);
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